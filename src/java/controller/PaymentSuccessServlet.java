package controller;

import dal.CartDAO;
import dal.CourseDAO;
import dal.OrderDAO;
import dal.OrderDetailDAO;
import dal.PaymentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Cart;
import model.CartItem;
import model.Course;
import model.User;
import utils.EmailUtil;

@WebServlet(name = "PaymentSuccessServlet", urlPatterns = {"/payment-success"})
public class PaymentSuccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String orderIdFromPg = request.getParameter("orderId");
        String paypalOrderId = request.getParameter("paypalOrderId");
        String amountStr = request.getParameter("amount");
        String currency = request.getParameter("currency");
        String method = request.getParameter("method");
        if (method == null || method.trim().isEmpty()) {
            method = "PayPal";
        }
        double amount = 0.0;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (Exception ignored) {
        }
        Long pendingOrderId = (Long) session.getAttribute("pendingOrderId");
        Double orderAmount = (Double) session.getAttribute("orderAmount");
        Long finalOrderId = pendingOrderId;
        if (finalOrderId == null && orderIdFromPg != null) {
            try {
                finalOrderId = Long.parseLong(orderIdFromPg);
            } catch (Exception ignored) {
            }
        }
        double finalAmount = amount > 0 ? amount : (orderAmount != null ? orderAmount.doubleValue() : 0.0);
        if (finalOrderId == null || finalAmount <= 0) {
            response.sendRedirect("cart");
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        CartDAO cartDAO = new CartDAO();
        CourseDAO courseDAO = new CourseDAO();
        boolean orderUpdated = orderDAO.updateOrderStatus(finalOrderId, "completed");
        if (!orderUpdated) {
            response.sendRedirect("cart");
            return;
        }
        List<CartItem> cartItems = new ArrayList<>();
        Cart userCart = cartDAO.getCartByUserId(user.getUser_id());
        if (userCart != null) {
            cartItems = cartDAO.getCartItemsByCartId(userCart.getCart_id());
        }

        long paymentId = paymentDAO.insertPayment(finalOrderId, finalAmount, method, "completed");
        if (userCart != null) {
            for (CartItem item : cartItems) {
                cartDAO.removeCourseFromCart(item.getCart_item_id());
            }
        }

        // Send Email
        try {
            String orderDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date());
            String totalFormatted = String.format("%.2f", finalAmount) + " " + (currency != null ? currency : "USD");

            StringBuilder itemsTable = new StringBuilder();
            for (CartItem item : cartItems) {
                Course c = courseDAO.getCourseById(item.getCourse_id());
                String title = (c != null ? c.getTitle() : ("Course #" + item.getCourse_id()));
                itemsTable.append("<tr>")
                        .append("<td style='padding:8px 12px;border-bottom:1px solid #eef2f7;'>").append(title).append("</td>")
                        .append("<td style='padding:8px 12px;border-bottom:1px solid #eef2f7;text-align:right;font-weight:600;'>$")
                        .append(String.format("%.2f", item.getPrice())).append("</td>")
                        .append("</tr>");
            }

            StringBuilder content = new StringBuilder();

            content.append("This email confirms your order.<br><br>")
                    .append("Customer: ")
                    .append(user.getFirstName()).append(" ")
                    .append(user.getMiddleName())
                    .append(user.getLastName()).append(" (")
                    .append(user.getEmail()).append(")<br><br>")
                    .append("Order ID: #").append(finalOrderId).append("<br>")
                    .append("Order Date: ").append(orderDate).append("<br>")
                    .append("Payment Method: ").append(method).append("<br>")
                    .append("Transaction ID: ").append(paypalOrderId != null ? paypalOrderId : "").append("<br><br>")
                    .append("Items:<br>").append(itemsTable).append("<br>")
                    .append("Total: ").append(totalFormatted).append("<br><br>")
                    .append("If you have any questions, reply to this email and we’ll be happy to help.\n\n")
                    .append("© ")
                    .append(new java.util.GregorianCalendar().get(java.util.Calendar.YEAR))
                    .append(" OnlineLearning — Thank you for your purchase.<br>");

            EmailUtil.sendEmail(user.getEmail(), "Your Order Confirmation", content.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        session.removeAttribute("pendingOrderId");
        session.removeAttribute("orderAmount");

        request.setAttribute("orderId", finalOrderId);
        request.setAttribute("amount", finalAmount);
        request.setAttribute("currency", currency != null ? currency : "USD");
        request.getRequestDispatcher("payment-success.jsp").forward(request, response);
    }
}
