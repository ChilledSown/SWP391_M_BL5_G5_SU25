package controller;

import dal.DBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.*;

@WebServlet(name = "VerifyOtpServlet", urlPatterns = {"/verify-otp"})
public class VerifyOtpServlet extends HttpServlet {

    DBContext db = new DBContext();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("verify_otp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String email = request.getParameter("email");
        String otp = request.getParameter("otp");

        String sql = "SELECT u.UserID, t.TokenID "
                + "FROM Users u "
                + "JOIN PasswordResetTokens t ON u.UserID = t.UserID "
                + "WHERE u.Email = ? AND t.OTPCode = ? AND t.IsUsed = 0 AND t.ExpiryTime > GETDATE()";

        try (PreparedStatement ps = db.connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, otp);

            try (ResultSet rs = ps.executeQuery()) { 
                if (rs.next()) {
                    Long userId = rs.getLong("UserID");
                    int tokenId = rs.getInt("TokenID");

                    // Cập nhật OTP đã dùng
                    String sqlUpdate = "UPDATE PasswordResetTokens SET IsUsed = 1 WHERE TokenID = ?";
                    try (PreparedStatement psUpdate = db.connection.prepareStatement(sqlUpdate)) {
                        psUpdate.setInt(1, tokenId);
                        psUpdate.executeUpdate();
                    }

                    request.setAttribute("email", email);
                    request.getRequestDispatcher("reset_password.jsp").forward(request, response);
                } else {
                    request.setAttribute("email", email); 
                    request.setAttribute("message", "OTP is invalid or expired. Please try again!");                  
                    request.getRequestDispatcher("verify_otp.jsp").forward(request, response);          
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "errorr: " + e.getMessage());
             request.setAttribute("email", email); 
            request.getRequestDispatcher("verify_otp.jsp").forward(request, response);
        }

    }
}
