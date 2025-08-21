/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.CartDAO;
import dal.CourseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Course;
import model.User;

/**
 *
 * @author sondo
 */
@WebServlet(name="AddToCartServlet", urlPatterns={"/add-to-cart"})
public class AddToCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // Get course ID from request (support both courseId and id)
            String courseIdStr = request.getParameter("courseId");
            if (courseIdStr == null || courseIdStr.trim().isEmpty()) {
                courseIdStr = request.getParameter("id");
            }
            if (courseIdStr == null || courseIdStr.trim().isEmpty()) {
                response.sendRedirect("courses");
                return;
            }
            
            long courseId = Long.parseLong(courseIdStr);
            CartDAO cartDAO = new CartDAO();
            CourseDAO courseDAO = new CourseDAO();
            
            // Get course details
            Course course = courseDAO.getCourseById(courseId);
            if (course == null) {
                response.sendRedirect("courses");
                return;
            }
            
            // Get user from session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            
            // Check if user is logged in
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            
            long userId = user.getUser_id();
            
            // Get or create user's cart
            Cart userCart = cartDAO.findOrCreateActiveCart(userId);
            if (userCart == null) {
                response.sendRedirect("courses");
                return;
            }
            
            // Check if course is already in cart
            if (!cartDAO.isCourseInCart(userCart.getCart_id(), courseId)) {
                // Add course to cart
                cartDAO.addCourseToCart(userCart.getCart_id(), courseId, course.getPrice());
            }
            response.sendRedirect("cart");
        } catch (NumberFormatException e) {
            response.sendRedirect("courses");
        } catch (Exception e) {
            e.printStackTrace();
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
        return "Add To Cart Servlet";
    }
}
