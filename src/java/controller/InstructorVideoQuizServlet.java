package controller;

import dal.InstructorVideoDAO;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Lesson;
import model.VideoQuiz;

@WebServlet(name = "InstructorVideoQuizServlet", urlPatterns = {"/instructorvideoquiz"})
public class InstructorVideoQuizServlet extends HttpServlet {

    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InstructorVideoDAO dao = new InstructorVideoDAO();
        try {
            List<Lesson> lessons = dao.getAllLessons();
            request.setAttribute("lessons", lessons);

            String action = request.getParameter("action");
            String videoQuizIdStr = request.getParameter("videoQuizId");
            Long videoQuizId = (videoQuizIdStr != null && !videoQuizIdStr.isEmpty()) ? Long.parseLong(videoQuizIdStr) : null;

            if (action != null) {
                if ("edit".equals(action) && videoQuizId != null) {
                    VideoQuiz videoQuiz = dao.getVideoQuizById(videoQuizId);
                    System.out.println("Edit action: videoQuizId=" + videoQuizId + ", videoQuiz=" + (videoQuiz != null ? videoQuiz : "null"));
                    if (videoQuiz != null) {
                        String correctAnswer = videoQuiz.getCorrectAnswer();
                        String correctAnswerLetter = (correctAnswer != null && correctAnswer.length() >= 2 && 
                            (correctAnswer.startsWith("A.") || correctAnswer.startsWith("B.") || 
                             correctAnswer.startsWith("C.") || correctAnswer.startsWith("D."))) 
                            ? correctAnswer.substring(0, 1) : "";
                        request.setAttribute("correctAnswerLetter", correctAnswerLetter);
                        request.setAttribute("videoQuiz", videoQuiz);
                        request.getRequestDispatcher("instructorvideoquizedit.jsp").forward(request, response);
                    } else {
                        request.setAttribute("error", "Video quiz not found.");
                        request.getRequestDispatcher("instructorvideoquiz.jsp").forward(request, response);
                    }
                    return;
                } else if ("view".equals(action) && videoQuizId != null) {
                    VideoQuiz videoQuiz = dao.getVideoQuizById(videoQuizId);
                    System.out.println("View action: videoQuizId=" + videoQuizId + ", videoQuiz=" + (videoQuiz != null ? videoQuiz : "null"));
                    if (videoQuiz != null) {
                        request.setAttribute("videoQuiz", videoQuiz);
                        request.getRequestDispatcher("instructorvideoquizdetail.jsp").forward(request, response);
                    } else {
                        request.setAttribute("error", "Video quiz not found.");
                        request.getRequestDispatcher("instructorvideoquiz.jsp").forward(request, response);
                    }
                    return;
                } else if ("create".equals(action)) {
                    request.getRequestDispatcher("instructorvideoquizcreate.jsp").forward(request, response);
                    return;
                } else if ("delete".equals(action) && videoQuizId != null) {
                    System.out.println("Delete action: videoQuizId=" + videoQuizId);
                    if (dao.deleteVideoQuiz(videoQuizId)) {
                        request.setAttribute("message", "Video quiz deleted successfully.");
                    } else {
                        request.setAttribute("error", "Failed to delete video quiz.");
                    }
                }
            }

            // Default: List with search and pagination
            String question = request.getParameter("title");
            String lessonIdStr = request.getParameter("lessonId");
            Long lessonId = (lessonIdStr != null && !lessonIdStr.isEmpty()) ? Long.parseLong(lessonIdStr) : null;
            String pageStr = request.getParameter("page");
            int page = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);

            List<VideoQuiz> quizzes = dao.searchVideoQuizzes(question, lessonId, page, PAGE_SIZE);
            int totalQuizzes = dao.getTotalVideoQuizzes(question, lessonId);
            int totalPages = (int) Math.ceil((double) totalQuizzes / PAGE_SIZE);

