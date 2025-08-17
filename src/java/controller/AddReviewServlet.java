package controller;

import dal.ReviewDAO;
import model.Review;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name="AddReviewServlet", urlPatterns={"/addReview"})
public class AddReviewServlet extends HttpServlet {
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        try {
            // Lấy tham số từ form
            String courseIdStr = request.getParameter("courseId");
            String userIdStr = request.getParameter("userId");
            String ratingStr = request.getParameter("rating");
            
            // Validate input
            if (courseIdStr == null || userIdStr == null || ratingStr == null) {
                request.setAttribute("error", "Missing required parameters");
                response.sendRedirect("customer-course-detail.jsp?error=missing_params");
                return;
            }
            
            long courseId = Long.parseLong(courseIdStr);
            long userId = Long.parseLong(userIdStr);
            int rating = Integer.parseInt(ratingStr);
            
            // Validate rating range
            if (rating < 1 || rating > 5) {
                request.setAttribute("error", "Rating must be between 1 and 5");
                response.sendRedirect("customer-course-detail.jsp?error=invalid_rating");
                return;
            }
            
            // Lấy comment từ form
            String comment = request.getParameter("comment");
            if (comment == null) {
                comment = "";
            }
            
            // Tạo review object
            Review review = new Review();
            review.setCourse_id(courseId);
            review.setUser_id(userId);
            review.setRating(rating);
            review.setComment(comment);
            review.setCreated_at(new Date());
            
            // Lưu review vào database
            ReviewDAO reviewDAO = new ReviewDAO();
            boolean success = reviewDAO.insertReview(review);
            
            if (success) {
                response.sendRedirect("customer-course-detail?id=" + courseId);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doPost(request, response);
    }
}
