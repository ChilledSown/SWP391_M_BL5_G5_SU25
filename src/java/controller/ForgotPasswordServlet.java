package controller;

import dal.PasswordResetTokensDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Random;
import model.PasswordResetToken;
import model.User;
import utils.EmailUtil;



@WebServlet(name="ForgotPasswordServlet", urlPatterns={"/forgot-password"})
public class ForgotPasswordServlet extends HttpServlet {
     private UserDAO userDAO = new UserDAO();
    private PasswordResetTokensDAO tokenDAO = new PasswordResetTokensDAO();

    private static final int OTP_LENGTH = 6;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
    } 

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String email = request.getParameter("email");

       if (email == null || email.trim().isEmpty()) {
            request.setAttribute("message", "Email cannot be empty.");
            request.setAttribute("email", email); 
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
            return;
        }

        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            request.setAttribute("message", "Email does not exist.");
            request.setAttribute("email", email); 
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
            return;
        }

        try {
            // Tạo OTP
            String otp = generateOTP();

            // Tạo đối tượng token
            PasswordResetToken token = new PasswordResetToken();
            token.setUserId(user.getUser_id()); 
            token.setOtpCode(otp);
            token.setExpiryTime(LocalDateTime.now().plusMinutes(5));
            token.setIsUsed(false);

            // Lưu vào DB
            tokenDAO.insertToken(token);

            // Gửi email OTP
            String subject = "OTP đặt lại mật khẩu";
            String content = "Xin chào <b>" + user.getFullName() + "</b>,<br/>" +
                    "Mã OTP của bạn là: <b>" + otp + "</b><br/>" +
                    "Mã này sẽ hết hạn trong 5 phút.";
            EmailUtil.sendEmail(email, subject, content);

            request.setAttribute("email", email);
            request.setAttribute("message", "Mã OTP đã được gửi đến email của bạn!");
            request.getRequestDispatcher("verify_otp.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi gửi OTP: " + e.getMessage());
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
        }
    }

    private String generateOTP() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

 

}
