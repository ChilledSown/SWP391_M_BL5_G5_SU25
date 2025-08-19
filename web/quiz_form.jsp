<%-- 
    Document   : quiz_form
    Created on : Aug 19, 2025, 12:31:02 AM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${quiz != null ? "Edit" : "Add"} Quiz</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
</head>
<body class="container mt-5">
    <h3>${quiz != null ? "Edit Quiz" : "Add New Quiz"}</h3>

    <form method="post" action="${quiz != null ? 'editQuiz' : 'createQuiz'}">
        <input type="hidden" name="lessonId" value="${param.lessonId}" />
        <c:if test="${quiz != null}">
            <input type="hidden" name="quizId" value="${quiz.quizId}" />
        </c:if>

        <div class="mb-3">
            <label>Question</label>
            <input type="text" class="form-control" name="question" value="${quiz != null ? quiz.question : ''}" required />
        </div>

        <div class="mb-3">
            <label>Answer Options (each option on a new line)</label>
            <textarea class="form-control" name="answerOptions" rows="4" required>
<c:if test="${quiz != null}">
<%
    String options = ((model.Quiz) request.getAttribute("quiz")).getAnswerOptions();
    for (String line : options.split(";")) {
        out.println(line.trim());
    }
%>
</c:if>
            </textarea>
        </div>

        <div class="mb-3">
            <label>Correct Answer (e.g., A, B, C...)</label>
            <input type="text" class="form-control" name="correctAnswer" value="${quiz != null ? quiz.correctAnswer : ''}" required />
        </div>

        <div class="mb-3">
            <label>Explanation (optional)</label>
            <textarea class="form-control" name="explanation" rows="3">${quiz != null ? quiz.explanation : ''}</textarea>
        </div>

        <button type="submit" class="btn btn-primary">${quiz != null ? "Update" : "Create"}</button>
        <a href="manageQuiz?lessonId=${param.lessonId}" class="btn btn-secondary">Cancel</a>
    </form>
</body>
</html>
