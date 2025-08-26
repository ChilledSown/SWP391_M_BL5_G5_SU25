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
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing lesson or course ID.");
                return;
            }
            long lessonId = Long.parseLong(lessonIdRaw);
            long courseId = Long.parseLong(courseIdRaw);

            Part filePart = request.getPart("importFile");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                if (!fileName.endsWith(".txt")) {
                    request.setAttribute("errorMessage", "Unsupported file format. Please use .txt.");
                    String referer = request.getHeader("Referer");
                    if (referer != null && referer.contains("manageQuizInstructor")) {
                        request.getRequestDispatcher("/manageQuizInstructor?lessonId=" + lessonId + "&courseId=" + courseId).forward(request, response);
                    } else {
                        request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
                    }
                    return;
                }
                importFromTxt(filePart, lessonId, courseId, request, response);
            } else {
                processManualInput(request, response, lessonId, courseId);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid lesson or course ID format.");
            request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating quiz: " + e.getMessage());
        }
    }

    private void importFromTxt(Part filePart, long lessonId, long courseId, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        QuizDAO dao = new QuizDAO();
        int lineCount = 0;
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
                    request.setAttribute("errorMessage", "At least two answer options are required in the file (line " + lineCount + ").");
                    String referer = request.getHeader("Referer");
                    if (referer != null && referer.contains("manageQuizInstructor")) {
                        request.getRequestDispatcher("/manageQuizInstructor?lessonId=" + lessonId + "&courseId=" + courseId).forward(request, response);
                    } else {
                        request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
                    }
                    return;
                }

                if (!correctAnswer.matches("[A-D]") ||
                    (correctAnswer.equals("A") && optionA.isEmpty()) ||
                    (correctAnswer.equals("B") && optionB.isEmpty()) ||
                    (correctAnswer.equals("C") && optionC.isEmpty()) ||
                    (correctAnswer.equals("D") && optionD.isEmpty())) {
                    request.setAttribute("errorMessage", "Invalid correct answer in the file (line " + lineCount + ").");
                    String referer = request.getHeader("Referer");
                    if (referer != null && referer.contains("manageQuizInstructor")) {
                        request.getRequestDispatcher("/manageQuizInstructor?lessonId=" + lessonId + "&courseId=" + courseId).forward(request, response);
                    } else {
                        request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
                    }
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
            request.setAttribute("errorMessage", "Error processing TXT file at line " + lineCount + ": " + e.getMessage());
            String referer = request.getHeader("Referer");
            if (referer != null && referer.contains("manageQuizInstructor")) {
                request.getRequestDispatcher("/manageQuizInstructor?lessonId=" + lessonId + "&courseId=" + courseId).forward(request, response);
            } else {
                request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
            }
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

        if (question == null || question.trim().isEmpty() ||
            correctAnswer == null || correctAnswer.trim().isEmpty() ||
            explanation == null || explanation.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Question, correct answer, and explanation are required.");
            forwardToForm(request, response, lessonId, courseId, 
                         question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
            return;
        }

        ArrayList<String> options = new ArrayList<>();
        if (answerOptionA != null && !answerOptionA.trim().isEmpty()) options.add(answerOptionA.trim());
        if (answerOptionB != null && !answerOptionB.trim().isEmpty()) options.add(answerOptionB.trim());
        if (answerOptionC != null && !answerOptionC.trim().isEmpty()) options.add(answerOptionC.trim());
        if (answerOptionD != null && !answerOptionD.trim().isEmpty()) options.add(answerOptionD.trim());

        if (options.size() < 2) {
            request.setAttribute("errorMessage", "At least two answer options are required.");
            forwardToForm(request, response, lessonId, courseId, 
                         question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
            return;
        }

        String correctAnswerUpper = correctAnswer.trim().toUpperCase();
        if (!correctAnswerUpper.matches("[A-D]") ||
            (correctAnswerUpper.equals("A") && (answerOptionA == null || answerOptionA.trim().isEmpty())) ||
            (correctAnswerUpper.equals("B") && (answerOptionB == null || answerOptionB.trim().isEmpty())) ||
            (correctAnswerUpper.equals("C") && (answerOptionC == null || answerOptionC.trim().isEmpty())) ||
            (correctAnswerUpper.equals("D") && (answerOptionD == null || answerOptionD.trim().isEmpty()))) {
            request.setAttribute("errorMessage", "Correct answer must be A, B, C, or D and correspond to a non-empty option.");
            forwardToForm(request, response, lessonId, courseId, 
                         question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
            return;
        }

        QuizDAO dao = new QuizDAO();
        if (dao.isQuestionDuplicate(question.trim(), lessonId)) {
            request.setAttribute("errorMessage", "This question already exists for the specified lesson.");
            forwardToForm(request, response, lessonId, courseId, 
                         question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
            return;
        }

        String answerOptions = String.join(";", options);
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

    private void forwardToForm(HttpServletRequest request, HttpServletResponse response, long lessonId, long courseId,
                              String question, String answerOptionA, String answerOptionB, String answerOptionC,
                              String answerOptionD, String correctAnswer, String explanation)
            throws ServletException, IOException {
        Quiz quiz = new Quiz();
        quiz.setLessonId(lessonId);
        quiz.setQuestion(question != null ? question : "");
        quiz.setAnswerOptions(String.join(";",
            answerOptionA != null ? answerOptionA : "",
            answerOptionB != null ? answerOptionB : "",
            answerOptionC != null ? answerOptionC : "",
            answerOptionD != null ? answerOptionD : ""));
        quiz.setCorrectAnswer(correctAnswer != null ? correctAnswer : "");
        quiz.setExplanation(explanation != null ? explanation : "");
        request.setAttribute("quiz", quiz);
        request.setAttribute("lessonId", lessonId);
        request.setAttribute("courseId", courseId);
        request.setAttribute("question", question);
        request.setAttribute("answerOptionA", answerOptionA);
        request.setAttribute("answerOptionB", answerOptionB);
        request.setAttribute("answerOptionC", answerOptionC);
        request.setAttribute("answerOptionD", answerOptionD);
        request.setAttribute("correctAnswer", correctAnswer);
        request.setAttribute("explanation", explanation);
        request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles creation of new quizzes with validation for duplicate questions and required fields, supporting .txt file imports";
    }
}