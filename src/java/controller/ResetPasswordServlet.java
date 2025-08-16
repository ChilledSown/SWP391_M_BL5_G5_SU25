package controller;

import dal.DBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.PasswordHashUtil;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/reset-password"})
public class ResetPasswordServlet extends HttpServlet {

    DBContext db = new DBContext();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("reset_password.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String email = request.getParameter("email");
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

         // 1. Validate password length
        if (newPassword != null && newPassword.length() < 6) {
            request.setAttribute("message", "Password must be at least 6 characters long.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            return;
        }
        // 1. Kiểm tra mật khẩu xác nhận
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("message", "Confirmation password does not match!");
            request.setAttribute("email", email);
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            return;
        }

       
        // 3. Cập nhật mật khẩu
        String sql = "UPDATE Users SET PasswordHash = ? WHERE Email = ?";

        try (PreparedStatement ps = db.connection.prepareStatement(sql)) {
            // 2. Hash mật khẩu
            String hashedPassword = PasswordHashUtil.hashPassword(newPassword);

            ps.setString(1, hashedPassword);
            ps.setString(2, email);
            int updated = ps.executeUpdate();

            if (updated > 0) {
                request.setAttribute("message", "Password was reset successfully!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "User not found or OTP not authenticated.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Errorr: " + e.getMessage());
            request.setAttribute("email", email);
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
