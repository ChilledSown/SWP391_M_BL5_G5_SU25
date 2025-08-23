/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.CartDAO;
import dal.CourseDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;
import model.CartItemWithCourse;
import model.Course;
import model.User;

/**
 *
 * @author sondo
 */
@WebServlet(name="CheckoutServlet", urlPatterns={"/checkout"})
public class CheckoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // Get user from session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            
            // Check if user is logged in
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            
            long userId = user.getUser_id();
            
            // Get DAOs
            CartDAO cartDAO = new CartDAO();
            CourseDAO courseDAO = new CourseDAO();
            
            // Get user's cart
            Cart userCart = cartDAO.getCartByUserId(userId);
            
            if (userCart != null) {
                // Get cart items with course details
                List<CartItem> cartItems = cartDAO.getCartItemsByCartId(userCart.getCart_id());
                List<CartItemWithCourse> cartItemsWithCourse = new ArrayList<>();
                
                // Add course details to each cart item
                for (CartItem cartItem : cartItems) {
                    Course course = courseDAO.getCourseById(cartItem.getCourse_id());
                    if (course != null) {
                        CartItemWithCourse cartItemWithCourse = new CartItemWithCourse(cartItem, course);
                        cartItemsWithCourse.add(cartItemWithCourse);
                    }
                }
                
                // Check if cart has items
                if (cartItemsWithCourse.isEmpty()) {
                    // Redirect to cart if empty
                    response.sendRedirect("cart");
                    return;
                }
                
                // Get cart total
                double cartTotal = cartDAO.getCartTotal(userCart.getCart_id());
                
                // Set attributes for checkout page
                request.setAttribute("cartItems", cartItemsWithCourse);
                request.setAttribute("cartTotal", cartTotal);
                request.setAttribute("userCart", userCart);
                request.setAttribute("user", user);
                
                // Forward to checkout.jsp
                request.getRequestDispatcher("checkout.jsp").forward(request, response);
                
            } else {
                // No cart found, redirect to cart
                response.sendRedirect("cart");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            // Log error and redirect to cart
            response.sendRedirect("cart");
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
        return "Checkout Servlet";
    }
}
