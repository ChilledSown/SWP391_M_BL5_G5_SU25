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
        try {
            String reviewIdStr = request.getParameter("reviewId");
            String courseIdStr = request.getParameter("courseId");

            if (reviewIdStr == null || courseIdStr == null) {
                response.sendRedirect("courses");
                return;
            }
            
            long reviewId = Long.parseLong(reviewIdStr);
            long courseId = Long.parseLong(courseIdStr);
            
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login");
                return;
            }
            
            long userId = user.getUser_id();
            
            // Get DAO
            ReviewDAO reviewDAO = new ReviewDAO();
            
            // Get review to check ownership
            Review review = reviewDAO.getReviewById(reviewId);
            if (review == null || review.getUser_id() != userId) {
                response.sendRedirect("customer-course-detail?id=" + courseId);
                return;
            }
            
            // Delete review
            reviewDAO.deleteReview(reviewId);
            
          
            response.sendRedirect("customer-course-detail?id=" + courseId);
            
        } catch (Exception e) {
            e.printStackTrace();
            // If error occurs, redirect back to courses page
            response.sendRedirect("courses");
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