<%-- 
    Document   : seller_blog.jsp
    Created on : Aug 19, 2025, 1:04:02 AM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Blogs | Seller Dashboard</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<h1>hello word</h1>
<!--<body class="container mt-5">
    <h2>Blog Management</h2>

    <a href="createBlog.jsp" class="btn btn-primary mb-3">
        <i class="fas fa-plus"></i> Add New Blog
    </a>

     Search 
    <form method="get" action="seller_blog.jsp" class="mb-4">
        <div class="row g-2">
            <div class="col-md-6">
                <input type="text" name="title" class="form-control" placeholder="Search by title..." value="${param.title}" />
            </div>
            <div class="col-md-3">
                <input type="date" name="createdDate" class="form-control" value="${param.createdDate}" />
            </div>
            <div class="col-md-3">
                <button type="submit" class="btn btn-info w-100">Search</button>
            </div>
        </div>
    </form>

     Blog Table 
    <table class="table table-bordered">
        <thead class="thead-dark">
            <tr>
                <th>Title</th>
                <th>Content Preview</th>
                <th>Created At</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="blog" items="${blogs}">
                <tr>
                    <td>${blog.title}</td>
                  
                    <td><fmt:formatDate value="${blog.createdAt}" pattern="yyyy-MM-dd" /></td>
                    <td>
                        <a href="editBlog?blogId=${blog.blogId}" class="btn btn-sm btn-warning" title="Edit">
                            <i class="fas fa-edit"></i>
                        </a>
                        <a href="deleteBlog?blogId=${blog.blogId}" class="btn btn-sm btn-danger" title="Delete"
                           onclick="return confirm('Are you sure you want to delete this blog?');">
                            <i class="fas fa-trash-alt"></i>
                        </a>
                        <a href="blogDetail?blogId=${blog.blogId}" class="btn btn-sm btn-info" title="Detail">
                            <i class="fas fa-eye"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <jsp:include page="pagination.jsp" />
</body>-->
</html>
