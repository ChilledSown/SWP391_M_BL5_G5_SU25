<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Topic Details</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            background-color: #f4f7f9;
            color: #333;
        }
        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h1, h2 {
            color: #2c3e50;
        }
        .topic-details {
            margin-bottom: 30px;
        }
        .topic-details img {
            max-width: 100%;
            height: auto;
            border-radius: 8px;
            margin-bottom: 15px;
        }
        .topic-details p {
            margin: 10px 0;
        }
        .data-table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border-radius: 5px;
            overflow: hidden;
        }
        .data-table th, .data-table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .data-table th {
            background-color: #f8f9fa;
            font-weight: bold;
        }
        .data-table tr:hover {
            background-color: #f5f5f5;
        }
        .data-table img {
            max-width: 100px;
            height: auto;
            border-radius: 4px;
        }
        .no-data {
            text-align: center;
            padding: 20px;
            color: #7f8c8d;
        }
        .action-btn, .back-btn, .back-to-topic-btn {
            display: inline-block;
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 4px;
            margin: 5px;
            font-size: 14px;
        }
        .action-btn {
            background-color: #3498db;
            color: white;
        }
        .action-btn:hover {
            background-color: #2980b9;
        }
        .back-btn {
            background-color: #e67e22;
            color: white;
        }
        .back-btn:hover {
            background-color: #d35400;
        }
        .back-to-topic-btn {
            background-color: #2ecc71;
            color: white;
        }
        .back-to-topic-btn:hover {
            background-color: #27ae60;
        }
        .error {
            color: red;
            text-align: center;
            margin-bottom: 20px;
        }
        .button-container {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Topic Details</h1>
        <c:if test="${empty topic}">
            <div class="error">Topic not found</div>
        </c:if>
        <c:if test="${not empty topic}">
            <div class="topic-details">
                <h2><c:out value="${topic.name}" /></h2>
                <c:if test="${not empty topic.thumbnail_Url}">
                    <img src="<c:out value='${topic.thumbnail_Url}' />" alt="Topic Thumbnail">
                </c:if>
                <p><strong>ID:</strong> <c:out value="${topic.topic_id}" /></p>
                <p><strong>Description:</strong> <c:out value="${topic.description != null ? topic.description : 'N/A'}" /></p>
            </div>

            <c:choose>
                <c:when test="${not empty quizzes}">
                    <h2>Quizzes in Lesson ID: ${param.lessonId}</h2>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Question</th>
                                <th>Answer Options</th>
                                <th>Correct Answer</th>
                                <th>Created At</th>
                                <th>Updated At</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty quizzes}">
                                    <c:forEach var="quiz" items="${quizzes}">
                                        <tr>
                                            <td><c:out value="${quiz.quiz_id}" /></td>
                                            <td><c:out value="${quiz.question}" /></td>
                                            <td><c:out value="${quiz.answer_options}" /></td>
                                            <td><c:out value="${quiz.correct_answer}" /></td>
                                            <td><fmt:formatDate value="${quiz.created_at}" pattern="dd-MM-yyyy" /></td>
                                            <td><c:out value="${quiz.updated_at != null ? quiz.updated_at : 'N/A'}" /></td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="6" class="no-data">No quizzes found for this lesson</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                    <div class="button-container">
                        <a href="viewtopic?topicId=${param.topicId}&courseId=${param.courseId}" class="back-btn">Back to Lessons</a>
                        <a href="viewtopic?topicId=${param.topicId}" class="back-to-topic-btn">Back to Topic</a>
                    </div>
                </c:when>
                <c:when test="${not empty lessons}">
                    <h2>Lessons in Course ID: ${param.courseId}</h2>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Video URL</th>
                                <th>Content</th>
                                <th>Created At</th>
                                <th>Updated At</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty lessons}">
                                    <c:forEach var="lesson" items="${lessons}">
                                        <tr>
                                            <td><c:out value="${lesson.lesson_id}" /></td>
                                            <td><c:out value="${lesson.title}" /></td>
                                            <td><c:out value="${lesson.video_url != null ? lesson.video_url : 'N/A'}" /></td>
                                            <td><c:out value="${lesson.content != null ? lesson.content : 'N/A'}" /></td>
                                            <td><fmt:formatDate value="${lesson.created_at}" pattern="dd-MM-yyyy" /></td>
                                            <td><c:out value="${lesson.updated_at != null ? lesson.updated_at : 'N/A'}" /></td>
                                            <td>
                                                <a href="viewtopic?topicId=${param.topicId}&courseId=${param.courseId}&lessonId=${lesson.lesson_id}" class="action-btn">View Quizzes</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="7" class="no-data">No lessons found for this course</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                    <div class="button-container">
                        <a href="viewtopic?topicId=${param.topicId}" class="back-btn">Back to Courses</a>
                        <a href="viewtopic?topicId=${param.topicId}" class="back-to-topic-btn">Back to Topic</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <h2>Courses in this Topic</h2>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Description</th>
                                <th>Price</th>
                                <th>Thumbnail</th>
                                <th>Created At</th>
                                <th>Updated At</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty courses}">
                                    <c:forEach var="course" items="${courses}">
                                        <tr>
                                            <td><c:out value="${course.course_id}" /></td>
                                            <td><c:out value="${course.title}" /></td>
                                            <td><c:out value="${course.description != null ? course.description : 'N/A'}" /></td>
                                            <td><fmt:formatNumber value="${course.price}" type="number" pattern="#,###" /> VND</td>
                                            <td>
                                                <c:if test="${not empty course.thumbnail_url}">
                                                    <img src="<c:out value='${course.thumbnail_url}' />" alt="Course Thumbnail">
                                                </c:if>
                                                <c:if test="${empty course.thumbnail_url}">
                                                    N/A
                                                </c:if>
                                            </td>
                                            <td><fmt:formatDate value="${course.created_at}" pattern="dd-MM-yyyy" /></td>
                                            <td><c:out value="${course.updated_at != null ? course.updated_at : 'N/A'}" /></td>
                                            <td>
                                                <a href="viewtopic?topicId=${param.topicId}&courseId=${course.course_id}" class="action-btn">View Lessons</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="8" class="no-data">No courses found for this topic</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                    <div class="button-container">
                        <a href="managecourse" class="back-btn">Back to Manage Courses</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>
    </div>
</body>
</html>