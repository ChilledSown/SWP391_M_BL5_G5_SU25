<%-- 
    Document   : quiz_form
    Created on : Aug 19, 2025, 12:31:02 AM
    Author     : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${quiz != null ? "Edit" : "Add"} Quiz</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css"/>
</head>
<body class="container mt-5">
    <h3>${quiz != null ? "Edit Quiz" : "Add New Quiz"}</h3>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    <form method="post" action="${quiz != null ? 'editQuizSeller' : 'createQuizSeller'}">
        <input type="hidden" name="lessonId" value="${param.lessonId != null ? param.lessonId : lessonId}" />
        <input type="hidden" name="courseId" value="${param.courseId != null ? param.courseId : courseId}" />
        <c:if test="${quiz != null}">
            <input type="hidden" name="quizId" value="${quiz.quizId}" />
        </c:if>
        <div class="mb-3">
            <label for="question">Question</label>
            <input type="text" class="form-control" id="question" name="question" value="${quiz != null ? quiz.question : param.question}" required />
        </div>
        <div class="mb-3">
            <label>Answer Options (at least two required)</label>
            <c:choose>
                <c:when test="${quiz != null}">
                    <%
                        String[] options = ((model.Quiz) request.getAttribute("quiz")).getAnswerOptions().split(";", -1);
                        String optionA = options.length > 0 ? options[0].trim() : "";
                        String optionB = options.length > 1 ? options[1].trim() : "";
                        String optionC = options.length > 2 ? options[2].trim() : "";
                        String optionD = options.length > 3 ? options[3].trim() : "";
                    %>
                    <div class="input-group mb-2">
                        <span class="input-group-text">A</span>
                        <input type="text" class="form-control" name="answerOptionA" value="<%= optionA %>" />
                    </div>
                    <div class="input-group mb-2">
                        <span class="input-group-text">B</span>
                        <input type="text" class="form-control" name="answerOptionB" value="<%= optionB %>" />
                    </div>
                    <div class="input-group mb-2">
                        <span class="input-group-text">C</span>
                        <input type="text" class="form-control" name="answerOptionC" value="<%= optionC %>" />
                    </div>
                    <div class="input-group mb-2">
                        <span class="input-group-text">D</span>
                        <input type="text" class="form-control" name="answerOptionD" value="<%= optionD %>" />
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="input-group mb-2">
                        <span class="input-group-text">A</span>
                        <input type="text" class="form-control" name="answerOptionA" value="${param.answerOptionA}" />
                    </div>
                    <div class="input-group mb-2">
                        <span class="input-group-text">B</span>
                        <input type="text" class="form-control" name="answerOptionB" value="${param.answerOptionB}" />
                    </div>
                    <div class="input-group mb-2">
                        <span class="input-group-text">C</span>
                        <input type="text" class="form-control" name="answerOptionC" value="${param.answerOptionC}" />
                    </div>
                    <div class="input-group mb-2">
                        <span class="input-group-text">D</span>
                        <input type="text" class="form-control" name="answerOptionD" value="${param.answerOptionD}" />
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="mb-3">
            <label for="correctAnswer">Correct Answer (A, B, C, or D)</label>
            <input type="text" class="form-control" id="correctAnswer" name="correctAnswer" value="${quiz != null ? quiz.correctAnswer : param.correctAnswer}" required />
        </div>
        <div class="mb-3">
            <label for="explanation">Explanation</label>
            <textarea class="form-control" id="explanation" name="explanation" rows="3" required>${quiz != null ? quiz.explanation : param.explanation}</textarea>
        </div>
        <button type="submit" class="btn btn-primary">${quiz != null ? "Update" : "Create"}</button>
        <a href="manageQuizInstructor?lessonId=${param.lessonId != null ? param.lessonId : lessonId}&courseId=${param.courseId != null ? param.courseId : courseId}" class="btn btn-secondary">Cancel</a>
    </form>
</body>
</html>