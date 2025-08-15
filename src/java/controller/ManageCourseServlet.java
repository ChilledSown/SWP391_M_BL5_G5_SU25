package controller;

import dal.TopicDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.Topic;
import model.Course;

@WebServlet(name="ManageCourseServlet", urlPatterns={"/managecourse"})
public class ManageCourseServlet extends HttpServlet {

    private final int PAGE_SIZE = 4; // 4 records per page

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TopicDAO topicDAO = new TopicDAO();
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            long topicId = Long.parseLong(request.getParameter("topicId"));
            Topic topic = topicDAO.getTopicById(topicId);
            request.setAttribute("topic", topic);
            request.getRequestDispatcher("updatetopic.jsp").forward(request, response);
            return;
        } else if ("add".equals(action)) {
            request.removeAttribute("topic");
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute("topic");
            }
            request.getRequestDispatcher("addtopic.jsp").forward(request, response);
            return;
        } else if ("view".equals(action)) {
            long topicId = Long.parseLong(request.getParameter("topicId"));
            Topic topic = topicDAO.getTopicById(topicId);
            List<Course> courses = topicDAO.getCoursesByTopicId(topicId);
            request.setAttribute("topic", topic);
            request.setAttribute("courses", courses);
            request.getRequestDispatcher("viewtopic.jsp").forward(request, response);
            return;
        }

        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                // Default to page 1 if invalid
            }
        }

        String query = request.getParameter("query");
        List<Topic> topics;
        if (query != null && !query.trim().isEmpty()) {
            topics = topicDAO.searchTopics(query.trim(), page, PAGE_SIZE);
        } else {
            topics = topicDAO.getAllTopics(page, PAGE_SIZE);
        }

        int totalTopics = topicDAO.getTotalTopics();
        int totalPages = (int) Math.ceil((double) totalTopics / PAGE_SIZE);

        request.setAttribute("topics", topics);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("query", query);

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }

        request.getRequestDispatcher("managecourse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TopicDAO topicDAO = new TopicDAO();
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            long topicId = Long.parseLong(request.getParameter("topicId"));
            String result = topicDAO.deleteTopic(topicId);
            request.getSession().setAttribute("message", result);
            response.sendRedirect("managecourse");
        } else if ("update".equals(action)) {
            String topicIdStr = request.getParameter("topicId");
            String name = request.getParameter("name");
            String thumbnailUrl = request.getParameter("thumbnailUrl");
            String description = request.getParameter("description");

            String error = null;
            if (name == null || name.trim().isEmpty()) {
                error = "Topic name cannot be empty.";
            }

            if (error != null) {
                Topic topic = new Topic();
                topic.setTopic_id(Long.parseLong(topicIdStr));
                topic.setName(name);
                topic.setThumbnail_Url(thumbnailUrl);
                topic.setDescription(description);
                request.setAttribute("topic", topic);
                request.setAttribute("error", error);
                request.getRequestDispatcher("updatetopic.jsp").forward(request, response);
                return;
            }

            Topic topic = new Topic();
            topic.setTopic_id(Long.parseLong(topicIdStr));
            topic.setName(name);
            topic.setThumbnail_Url(thumbnailUrl);
            topic.setDescription(description);

            boolean success = topicDAO.updateTopic(topic);
            request.getSession().setAttribute("message", success ? "Topic updated successfully" : "Failed to update topic");
            response.sendRedirect("managecourse");
        } else if ("add".equals(action)) {
            String name = request.getParameter("name");
            String thumbnailUrl = request.getParameter("thumbnailUrl");
            String description = request.getParameter("description");

            String error = null;
            if (name == null || name.trim().isEmpty()) {
                error = "Topic name cannot be empty.";
            }

            if (error != null) {
                Topic topic = new Topic();
                topic.setName(name);
                topic.setThumbnail_Url(thumbnailUrl);
                topic.setDescription(description);
                request.setAttribute("topic", topic);
                request.setAttribute("error", error);
                request.getRequestDispatcher("addtopic.jsp").forward(request, response);
                return;
            }

            Topic topic = new Topic();
            topic.setName(name);
            topic.setThumbnail_Url(thumbnailUrl);
            topic.setDescription(description);

            boolean success = topicDAO.insertTopic(topic);
            request.getSession().setAttribute("message", success ? "Topic created successfully" : "Failed to create topic");
            response.sendRedirect("managecourse");
        }
    }
}