            System.out.println("List action: question=" + question + ", lessonId=" + lessonId + ", page=" + page + ", quizzes=" + quizzes.size());
            request.setAttribute("videoQuizzes", quizzes);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("instructorvideoquiz.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error in doGet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing your request.");
            request.getRequestDispatcher("instructorvideoquiz.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InstructorVideoDAO dao = new InstructorVideoDAO();
        try {
            String action = request.getParameter("action");

            // Retrieve form parameters
            String lessonIdStr = request.getParameter("lessonId");
            String timestampStr = request.getParameter("timestamp");
            String question = request.getParameter("question");
            String answerOptions = request.getParameter("answerOptions");
            String correctAnswerLetter = request.getParameter("correctAnswerLetter");
            String correctAnswer = request.getParameter("correctAnswer");
            String answerOptionA = request.getParameter("answerOptionA");
            String answerOptionB = request.getParameter("answerOptionB");
            String answerOptionC = request.getParameter("answerOptionC");
            String answerOptionD = request.getParameter("answerOptionD");
            String explanation = request.getParameter("explanation");

            // Validate required fields
            if (lessonIdStr == null || lessonIdStr.trim().isEmpty()) {
                handleValidationError(request, response, action, "Lesson ID is required.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                return;
            }
            if (timestampStr == null || timestampStr.trim().isEmpty()) {
                handleValidationError(request, response, action, "Timestamp is required.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                return;
            }
            if (question == null || question.trim().isEmpty()) {
                handleValidationError(request, response, action, "Question is required.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                return;
            }

            // Parse numeric fields
            Long lessonId;
            Integer timestamp;
            try {
                lessonId = Long.parseLong(lessonIdStr.trim());
                timestamp = Integer.parseInt(timestampStr.trim());
            } catch (NumberFormatException e) {
                handleValidationError(request, response, action, "Invalid number format for lesson ID or timestamp.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                return;
            }

            // Normalize and validate answerOptions and correctAnswer
            String normalizedAnswerOptions = answerOptions != null ? answerOptions.trim().replaceAll("\\s*;\\s*", "; ") : "";
            String normalizedCorrectAnswer = correctAnswer != null ? correctAnswer.trim() : "";
            boolean optionsModified = (answerOptionA != null && !answerOptionA.trim().isEmpty()) ||
                                     (answerOptionB != null && !answerOptionB.trim().isEmpty()) ||
                                     (answerOptionC != null && !answerOptionC.trim().isEmpty()) ||
                                     (answerOptionD != null && !answerOptionD.trim().isEmpty());

            if ("edit".equals(action)) {
                String videoQuizIdStr = request.getParameter("videoQuizId");
                Long videoQuizId;
                try {
                    videoQuizId = Long.parseLong(videoQuizIdStr.trim());
                } catch (NumberFormatException e) {
                    handleValidationError(request, response, action, "Invalid video quiz ID format.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                    return;
                }
                VideoQuiz existingQuiz = dao.getVideoQuizById(videoQuizId);
                if (existingQuiz == null) {
                    handleValidationError(request, response, action, "Video quiz not found.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                    return;
                }

                if (optionsModified) {
                    // Validate modified answer options
                    if (normalizedAnswerOptions.isEmpty()) {
                        handleValidationError(request, response, action, "Answer options are required when modifying answers.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                        return;
                    }
                    if (correctAnswerLetter == null || correctAnswerLetter.trim().isEmpty()) {
                        handleValidationError(request, response, action, "Correct answer letter is required when modifying answers.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                        return;
                    }
                    if (normalizedCorrectAnswer.isEmpty()) {
                        handleValidationError(request, response, action, "Correct answer is required when modifying answers.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                        return;
                    }

                    List<String> options = Arrays.asList(normalizedAnswerOptions.split("; "));
                    if (options.size() < 2) {
                        handleValidationError(request, response, action, "At least two answer options are required when modifying answers.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                        return;
                    }
                    String normalizedCorrectAnswerLetter = correctAnswerLetter.trim().toUpperCase();
                    if (!Arrays.asList("A", "B", "C", "D").contains(normalizedCorrectAnswerLetter)) {
                        handleValidationError(request, response, action, "Correct answer letter must be A, B, C, or D.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                        return;
                    }
                    boolean validCorrectAnswer = false;
                    for (String option : options) {
                        if (option.startsWith(normalizedCorrectAnswerLetter + ". ") && normalizedCorrectAnswer.equals(option)) {
                            validCorrectAnswer = true;
                            break;
                        }
                    }
                    if (!validCorrectAnswer) {
                        handleValidationError(request, response, action, "Correct answer does not match any provided option.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                        return;
                    }
                } else {
                    // Use existing quiz's answer options and correct answer
                    normalizedAnswerOptions = existingQuiz.getAnswerOptions();
                    normalizedCorrectAnswer = existingQuiz.getCorrectAnswer();
                }

                // Always set isActive to true
                boolean isActive = true;

                // Create VideoQuiz object
                VideoQuiz quiz = new VideoQuiz(videoQuizId, lessonId, timestamp, question.trim(), normalizedAnswerOptions, normalizedCorrectAnswer, explanation != null ? explanation.trim() : null, isActive, null, null);

                System.out.println("Edit action: quiz=" + quiz);
                if (dao.updateVideoQuiz(quiz)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("success:Video quiz updated successfully.");
                } else {
                    handleValidationError(request, response, action, "Failed to update video quiz.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                }
            } else if ("create".equals(action)) {
                // For create action, validate all fields as before
                if (normalizedAnswerOptions.isEmpty()) {
                    handleValidationError(request, response, action, "Answer options are required.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                    return;
                }
                if (correctAnswerLetter == null || correctAnswerLetter.trim().isEmpty()) {
                    handleValidationError(request, response, action, "Correct answer letter is required.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                    return;
                }
                if (normalizedCorrectAnswer.isEmpty()) {
                    handleValidationError(request, response, action, "Correct answer is required.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                    return;
                }
                List<String> options = Arrays.asList(normalizedAnswerOptions.split("; "));
                if (options.size() < 2) {
                    handleValidationError(request, response, action, "At least two answer options are required.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                    return;
                }
                String normalizedCorrectAnswerLetter = correctAnswerLetter.trim().toUpperCase();
                if (!Arrays.asList("A", "B", "C", "D").contains(normalizedCorrectAnswerLetter)) {
                    handleValidationError(request, response, action, "Correct answer letter must be A, B, C, or D.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                    return;
                }
                boolean validCorrectAnswer = false;
                for (String option : options) {
                    if (option.startsWith(normalizedCorrectAnswerLetter + ". ") && normalizedCorrectAnswer.equals(option)) {
                        validCorrectAnswer = true;
                        break;
                    }
                }
                if (!validCorrectAnswer) {
                    handleValidationError(request, response, action, "Correct answer does not match any provided option.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
                    return;
                }

                // Always set isActive to true
                boolean isActive = true;

                // Create VideoQuiz object
                VideoQuiz quiz = new VideoQuiz(null, lessonId, timestamp, question.trim(), normalizedAnswerOptions, normalizedCorrectAnswer, explanation != null ? explanation.trim() : null, isActive, null, null);

                System.out.println("Create action: quiz=" + quiz);
                if (dao.insertVideoQuiz(quiz)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("success:Video quiz created successfully.");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("error:Failed to create video quiz.");
                }
            } else {
                handleValidationError(request, response, action, "Invalid action.", lessonIdStr, timestampStr, question, answerOptions, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswerLetter, explanation);
            }
        } catch (Exception e) {
            System.err.println("Error in doPost: " + e.getMessage());
            e.printStackTrace();
            handleValidationError(request, response, request.getParameter("action"), 
                "An error occurred while processing your request: " + e.getMessage(), 
                request.getParameter("lessonId"), request.getParameter("timestamp"), request.getParameter("question"), 
                request.getParameter("answerOptions"), request.getParameter("answerOptionA"), request.getParameter("answerOptionB"), 
                request.getParameter("answerOptionC"), request.getParameter("answerOptionD"), request.getParameter("correctAnswerLetter"), 
                request.getParameter("explanation"));
        }
    }

    private void handleValidationError(HttpServletRequest request, HttpServletResponse response, String action, 
            String errorMessage, String lessonId, String timestamp, String question, String answerOptions, 
            String answerOptionA, String answerOptionB, String answerOptionC, String answerOptionD, 
            String correctAnswerLetter, String explanation) 
            throws ServletException, IOException {
        List<Lesson> lessons = new InstructorVideoDAO().getAllLessons();
        request.setAttribute("lessons", lessons);
        request.setAttribute("error", errorMessage);
        request.setAttribute("submittedLessonId", lessonId);
        request.setAttribute("submittedTimestamp", timestamp);
        request.setAttribute("submittedQuestion", question);
        request.setAttribute("submittedAnswerOptions", answerOptions);
        request.setAttribute("submittedAnswerOptionA", answerOptionA != null && !answerOptionA.trim().isEmpty() ? answerOptionA : null);
        request.setAttribute("submittedAnswerOptionB", answerOptionB != null && !answerOptionB.trim().isEmpty() ? answerOptionB : null);
        request.setAttribute("submittedAnswerOptionC", answerOptionC != null && !answerOptionC.trim().isEmpty() ? answerOptionC : null);
        request.setAttribute("submittedAnswerOptionD", answerOptionD != null && !answerOptionD.trim().isEmpty() ? answerOptionD : null);
        request.setAttribute("correctAnswerLetter", correctAnswerLetter);
        request.setAttribute("submittedExplanation", explanation);

        if ("edit".equals(action)) {
            String videoQuizIdStr = request.getParameter("videoQuizId");
            if (videoQuizIdStr != null && !videoQuizIdStr.trim().isEmpty()) {
                try {
                    Long videoQuizId = Long.parseLong(videoQuizIdStr.trim());
                    VideoQuiz videoQuiz = new InstructorVideoDAO().getVideoQuizById(videoQuizId);
                    if (videoQuiz != null) {
                        request.setAttribute("videoQuiz", videoQuiz);
                        request.setAttribute("originalCorrectAnswer", videoQuiz.getCorrectAnswer());
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid video quiz ID format.");
                }
            }
            request.getRequestDispatcher("instructorvideoquizedit.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("instructorvideoquizcreate.jsp").forward(request, response);
        }
    }
}