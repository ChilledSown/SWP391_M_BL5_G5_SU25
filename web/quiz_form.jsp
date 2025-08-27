
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${quiz != null && quiz.quizId != null ? "Edit Quiz" : "Add New Quiz"}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css"/>
</head>
<body class="container mt-5">
    <h3>${quiz != null && quiz.quizId != null ? "Edit Quiz" : "Add New Quiz"}</h3>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    <form method="post" action="${quiz != null && quiz.quizId != null ? 'editQuizSeller' : 'createQuizSeller'}"
          ${quiz == null || quiz.quizId == null ? 'enctype="multipart/form-data"' : ''}>
        <input type="hidden" name="lessonId" value="${lessonId}" />
        <input type="hidden" name="courseId" value="${courseId}" />
        <c:if test="${quiz != null && quiz.quizId != null}">
            <input type="hidden" name="quizId" value="${quiz.quizId}" />
        </c:if>
      
        <div class="mb-3">
            <label for="question">Question</label>
            <input type="text" class="form-control" id="question" name="question" value="${quiz.question != null ? quiz.question : param.question}" />
            <c:if test="${not empty questionError}">
                <div class="text-danger">${questionError}</div>
            </c:if>
        </div>
        <div class="mb-3">
            <label>Answer Options (at least two required)</label>
            <c:set var="options" value="${quiz != null && quiz.answerOptions != null ? quiz.answerOptions.split(';') : ['', '', '', '']}" />
            <div class="input-group mb-2">
                <span class="input-group-text">A</span>
                <input type="text" class="form-control" name="answerOptionA" value="${options[0]}" />
            </div>
            <div class="input-group mb-2">
                <span class="input-group-text">B</span>
                <input type="text" class="form-control" name="answerOptionB" value="${options[1]}" />
            </div>
            <div class="input-group mb-2">
                <span class="input-group-text">C</span>
                <input type="text" class="form-control" name="answerOptionC" value="${options[2]}" />
            </div>
            <div class="input-group mb-2">
                <span class="input-group-text">D</span>
                <input type="text" class="form-control" name="answerOptionD" value="${options[3]}" />
            </div>
            <c:if test="${not empty optionsError}">
                <div class="text-danger">${optionsError}</div>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="correctAnswer">Correct Answer (A, B, C, or D)</label>
            <input type="text" class="form-control" id="correctAnswer" name="correctAnswer" value="${quiz.correctAnswer != null ? quiz.correctAnswer : param.correctAnswer}"  />
            <c:if test="${not empty correctAnswerError}">
                <div class="text-danger">${correctAnswerError}</div>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="explanation">Explanation</label>
            <textarea class="form-control" id="explanation" name="explanation" rows="3">${quiz.explanation != null ? quiz.explanation : param.explanation}</textarea>
            <c:if test="${not empty explanationError}">
                <div class="text-danger">${explanationError}</div>
            </c:if>
        </div>
        <button type="submit" class="btn btn-primary">${quiz != null && quiz.quizId != null ? "Update" : "Create"}</button>
        <a href="manageQuizInstructor?lessonId=${lessonId}&courseId=${courseId}" class="btn btn-secondary">Cancel</a>
    </form>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
