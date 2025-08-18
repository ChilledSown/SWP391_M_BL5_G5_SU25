/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.CartDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author sondo
 */
@WebServlet(name="RemoveFromCartServlet", urlPatterns={"/remove-from-cart"})
public class RemoveFromCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Get cart item ID from request
            String cartItemIdStr = request.getParameter("cartItemId");
            if (cartItemIdStr == null || cartItemIdStr.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"Cart item ID is required\"}");
                return;
            }
            
            long cartItemId = Long.parseLong(cartItemIdStr);
            
            // Get DAO
            CartDAO cartDAO = new CartDAO();
            
            // Remove course from cart
            cartDAO.removeCourseFromCart(cartItemId);
            
            out.print("{\"success\": true, \"message\": \"Item removed from cart successfully\"}");
            
        } catch (NumberFormatException e) {
            out.print("{\"success\": false, \"message\": \"Invalid cart item ID\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"Error removing item from cart\"}");
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
        return "Remove From Cart Servlet";
    }
}
