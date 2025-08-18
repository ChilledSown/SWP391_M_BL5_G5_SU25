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
            String reviewIdStr = request.getParameter("reviewId");

            long reviewId = Long.parseLong(reviewIdStr);
            
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            
            long userId = user.getUser_id();
            
            // Get DAO
            ReviewDAO reviewDAO = new ReviewDAO();
            
            // Get review to check ownership
            Review review = reviewDAO.getReviewById(reviewId);
            
            // Delete review
            reviewDAO.deleteReview(reviewId);
        } catch (Exception e) {
            e.printStackTrace();
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
