<%-- 
    Document   : BlogDetailSeller
    Created on : Aug 20, 2025, 11:08:10 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <title>Blog Detail</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css"/>

    </head>
    <body class="container mt-5">
        <h3>Blog Detail</h3>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <c:if test="${not empty blog}">
            <table class="table table-bordered">
                <tr>
                    <th>Title</th>
                    <td>${blog.title}</td>
                </tr>
                <tr>
                    <th>Content</th>
                    <td>${blog.content}</td>
                </tr>
                <tr>
                    <th>Thumbnail</th>
                    <td>
                        <c:if test="${not empty blog.thumbnailUrl}">
                            <img src="${blog.thumbnailUrl}" alt="Blog Thumbnail" style="max-width: 200px;">
                        </c:if>
                        <c:if test="${empty blog.thumbnailUrl}">
                            No thumbnail
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th>Created At</th>
                    <td><fmt:formatDate value="${blog.createdAt}" pattern="yyyy-MM-dd" /></td>
                </tr>
                <tr>
                    <th>Updated At</th>
                    <td><fmt:formatDate value="${blog.updatedAt}" pattern="yyyy-MM-dd" /></td>
                </tr>
                <tr>
                    <th>Created By</th>
                    <td>${blog.createdBy}</td>
                </tr>
            </table>
        </c:if>
    <a href="listBlogsInstructor" class="btn btn-secondary">Back</a>
</body>
</html>