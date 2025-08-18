/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.ReviewDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Review;
import model.User;

/**
 *
 * @author sondo
 */
@WebServlet(name="UpdateReviewServlet", urlPatterns={"/update-review"})
public class UpdateReviewServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Get parameters from request
            String reviewIdStr = request.getParameter("reviewId");
            String ratingStr = request.getParameter("rating");
            String comment = request.getParameter("comment");
            
            if (reviewIdStr == null || ratingStr == null || comment == null || 
                reviewIdStr.trim().isEmpty() || ratingStr.trim().isEmpty() || comment.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"All fields are required\"}");
                return;
            }
            
            long reviewId = Long.parseLong(reviewIdStr);
            int rating = Integer.parseInt(ratingStr);
            
            // Validate rating
            if (rating < 1 || rating > 5) {
                out.print("{\"success\": false, \"message\": \"Rating must be between 1 and 5\"}");
                return;
            }
            
            // For testing, use a fixed user ID
            // Get user from session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            
            // Check if user is logged in
            if (user == null) {
                out.print("{\"success\": false, \"message\": \"Please login first\"}");
                return;
            }
            
            long userId = user.getUser_id();
            
            // Get DAO
            ReviewDAO reviewDAO = new ReviewDAO();
            
            // Get review to check ownership
            Review review = reviewDAO.getReviewById(reviewId);
            if (review == null) {
                out.print("{\"success\": false, \"message\": \"Review not found\"}");
                return;
            }
            
            // Check if user owns this review
            if (!review.getUser_id().equals(userId)) {
                out.print("{\"success\": false, \"message\": \"You can only update your own reviews\"}");
                return;
            }
            
            // Update review
            review.setRating(rating);
            review.setComment(comment);
            reviewDAO.updateReview(review);
            
            out.print("{\"success\": true, \"message\": \"Review updated successfully\"}");
            
        } catch (NumberFormatException e) {
            out.print("{\"success\": false, \"message\": \"Invalid review ID or rating\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Error updating review\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Update Review Servlet";
    }
}
