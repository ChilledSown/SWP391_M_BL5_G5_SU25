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
        <div class="error-message">${errorMessage}</div>
    </c:if>
    <c:choose>
        <c:when test="${not empty blog}">
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
                    <img src="${blog.thumbnailUrl}" alt="Blog Thumbnail">
                </c:if>
                <c:if test="${empty blog.thumbnailUrl}">
                    No thumbnail available
                </c:if>
                </td>
                </tr>
                <tr>
                    <th>Created At</th>
                    <td>
                <fmt:parseDate value="${blog.createdAt}" pattern="yyyy-MM-dd" var="parsedCreatedAt" />
                <fmt:formatDate value="${parsedCreatedAt}" pattern="yyyy-MM-dd" />
                </td>
                </tr>
                <tr>
                    <th>Updated At</th>
                    <td>
                <fmt:parseDate value="${blog.updatedAt}" pattern="yyyy-MM-dd" var="parsedUpdatedAt" />
                <fmt:formatDate value="${parsedUpdatedAt}" pattern="yyyy-MM-dd" />
                </td>
                </tr>
                <tr>
                    <th>Created By</th>
                    <td>${blog.createdBy}</td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <p>No blog details available.</p>
        </c:otherwise>
    </c:choose>
    <a href="listBlogsSeller" class="btn btn-secondary">Back</a>
</body>
</html>