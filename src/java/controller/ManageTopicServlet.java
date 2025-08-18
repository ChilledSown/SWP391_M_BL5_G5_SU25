package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.TopicDAO;
import dal.CourseDAO;
import model.Topic;
import model.Course;
import java.util.List;

@WebServlet(name="ManageTopicServlet", urlPatterns={"/managetopic"})
public class ManageTopicServlet extends HttpServlet {

    private final int PAGE_SIZE = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TopicDAO tdao = new TopicDAO();

        String action = request.getParameter("action");

        // Handle the "details" action
        if ("details".equals(action)) {
            try {
                long topicId = Long.parseLong(request.getParameter("topicId"));
                Topic topic = tdao.getTopicById(topicId);
                if (topic == null) {
                    request.setAttribute("error", "Topic not found");
                    request.getRequestDispatcher("managetopic.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("topic", topic);
                request.getRequestDispatcher("topicDetails.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid topic ID");
                request.getRequestDispatcher("managetopic.jsp").forward(request, response);
            }
            return;
        }

        // Handle the "view" action (for courses)
        if ("view".equals(action)) {
            try {
                long topicId = Long.parseLong(request.getParameter("topicId"));
                Topic topic = tdao.getTopicById(topicId);
                if (topic == null) {
                    request.setAttribute("error", "Topic not found");
                    request.getRequestDispatcher("managetopic.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("topic", topic);
                CourseDAO cdao = new CourseDAO();
                List<Course> courses = cdao.getCoursesByTopicId(topicId);
                request.setAttribute("courses", courses);
                request.setAttribute("topicId", topicId);
                request.getRequestDispatcher("managecourse.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid topic ID");
                request.getRequestDispatcher("managetopic.jsp").forward(request, response);
            }
            return;
        }

        // Handle topic listing with pagination and search
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                // Default to page 1 if invalid
            }
        }

        String searchQuery = request.getParameter("query");
        List<Topic> topics;
        int totalTopics;
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            topics = tdao.getTopicsWithPagination(searchQuery, page, PAGE_SIZE);
            totalTopics = tdao.getTotalTopics(searchQuery);
        } else {
            topics = tdao.getTopicsWithPagination(null, page, PAGE_SIZE);
            totalTopics = tdao.getTotalTopics(null);
        }
        int totalPages = (int) Math.ceil((double) totalTopics / PAGE_SIZE);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) {
            page = totalPages;
            topics = tdao.getTopicsWithPagination(searchQuery, page, PAGE_SIZE);
        }

        request.setAttribute("topics", topics);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchQuery", searchQuery);

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }
        request.getRequestDispatcher("managetopic.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Manages topic-related operations for the admin dashboard";
    }
}