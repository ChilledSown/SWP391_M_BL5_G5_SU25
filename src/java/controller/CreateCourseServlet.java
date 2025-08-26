package controller;

import dal.CourseDAO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.util.Date;
import model.Course;
import model.User;

@WebServlet(name = "CreateCourseServlet", urlPatterns = {"/createCourse"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 2 * 1024 * 1024, // 2MB
    maxRequestSize = 5 * 1024 * 1024 // 5MB
)
public class CreateCourseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
      
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            session.setAttribute("error", "Please log in to create a course.");
            response.sendRedirect("login.jsp");
            return;
        }
     
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String topicIdStr = request.getParameter("topic_id");
        Part filePart = request.getPart("thumbnail");
     
        boolean hasError = false;
        if (title == null || title.trim().isEmpty()) {
            session.setAttribute("titleError", "Please enter a course title.");
            hasError = true;
        }
        if (description == null || description.trim().isEmpty()) {
            session.setAttribute("descriptionError", "Please enter a course description.");
            hasError = true;
        }
        if (priceStr == null || priceStr.trim().isEmpty()) {
            session.setAttribute("priceError", "Please enter a course price.");
            hasError = true;
        } else {
            try {
                int price = Integer.parseInt(priceStr);
                if (price < 0) {
                    session.setAttribute("priceError", "Price must be 0 or greater.");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                session.setAttribute("priceError", "Invalid price format. Please enter a valid number.");
                hasError = true;
            }
        }
        if (topicIdStr == null || topicIdStr.trim().isEmpty()) {
            session.setAttribute("topicError", "Please select a topic.");
            hasError = true;
        } else {
            try {
                Long.parseLong(topicIdStr);
            } catch (NumberFormatException e) {
                session.setAttribute("topicError", "Invalid topic ID format.");
                hasError = true;
            }
        }
        if (filePart == null || filePart.getSize() == 0) {
            session.setAttribute("thumbnailError", "Please select an image file.");
            hasError = true;
        }
    
        if (hasError) {
            response.sendRedirect("blog_course_form.jsp?type=course");
            return;
        }
    
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String contentType = filePart.getContentType();
            long fileSize = filePart.getSize();
        
            if (!(contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/gif"))) {
                session.setAttribute("thumbnailError", "Only JPG, PNG, or GIF files are allowed.");
                response.sendRedirect("blog_course_form.jsp?type=course");
                return;
            }
            if (fileSize > 2 * 1024 * 1024) {
                session.setAttribute("thumbnailError", "Image size exceeds the 2MB limit.");
                response.sendRedirect("blog_course_form.jsp?type=course");
                return;
            }
           
            String uploadPath = getServletContext().getRealPath("/") + "assets/img/uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
       
            String savedFileName = System.currentTimeMillis() + "_" + fileName;
            filePart.write(uploadPath + File.separator + savedFileName);
         
            String thumbnailUrl = "assets/img/uploads/" + savedFileName;
      
            CourseDAO dao = new CourseDAO();
            boolean isDuplicate = dao.isTitleExists(title);
     
            Course course = new Course();
            course.setTitle(title);
            course.setDescription(description);
            course.setPrice(Integer.parseInt(priceStr));
            course.setThumbnail_url(thumbnailUrl);
            course.setCreated_at(new Date());
            course.setTopic_id(Long.parseLong(topicIdStr));
            dao.insertCourse(course, currentUser.getUser_id());
      
            if (isDuplicate) {
                session.setAttribute("duplicateMessage", "Course title already exists. You may want to use a different title.");
            }
        }
        response.sendRedirect("listCourses");
    }
}