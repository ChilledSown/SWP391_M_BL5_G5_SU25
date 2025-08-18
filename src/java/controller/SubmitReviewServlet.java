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
@WebServlet(name="SubmitReviewServlet", urlPatterns={"/submit-review"})
public class SubmitReviewServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Get parameters from request
            String courseIdStr = request.getParameter("courseId");
            String ratingStr = request.getParameter("rating");
            String comment = request.getParameter("comment");
            
            if (courseIdStr == null || ratingStr == null || comment == null || 
                courseIdStr.trim().isEmpty() || ratingStr.trim().isEmpty() || comment.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"All fields are required\"}");
                return;
            }
            
            long courseId = Long.parseLong(courseIdStr);
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
            
            // Check if user already reviewed this course
            Review existingReview = reviewDAO.getReviewByUserAndCourse(userId, courseId);
            if (existingReview != null) {
                out.print("{\"success\": false, \"message\": \"You have already reviewed this course\"}");
                return;
            }
            
            // Create new review
            Review review = new Review();
            review.setCourse_id(courseId);
            review.setRating(rating);
            review.setComment(comment);
            review.setUser_id(userId);
            
            // Insert review
            reviewDAO.insertReview(review);
            
            out.print("{\"success\": true, \"message\": \"Review submitted successfully\"}");
            
        } catch (NumberFormatException e) {
            out.print("{\"success\": false, \"message\": \"Invalid course ID or rating\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Error submitting review\"}");
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
        return "Submit Review Servlet";
    }
}
