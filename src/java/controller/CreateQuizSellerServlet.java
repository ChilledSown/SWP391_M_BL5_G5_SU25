package controller;

import dal.QuizDAO;
import model.Quiz;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import model.User;

@WebServlet(name = "CreateQuizSellerServlet", urlPatterns = {"/createQuizSeller"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 10 * 1024 * 1024, // 10MB
    maxRequestSize = 50 * 1024 * 1024 // 50MB
)
public class CreateQuizSellerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
User user = (User) session.getAttribute("user");
if (user == null || !"instructor".equalsIgnoreCase(user.getRole())) {
    response.sendRedirect("login.jsp");
    return;
}
        String lessonIdRaw = request.getParameter("lessonId");
        String courseIdRaw = request.getParameter("courseId");
        if (lessonIdRaw == null || lessonIdRaw.trim().isEmpty() ||
            courseIdRaw == null || courseIdRaw.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing lesson or course ID.");
            return;
        }
        try {
            long lessonId = Long.parseLong(lessonIdRaw);
            long courseId = Long.parseLong(courseIdRaw);

            // Kiểm tra session để lấy errors và values nếu có
           
            if (session.getAttribute("questionError") != null) {
                request.setAttribute("questionError", session.getAttribute("questionError"));
                session.removeAttribute("questionError");
            }
            if (session.getAttribute("optionsError") != null) {
                request.setAttribute("optionsError", session.getAttribute("optionsError"));
                session.removeAttribute("optionsError");
            }
            if (session.getAttribute("correctAnswerError") != null) {
                request.setAttribute("correctAnswerError", session.getAttribute("correctAnswerError"));
                session.removeAttribute("correctAnswerError");
            }
            if (session.getAttribute("explanationError") != null) {
                request.setAttribute("explanationError", session.getAttribute("explanationError"));
                session.removeAttribute("explanationError");
            }
            if (session.getAttribute("errorMessage") != null) {
                request.setAttribute("errorMessage", session.getAttribute("errorMessage"));
                session.removeAttribute("errorMessage");
            }

            // Lấy values từ session để điền vào form
            request.setAttribute("question", session.getAttribute("question"));
            request.setAttribute("answerOptionA", session.getAttribute("answerOptionA"));
            request.setAttribute("answerOptionB", session.getAttribute("answerOptionB"));
            request.setAttribute("answerOptionC", session.getAttribute("answerOptionC"));
            request.setAttribute("answerOptionD", session.getAttribute("answerOptionD"));
            request.setAttribute("correctAnswer", session.getAttribute("correctAnswer"));
            request.setAttribute("explanation", session.getAttribute("explanation"));

            // Remove session attributes
            session.removeAttribute("question");
            session.removeAttribute("answerOptionA");
            session.removeAttribute("answerOptionB");
            session.removeAttribute("answerOptionC");
            session.removeAttribute("answerOptionD");
            session.removeAttribute("correctAnswer");
            session.removeAttribute("explanation");

            request.setAttribute("lessonId", lessonId);
            request.setAttribute("courseId", courseId);
            request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid lesson or course ID format.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String lessonIdRaw = request.getParameter("lessonId");
            String courseIdRaw = request.getParameter("courseId");
            if (lessonIdRaw == null || lessonIdRaw.trim().isEmpty() ||
                courseIdRaw == null || courseIdRaw.trim().isEmpty()) {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "Lesson ID and course ID are required.");
                response.sendRedirect("createQuizSeller?lessonId=" + (lessonIdRaw != null ? lessonIdRaw : "") + "&courseId=" + (courseIdRaw != null ? courseIdRaw : ""));
                return;
            }
            long lessonId = Long.parseLong(lessonIdRaw);
            long courseId = Long.parseLong(courseIdRaw);
            Part filePart = request.getPart("importFile");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                if (!fileName.endsWith(".txt")) {
                    HttpSession session = request.getSession();
                    session.setAttribute("errorMessage", "Unsupported file format. Please use .txt.");
                    response.sendRedirect("createQuizSeller?lessonId=" + lessonId + "&courseId=" + courseId);
                    return;
                }
                importFromTxt(filePart, lessonId, courseId, request, response);
            } else {
                processManualInput(request, response, lessonId, courseId);
            }
        } catch (NumberFormatException e) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Invalid lesson or course ID format.");
            String lessonIdRaw = request.getParameter("lessonId");
            String courseIdRaw = request.getParameter("courseId");
            response.sendRedirect("createQuizSeller?lessonId=" + (lessonIdRaw != null ? lessonIdRaw : "") + "&courseId=" + (courseIdRaw != null ? courseIdRaw : ""));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating quiz: " + e.getMessage());
        }
    }

    private void importFromTxt(Part filePart, long lessonId, long courseId, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        QuizDAO dao = new QuizDAO();
        int lineCount = 0;
        HttpSession session = request.getSession();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                System.out.println("Processing line " + lineCount + ": " + line);
                String[] parts = line.split(";", -1);
                if (parts.length < 7) {
                    System.out.println("Invalid format at line " + lineCount + ": " + line);
                    continue;
                }
                String question = parts[0].trim();
                String optionA = parts[1].trim();
                String optionB = parts[2].trim();
                String optionC = parts[3].trim();
                String optionD = parts[4].trim();
                String correctAnswer = parts[5].trim().toUpperCase();
                String explanation = parts[6].trim();
                if (question.isEmpty() || correctAnswer.isEmpty() || explanation.isEmpty()) {
                    System.out.println("Skipping line " + lineCount + ": Missing required fields.");
                    continue;
                }
                ArrayList<String> options = new ArrayList<>();
                if (!optionA.isEmpty()) options.add(optionA);
                if (!optionB.isEmpty()) options.add(optionB);
                if (!optionC.isEmpty()) options.add(optionC);
                if (!optionD.isEmpty()) options.add(optionD);
                if (options.size() < 2) {
                    session.setAttribute("errorMessage", "At least two answer options are required in the file (line " + lineCount + ").");
                    response.sendRedirect("createQuizSeller?lessonId=" + lessonId + "&courseId=" + courseId);
                    return;
                }
                Set<String> uniqueOptions = new HashSet<>();
                boolean hasDuplicate = false;
                for (String option : options) {
                    if (!uniqueOptions.add(option.toLowerCase())) {
                        hasDuplicate = true;
                        break;
                    }
                }
                if (hasDuplicate) {
                    session.setAttribute("errorMessage", "Answer options must not be duplicates in the file (line " + lineCount + ").");
                    response.sendRedirect("createQuizSeller?lessonId=" + lessonId + "&courseId=" + courseId);
                    return;
                }
                if (!correctAnswer.matches("[A-D]") ||
                    (correctAnswer.equals("A") && optionA.isEmpty()) ||
                    (correctAnswer.equals("B") && optionB.isEmpty()) ||
                    (correctAnswer.equals("C") && optionC.isEmpty()) ||
                    (correctAnswer.equals("D") && optionD.isEmpty())) {
                    session.setAttribute("errorMessage", "Invalid correct answer in the file (line " + lineCount + ").");
                    response.sendRedirect("createQuizSeller?lessonId=" + lessonId + "&courseId=" + courseId);
                    return;
                }
                if (dao.isQuestionDuplicate(question, lessonId)) {
                    System.out.println("Skipping duplicate question at line " + lineCount + ": " + question);
                    continue;
                }
                String answerOptions = String.join(";", options);
                Quiz quiz = new Quiz();
                quiz.setQuestion(question);
                quiz.setAnswerOptions(answerOptions);
                quiz.setCorrectAnswer(correctAnswer);
                quiz.setExplanation(explanation);
                quiz.setLessonId(lessonId);
                quiz.setCreatedAt(new Date());
                quiz.setUpdatedAt(new Date());
                dao.createQuiz(quiz);
                System.out.println("Imported question: " + question);
            }
            System.out.println("Total lines processed: " + lineCount);
            response.sendRedirect("manageQuizInstructor?lessonId=" + lessonId + "&courseId=" + courseId);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error processing TXT file at line " + lineCount + ": " + e.getMessage());
            response.sendRedirect("createQuizSeller?lessonId=" + lessonId + "&courseId=" + courseId);
        }
    }

    private void processManualInput(HttpServletRequest request, HttpServletResponse response, long lessonId, long courseId)
            throws ServletException, IOException {
        String question = request.getParameter("question");
        String answerOptionA = request.getParameter("answerOptionA");
        String answerOptionB = request.getParameter("answerOptionB");
        String answerOptionC = request.getParameter("answerOptionC");
        String answerOptionD = request.getParameter("answerOptionD");
        String correctAnswer = request.getParameter("correctAnswer");
        String explanation = request.getParameter("explanation");

        System.out.println("CreateQuizSellerServlet - Parameters:");
        System.out.println("question: " + question);
        System.out.println("answerOptionA: " + answerOptionA);
        System.out.println("answerOptionB: " + answerOptionB);
        System.out.println("answerOptionC: " + answerOptionC);
        System.out.println("answerOptionD: " + answerOptionD);
        System.out.println("correctAnswer: " + correctAnswer);
        System.out.println("explanation: " + explanation);
        System.out.println("lessonId: " + lessonId);
        System.out.println("courseId: " + courseId);

        boolean hasError = false;
        HttpSession session = request.getSession();

        // Validate question
        if (question == null || question.trim().isEmpty()) {
            session.setAttribute("questionError", "Question is required.");
            hasError = true;
        }

        // Validate answer options (at least two non-empty, no duplicates)
        ArrayList<String> options = new ArrayList<>();
        if (answerOptionA != null && !answerOptionA.trim().isEmpty()) options.add(answerOptionA.trim());
        if (answerOptionB != null && !answerOptionB.trim().isEmpty()) options.add(answerOptionB.trim());
        if (answerOptionC != null && !answerOptionC.trim().isEmpty()) options.add(answerOptionC.trim());
        if (answerOptionD != null && !answerOptionD.trim().isEmpty()) options.add(answerOptionD.trim());

        if (options.size() < 2) {
            session.setAttribute("optionsError", "At least two answer options are required.");
            hasError = true;
        } else {
            Set<String> uniqueOptions = new HashSet<>();
            for (String option : options) {
                if (!uniqueOptions.add(option.toLowerCase())) {
                    session.setAttribute("optionsError", "Answer options must not be duplicates.");
                    hasError = true;
                    break;
                }
            }
        }

        // Validate correct answer
        if (correctAnswer == null || correctAnswer.trim().isEmpty()) {
            session.setAttribute("correctAnswerError", "Correct answer is required.");
            hasError = true;
        } else {
            String correctAnswerUpper = correctAnswer.trim().toUpperCase();
            if (!correctAnswerUpper.matches("[A-D]")) {
                session.setAttribute("correctAnswerError", "Correct answer must be A, B, C, or D.");
                hasError = true;
            } else if ((correctAnswerUpper.equals("A") && (answerOptionA == null || answerOptionA.trim().isEmpty())) ||
                       (correctAnswerUpper.equals("B") && (answerOptionB == null || answerOptionB.trim().isEmpty())) ||
                       (correctAnswerUpper.equals("C") && (answerOptionC == null || answerOptionC.trim().isEmpty())) ||
                       (correctAnswerUpper.equals("D") && (answerOptionD == null || answerOptionD.trim().isEmpty()))) {
                session.setAttribute("correctAnswerError", "Correct answer must correspond to a non-empty option.");
                hasError = true;
            }
        }

        // Validate explanation
        if (explanation == null || explanation.trim().isEmpty()) {
            session.setAttribute("explanationError", "Explanation is required.");
            hasError = true;
        }

        // Validate duplicate question
        QuizDAO dao = new QuizDAO();
        if (question != null && !question.trim().isEmpty() && dao.isQuestionDuplicate(question.trim(), lessonId)) {
            session.setAttribute("questionError", "This question already exists for the specified lesson.");
            hasError = true;
        }

        if (hasError) {
            // Lưu values vào session
            session.setAttribute("question", question);
            session.setAttribute("answerOptionA", answerOptionA);
            session.setAttribute("answerOptionB", answerOptionB);
            session.setAttribute("answerOptionC", answerOptionC);
            session.setAttribute("answerOptionD", answerOptionD);
            session.setAttribute("correctAnswer", correctAnswer);
            session.setAttribute("explanation", explanation);

            response.sendRedirect("createQuizSeller?lessonId=" + lessonId + "&courseId=" + courseId);
            return;
        }

        // Nếu không lỗi, tạo quiz
        String answerOptions = String.join(";", options);
        String correctAnswerUpper = correctAnswer.trim().toUpperCase();
        Quiz quiz = new Quiz();
        quiz.setQuestion(question.trim());
        quiz.setAnswerOptions(answerOptions);
        quiz.setCorrectAnswer(correctAnswerUpper);
        quiz.setExplanation(explanation.trim());
        quiz.setLessonId(lessonId);
        quiz.setCreatedAt(new Date());
        quiz.setUpdatedAt(new Date());
        dao.createQuiz(quiz);
        response.sendRedirect("manageQuizInstructor?lessonId=" + lessonId + "&courseId=" + courseId);
    }

    @Override
    public String getServletInfo() {
    return "Handles creation of new quizzes with validation for duplicate questions and required fields, supporting .txt file imports";
    }
}