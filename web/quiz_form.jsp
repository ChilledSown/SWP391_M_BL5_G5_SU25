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
    <form method="post" action="${quiz != null ? 'editQuizSeller' : 'createQuizSeller'}"
          ${quiz == null ? 'enctype="multipart/form-data"' : ''}>
        <input type="hidden" name="lessonId" value="${lessonId}" />
        <input type="hidden" name="courseId" value="${courseId}" />
        <c:if test="${quiz != null}">
            <input type="hidden" name="quizId" value="${quiz.quizId}" />
        </c:if>
        <c:if test="${quiz == null}">
            <div class="mb-3">
                <label for="importFile">Import Questions from File (.txt)</label>
                <input type="file" class="form-control" id="importFile" name="importFile" accept=".txt" />
            </div>
        </c:if>
        <c:if test="${quiz != null || empty param.importFile}">
            <div class="mb-3">
                <label for="question">Question</label>
                <input type="text" class="form-control" id="question" name="question" value="${quiz != null ? quiz.question : param.question}" required />
            </div>
            <div class="mb-3">
                <label>Answer Options (at least two required)</label>
                <c:set var="options" value="${quiz != null ? quiz.answerOptions.split(';') : ['', '', '', '']}" />
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
            </div>
            <div class="mb-3">
                <label for="correctAnswer">Correct Answer (A, B, C, or D)</label>
                <input type="text" class="form-control" id="correctAnswer" name="correctAnswer" value="${quiz != null ? quiz.correctAnswer : param.correctAnswer}" required />
            </div>
            <div class="mb-3">
                <label for="explanation">Explanation</label>
                <textarea class="form-control" id="explanation" name="explanation" rows="3" required>${quiz != null ? quiz.explanation : param.explanation}</textarea>
            </div>
        </c:if>
        <button type="submit" class="btn btn-primary">${quiz != null ? "Update" : "Create"}</button>
        <a href="manageQuizInstructor?lessonId=${lessonId}&courseId=${courseId}" class="btn btn-secondary">Cancel</a>
    </form>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>