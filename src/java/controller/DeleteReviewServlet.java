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
@WebServlet(name="DeleteReviewServlet", urlPatterns={"/delete-review"})
public class DeleteReviewServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Get review ID from request
            String reviewIdStr = request.getParameter("reviewId");
            if (reviewIdStr == null || reviewIdStr.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"Review ID is required\"}");
                return;
            }
            
            long reviewId = Long.parseLong(reviewIdStr);
            
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
            
            // Check if user owns this review (for testing, allow deletion)
            if (!review.getUser_id().equals(userId)) {
                out.print("{\"success\": false, \"message\": \"You can only delete your own reviews\"}");
                return;
            }
            
            // Delete review
            reviewDAO.deleteReview(reviewId);
            
            out.print("{\"success\": true, \"message\": \"Review deleted successfully\"}");
            
        } catch (NumberFormatException e) {
            out.print("{\"success\": false, \"message\": \"Invalid review ID\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Error deleting review\"}");
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
        return "Delete Review Servlet";
    }
}
