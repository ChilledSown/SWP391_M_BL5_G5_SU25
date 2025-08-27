package controller;

import dal.TopicDAO;
import model.Topic;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import model.User;

@WebServlet(name = "AddTopicServletInstructor", urlPatterns = {"/addTopicInstructor"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 2 * 1024 * 1024, // 2MB
    maxRequestSize = 5 * 1024 * 1024 // 5MB
)
public class AddTopicServletInstructor extends HttpServlet {
    private static final String UPLOAD_DIR = "assets/img/uploads/";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        
        HttpSession session = request.getSession();
User user = (User) session.getAttribute("user");
if (user == null || !"instructor".equalsIgnoreCase(user.getRole())) {
    response.sendRedirect("login.jsp");
    return;
}

        // Retrieve parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Part filePart = request.getPart("thumbnail");
        String thumbnailUrl = request.getParameter("thumbnail_url");
        String type = request.getParameter("type");
        String action = request.getParameter("action");
        String courseId = request.getParameter("courseId");

        // Construct redirect URL
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

        // Handle file upload
        String thumbnailPath = "";
        if (filePart != null && filePart.getSize() > 0 && !"null".equals(thumbnailUrl)) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));
            String uniqueFileName = System.currentTimeMillis() + fileExtension; // Avoid file name conflicts
            String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIR;

            // Create directory if it doesn't exist
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Save file to disk
            filePart.write(uploadPath + uniqueFileName);
            thumbnailPath = UPLOAD_DIR + uniqueFileName; // Store relative path
            System.out.println("File uploaded to: " + uploadPath + uniqueFileName);
        } else if ("null".equals(thumbnailUrl)) {
            thumbnailPath = ""; // Image was cleared
        }

        // Create and insert new topic
        Topic topic = new Topic();
        topic.setName(name.trim());
        topic.setDescription(description != null ? description.trim() : "");
        topic.setThumbnail_url(thumbnailPath);
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
        return "Servlet to handle adding a new topic for instructors with image upload";
    }
}