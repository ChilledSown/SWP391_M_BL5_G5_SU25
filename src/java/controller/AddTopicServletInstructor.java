package controller;

import dal.TopicDAO;
import model.Topic;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddTopicServletInstructor", urlPatterns = {"/addTopicInstructor"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 2 * 1024 * 1024, // 2MB
    maxRequestSize = 5 * 1024 * 1024 // 5MB
)
public class AddTopicServletInstructor extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Retrieve parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String thumbnailUrl = request.getParameter("thumbnail_url");

        // Preserve original parameters for redirect
        String type = request.getParameter("type");
        String action = request.getParameter("action");
        String courseId = request.getParameter("courseId");
        String redirectUrl = "blog_course_form.jsp?type=" + (type != null ? type : "course");
        if (action != null) redirectUrl += "&action=" + action;
        if (courseId != null) redirectUrl += "&courseId=" + courseId;

        // Debug logging
        System.out.println("Received parameters - name: " + name + ", description: " + description + ", thumbnailUrl: " + thumbnailUrl);

        // Validate input
        if (name == null || name.trim().isEmpty()) {
            request.getSession().setAttribute("topicAddError", "Topic name is required.");
            response.sendRedirect("add_topic.jsp?type=" + (type != null ? type : "course") + (action != null ? "&action=" + action : "") + (courseId != null ? "&courseId=" + courseId : ""));
            return;
        }

        TopicDAO topicDAO = new TopicDAO();
        // Check for duplicate topic name
        Topic existingTopic = topicDAO.getTopicByName(name);
        if (existingTopic != null) {
            request.getSession().setAttribute("topicAddError", "Topic name already exists.");
            response.sendRedirect("add_topic.jsp?type=" + (type != null ? type : "course") + (action != null ? "&action=" + action : "") + (courseId != null ? "&courseId=" + courseId : ""));
            return;
        }

        // Create and insert new topic
        Topic topic = new Topic();
        topic.setName(name.trim());
        topic.setDescription(description != null ? description.trim() : "");
        topic.setThumbnail_url(thumbnailUrl != null ? thumbnailUrl.trim() : "");
        long topicId = topicDAO.insertTopic(topic);

        // Debug logging
        System.out.println("InsertTopic returned topicId: " + topicId);

        if (topicId != -1) {
            // Store new topic ID in session to preselect in dropdown
            request.getSession().setAttribute("newTopicId", topicId);
            request.getSession().setAttribute("topicAddDebug", "Topic added successfully with ID: " + topicId);
            response.sendRedirect(redirectUrl);
        } else {
            request.getSession().setAttribute("topicAddError", "Failed to add topic to the database.");
            response.sendRedirect("add_topic.jsp?type=" + (type != null ? type : "course") + (action != null ? "&action=" + action : "") + (courseId != null ? "&courseId=" + courseId : ""));
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet to handle adding a new topic for instructors";
    }
}