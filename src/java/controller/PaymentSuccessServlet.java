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
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        String amountStr = request.getParameter("amount");
        String currency = request.getParameter("currency");
        String method = request.getParameter("method");
        if (method == null || method.trim().isEmpty()) { method = "PayPal"; }
        double amount = 0.0;
        try { amount = Double.parseDouble(amountStr); } catch (Exception ignored) {}

        // Build order from cart
        CartDAO cartDAO = new CartDAO();
        CourseDAO courseDAO = new CourseDAO();
        List<CartItem> cartItems = new ArrayList<>();
        Cart userCart = cartDAO.getCartByUserId(user.getUser_id());
        if (userCart != null) {
            cartItems = cartDAO.getCartItemsByCartId(userCart.getCart_id());
        }

        if (cartItems == null || cartItems.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        PaymentDAO paymentDAO = new PaymentDAO();

        long orderId = orderDAO.insertOrder(user.getUser_id(), amount, "paid");
        for (CartItem item : cartItems) {
            orderDetailDAO.insertOrderDetail(orderId, item.getCourse_id(), item.getPrice());
        }

        paymentDAO.insertPayment(orderId, amount, currency != null ? currency : "USD", method, orderIdFromPg != null ? orderIdFromPg : "", "captured");

        // Clear cart
        if (userCart != null) {
            for (CartItem item : cartItems) {
                cartDAO.removeCourseFromCart(item.getCart_item_id());
            }
        }

        // Send Email
        try {
            String orderDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date());
            String totalFormatted = String.format("%.2f", amount) + " " + (currency != null ? currency : "USD");

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
            content.append("<div style='font-family:Segoe UI,Roboto,Helvetica,Arial,sans-serif;background:#f6f7fb;padding:24px;'>")
                   .append("<table role='presentation' style='max-width:640px;margin:0 auto;background:#ffffff;border-radius:12px;box-shadow:0 12px 30px rgba(0,0,0,.06);overflow:hidden;' cellpadding='0' cellspacing='0' width='100%'>")
                   .append("<tr><td style='background:linear-gradient(135deg,#667eea,#764ba2);padding:24px;text-align:center;color:#fff;'>")
                   .append("<div style='width:64px;height:64px;border-radius:50%;background:#28a745;margin:0 auto 10px;display:flex;align-items:center;justify-content:center;box-shadow:0 8px 20px rgba(40,167,69,.35);'>")
                   .append("<span style='display:inline-block;width:28px;height:14px;border-left:4px solid #fff;border-bottom:4px solid #fff;transform:rotate(-45deg);'></span>")
                   .append("</div>")
                   .append("<h2 style='margin:0;font-size:22px;font-weight:800;letter-spacing:.3px;'>Payment Successful</h2>")
                   .append("<p style='margin:6px 0 0;opacity:.9;'>Thank you! Your payment has been processed successfully.</p>")
                   .append("</td></tr>")

                   .append("<tr><td style='padding:22px 24px;'>")
                   .append("<div style='background:#f3f6ff;border:1px dashed #cdd9ff;border-radius:10px;padding:12px 14px;margin-bottom:16px;color:#5f6b7a;'>")
                   .append("This email confirms your order. Below are the details of your purchase.</div>")

                   .append("<table role='presentation' width='100%' cellpadding='0' cellspacing='0' style='margin-bottom:12px;'>")
                   .append("<tr><td style='padding:8px 0;color:#6c757d;width:40%'>Customer</td><td style='font-weight:600;color:#2c3e50;'>")
                   .append(user.getFirstName()).append(" ").append(user.getLastName()).append(" (" ).append(user.getEmail()).append(")</td></tr>")
                   .append("<tr><td style='padding:8px 0;color:#6c757d;'>Order ID</td><td style='font-weight:600;color:#2c3e50;'>#").append(orderId).append("</td></tr>")
                   .append("<tr><td style='padding:8px 0;color:#6c757d;'>Order Date</td><td style='font-weight:600;color:#2c3e50;'>").append(orderDate).append("</td></tr>")
                   .append("<tr><td style='padding:8px 0;color:#6c757d;'>Payment Method</td><td style='font-weight:600;color:#2c3e50;'>").append(method).append("</td></tr>")
                   .append("<tr><td style='padding:8px 0;color:#6c757d;'>Transaction ID</td><td style='font-weight:600;color:#2c3e50;'>").append(orderIdFromPg != null ? orderIdFromPg : "").append("</td></tr>")
                   .append("</table>")

                   .append("<table role='presentation' width='100%' cellpadding='0' cellspacing='0' style='border:1px solid #eef2f7;border-radius:10px;overflow:hidden;'>")
                   .append("<thead><tr style='background:#f9fbff;color:#6c757d;font-size:13px;text-transform:uppercase;letter-spacing:.4px;'>")
                   .append("<th align='left' style='padding:10px 12px;'>Course</th><th align='right' style='padding:10px 12px;'>Price</th>")
                   .append("</tr></thead><tbody>")
                   .append(itemsTable)
                   .append("<tr><td style='padding:12px;border-top:1px solid #eef2f7;font-weight:700;text-align:right;' colspan='2'>Total: ")
                   .append(totalFormatted)
                   .append("</td></tr>")
                   .append("</tbody></table>")

                   .append("<p style='margin:14px 0 6px;color:#6c757d;'>If you have any questions, reply to this email and we’ll be happy to help.</p>")
                   .append("</td></tr>")

                   .append("<tr><td style='text-align:center;color:#a0aec0;font-size:12px;padding:16px 12px;'>© ")
                   .append(new java.util.GregorianCalendar().get(java.util.Calendar.YEAR))
                   .append(" OnlineLearning — Thank you for your purchase.</td></tr>")
                   .append("</table></div>");

            EmailUtil.sendEmail(user.getEmail(), "Your Order Confirmation", content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("orderId", orderId);
        request.setAttribute("amount", amount);
        request.setAttribute("currency", currency != null ? currency : "USD");
        request.getRequestDispatcher("payment-success.jsp").forward(request, response);
    }
}


