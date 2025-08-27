<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Slider</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            background-color: #f4f7f9;
            color: #333;
        }
        .dashboard-container {
            display: flex;
            min-height: 100vh;
        }
        .sidebar {
            width: 250px;
            background-color: #2c3e50;
            color: white;
            padding: 20px;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            transition: width 0.3s ease-in-out;
        }
        .sidebar-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .sidebar-nav ul {
            list-style-type: none;
            padding: 0;
        }
        .sidebar-nav li {
            margin-bottom: 10px;
            transition: background-color 0.3s ease;
            border-radius: 5px;
        }
        .sidebar-nav li:hover {
            background-color: #34495e;
        }
        .sidebar-nav li.active {
            background-color: #3498db;
        }
        .sidebar-nav a {
            display: block;
            padding: 15px 20px;
            color: white;
            text-decoration: none;
            font-size: 16px;
            transition: color 0.3s ease;
        }
        .sidebar-nav a:hover {
            color: #ecf0f1;
        }
        .main-content {
            flex-grow: 1;
            padding: 30px;
            transition: margin-left 0.3s ease-in-out;
        }
        .main-header {
            margin-bottom: 20px;
        }
        .content-section {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        }
        .content-section h2 {
            margin-top: 0;
        }
        .content-section form label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }
        .content-section form input[type="text"],
        .content-section form input[type="file"] {
            width: 100%;
            padding: 10px 12px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-bottom: 15px;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }
        .content-section form input[type="text"]:focus,
        .content-section form input[type="file"]:focus {
            border-color: #3498db;
            box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
            outline: none;
        }
        .content-section form button {
            padding: 10px 20px;
            background-color: #2ecc71;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        .content-section form button:hover {
            background-color: #27ae60;
        }
        .back-btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-bottom: 20px;
            transition: background-color 0.3s ease;
        }
        .back-btn:hover {
            background-color: #2980b9;
        }
        .profile-section {
            text-align: center;
            margin-bottom: 30px;
        }
        .profile-avatar {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid #3498db;
        }
        .profile-name {
            margin: 10px 0;
            font-size: 18px;
            font-weight: 500;
        }
        .edit-profile-btn {
            background-color: transparent;
            border: none;
            color: #3498db;
            font-size: 16px;
            cursor: pointer;
            position: relative;
            top: -58px;
            left: 30px;
            transition: color 0.3s ease;
        }
        .edit-profile-btn:hover {
            color: #2980b9;
        }
        .image-input-section {
            margin-bottom: 15px;
        }
        .image-input-section label {
            font-weight: bold;
            margin-bottom: 10px;
            display: block;
        }
        .image-input-section .input-toggle {
            margin-bottom: 10px;
        }
        .image-input-section .input-toggle input[type="radio"] {
            margin-right: 5px;
        }
        .image-input-section .input_toggle label {
            font-weight: normal;
            margin-right: 20px;
            cursor: pointer;
        }
        .image-input-section .input-field {
            display: none;
        }
        .image-input-section .input-field.active {
            display: block;
        }
        @media (max-width: 768px) {
            .content-section {
                padding: 15px;
            }
        }
    </style>
    <script>
        function toggleImageInput() {
            const urlInput = document.getElementById('imageUrlInput');
            const fileInput = document.getElementById('imageFileInput');
            const urlRadio = document.getElementById('inputUrl');
            const fileRadio = document.getElementById('inputFile');
            
            if (urlRadio.checked) {
                urlInput.classList.add('active');
                fileInput.classList.remove('active');
                fileInput.querySelector('input').value = ''; // Clear file input
            } else {
                fileInput.classList.add('active');
                urlInput.classList.remove('active');
                urlInput.querySelector('input').value = ''; // Clear URL input
            }
        }
        // Redirect after 3 seconds if success message is present
        window.onload = function() {
            toggleImageInput();
            const message = document.getElementById('success-message');
            if (message && message.innerText.trim() !== '') {
                setTimeout(function() {
                    window.location.href = 'manageslider';
                }, 3000);
            }
        };
    </script>
</head>
<body>
    <c:if test="${empty sessionScope.user}">
        <c:redirect url="login"/>
    </c:if>
    <div class="dashboard-container">
        <aside class="sidebar">
            <div class="sidebar-header">
                <h3>Admin Dashboard</h3>
            </div>
            <div class="profile-section">
                <img src="${empty sessionScope.user.avataUrl ? 'assets/img/default-avatar.png' : sessionScope.user.avataUrl}" alt="Avatar" class="profile-avatar">
                <div class="profile-name">
                    <c:out value="${sessionScope.user.firstName} ${sessionScope.user.middleName != null ? sessionScope.user.middleName : ''} ${sessionScope.user.lastName}"/>
                </div>
                <a href="${pageContext.request.contextPath}/profile"><button class="edit-profile-btn" title="Edit Profile"><i class="fas fa-edit"></i></button></a>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li data-section="overview">
                        <a href="admin">Overview</a>
                    </li>
                    <li data-section="courses">
                        <a href="admintopic">List Topic</a>
                    </li>
                    <li data-section="users">
                        <a href="manageuser">Manage Users</a>
                    </li>
                    <li data-section="slider" class="active">
                        <a href="manageslider">Manage Slider</a>
                    </li>
                    <li data-section="settings">
                        <a href="${pageContext.request.contextPath}/logout">Logout</a>
                    </li>
                </ul>
            </nav>
        </aside>
        <main class="main-content">
            <header class="main-header">
                <h1>Welcome to Create Slider</h1>
            </header>
            <div id="success-message" style="color:green">${message}</div>
            <div style="color:red; text-align:center; padding: 20px;">${error}</div>
            <div class="content-section">
                <a href="manageslider" class="back-btn">Back to Slider List</a>
                <h2>Create Slider</h2>
                <form action="manageslider" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="create">
                    <label>Title:
                        <input type="text" name="title" value="${param.title}" required>
                    </label>
                    <div class="image-input-section">
                        <label>Image:</label>
                        <div class="input-toggle">
                            <input type="radio" id="inputUrl" name="inputType" value="url" checked onclick="toggleImageInput()">
                            <label for="inputUrl">Enter URL</label>
                            <input type="radio" id="inputFile" name="inputType" value="file" onclick="toggleImageInput()">
                            <label for="inputFile">Upload File</label>
                        </div>
                        <div id="imageUrlInput" class="input-field active">
                            <input type="text" name="imageUrl" value="${param.imageUrl}" placeholder="Enter image URL">
                        </div>
                        <div id="imageFileInput" class="input-field">
                            <input type="file" name="imageFile" accept="image/*">
                        </div>
                    </div>
                    <button type="submit">Create</button>
                </form>
            </div>
        </main>
    </div>
</body>
</html>