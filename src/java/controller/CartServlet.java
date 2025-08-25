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
@WebServlet(name="CartServlet", urlPatterns={"/cart"})
public class CartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            
            long userId = user.getUser_id();
            CartDAO cartDAO = new CartDAO();
            CourseDAO courseDAO = new CourseDAO();
            Cart userCart = cartDAO.getCartByUserId(userId);
            
            if (userCart != null) {
                List<CartItem> cartItems = cartDAO.getCartItemsByCartId(userCart.getCart_id());
                List<CartItemWithCourse> cartItemsWithCourse = new ArrayList<>();
                for (CartItem cartItem : cartItems) {
                    Course course = courseDAO.getCourseById(cartItem.getCourse_id());
                    CartItemWithCourse cartItemWithCourse = new CartItemWithCourse(cartItem, course);
                    cartItemsWithCourse.add(cartItemWithCourse);
                }
                double cartTotal = cartDAO.getCartTotal(userCart.getCart_id());
                request.setAttribute("cartItems", cartItemsWithCourse);
                request.setAttribute("cartTotal", cartTotal);
                request.setAttribute("userCart", userCart);
            }
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            
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
        return "Cart Servlet";
    }
}
