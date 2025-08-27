package controller;

import dal.LessonDAO;
import dal.QuizDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Lesson;
import model.Quiz;
import model.User;

@WebServlet(name = "ManageQuizServlet", urlPatterns = {"/manageQuizInstructor"})
public class ManageQuizServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("manageQuizInstructor.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lessonIdRaw = request.getParameter("lessonId");
        String courseIdRaw = request.getParameter("courseId");

        HttpSession session = request.getSession();
User user = (User) session.getAttribute("user");
if (user == null || !"instructor".equalsIgnoreCase(user.getRole())) {
    response.sendRedirect("login.jsp");
    return;
}
        // Kiểm tra lessonId
        if (lessonIdRaw == null || lessonIdRaw.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Missing lessonId parameter.");
            request.getRequestDispatcher("manageQuizInstructor.jsp").forward(request, response);
            return;
        }

        // Kiểm tra courseId
        if (courseIdRaw == null || courseIdRaw.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Missing courseId parameter.");
            request.getRequestDispatcher("manageQuizInstructor.jsp").forward(request, response);
            return;
        }

        long lessonId, courseId;
        try {
            lessonId = Long.parseLong(lessonIdRaw);
            courseId = Long.parseLong(courseIdRaw);
            if (lessonId <= 0 || courseId <= 0) {
                request.setAttribute("errorMessage", "Invalid Lesson ID or Course ID.");
                request.getRequestDispatcher("manageQuizInstructor.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Lesson ID or Course ID format.");
            request.getRequestDispatcher("manageQuizInstructor.jsp").forward(request, response);
            return;
        }

        // Xác minh lesson thuộc course
        LessonDAO lessonDAO = new LessonDAO();
        Lesson lesson = lessonDAO.getLessonById(lessonId);
        if (lesson == null || lesson.getCourseId() != courseId) {
            request.setAttribute("errorMessage", "Lesson does not belong to the specified course.");
            request.getRequestDispatcher("manageQuizInstructor.jsp").forward(request, response);
            return;
        }

        // Lấy tham số tìm kiếm và phân trang
        String question = request.getParameter("question");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        int page = 1;
        int pageSize = 5; // Số lượng quiz mỗi trang
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Math.max(1, Integer.parseInt(pageParam));
            } catch (NumberFormatException ignored) {
            }
        }
        int offset = (page - 1) * pageSize;

        QuizDAO dao = new QuizDAO();
        List<Quiz> quizzes = dao.getFilteredQuizzesByLessonPaged(lessonId, question, startDate, endDate, offset, pageSize);
        int totalItems = dao.countFilteredQuizzesByLesson(lessonId, question, startDate, endDate);
        int totalPages = (int) Math.ceil(totalItems / (double) pageSize);

        // Truyền attribute
        request.setAttribute("lessonId", lessonId);
        request.setAttribute("courseId", courseId);
        request.setAttribute("quizzes", quizzes);
        request.setAttribute("question", question);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Tạo baseUrl để phân trang giữ lại filter
        StringBuilder baseUrl = new StringBuilder("manageQuizInstructor?lessonId=" + lessonId + "&courseId=" + courseId);
        if (question != null && !question.isEmpty()) {
            baseUrl.append("&question=").append(java.net.URLEncoder.encode(question, java.nio.charset.StandardCharsets.UTF_8));
        }
        if (startDate != null && !startDate.isEmpty()) {
            baseUrl.append("&startDate=").append(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            baseUrl.append("&endDate=").append(endDate);
        }
        request.setAttribute("baseUrl", baseUrl.toString());

        request.getRequestDispatcher("manageQuizInstructor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Manages quizzes for a specific lesson.";
    }
}