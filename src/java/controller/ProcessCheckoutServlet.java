/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.CartDAO;
import dal.CourseDAO;
import dal.OrderDAO;
import dal.OrderDetailDAO;
import java.io.IOException;
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
import model.User;

/**
 *
 * @author sondo
 */
@WebServlet(name="ProcessCheckoutServlet", urlPatterns={"/process-checkout"})
public class ProcessCheckoutServlet extends HttpServlet {

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
            OrderDAO orderDAO = new OrderDAO();
            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            
            // Get user's cart
            Cart userCart = cartDAO.getCartByUserId(userId);
            
            if (userCart != null) {
                // Get cart items
                List<CartItem> cartItems = cartDAO.getCartItemsByCartId(userCart.getCart_id());
                
                if (cartItems != null && !cartItems.isEmpty()) {
                    // Get cart total
                    double cartTotal = cartDAO.getCartTotal(userCart.getCart_id());
                    
                    // Create pending order
                    long orderId = orderDAO.insertOrder(userId, cartTotal, "pending");
                    
                    if (orderId > 0) {
                        // Create order details
                        boolean allDetailsInserted = true;
                        for (CartItem item : cartItems) {
                            boolean detailInserted = orderDetailDAO.insertOrderDetail(orderId, item.getCourse_id(), item.getPrice());
                            if (!detailInserted) {
                                allDetailsInserted = false;
                                break;
                            }
                        }
                        
                        if (allDetailsInserted) {
                            // Store order ID in session for payment processing
                            session.setAttribute("pendingOrderId", orderId);
                            session.setAttribute("orderAmount", cartTotal);
                            // No JSON body; indicate success with 204 No Content
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                            return;
                        } else {
                            // Failed to insert order details
                            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to create order details");
                            return;
                        }
                    } else {
                        // Failed to create order
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to create order");
                        return;
                    }
                } else {
                    // Empty cart
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cart is empty");
                    return;
                }
            } else {
                // No cart found
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No cart found");
                return;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
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
        return "Process Checkout Servlet";
    }
}
