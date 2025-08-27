package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import utils.PasswordHashUtil;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Hiển thị trang đăng nhập khi truy cập GET
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("message", "Email cannot be empty.");
            request.setAttribute("email", email);
            request.setAttribute("password", password); 
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("message", "Password cannot be empty.");
            request.setAttribute("email", email); 
            request.setAttribute("password", password);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            // Hash mật khẩu nhập vào để so sánh
            String hashedPassword = PasswordHashUtil.hashPassword(password);

            UserDAO dao = new UserDAO();
            User u = dao.login(email, hashedPassword);

            if (u != null) {
                // Kiểm tra trạng thái tài khoản
                if (!u.getAccountStatus().equalsIgnoreCase("active")) {
                    String statusMessage = u.getAccountStatus().equalsIgnoreCase("inactive")
                            ? "Your account is Inactive. Please contact the administrator.."
                            : "Your account has been Suspended. Please contact the administrator..";
                    request.setAttribute("message", statusMessage);
                    request.setAttribute("email", email);
                    request.setAttribute("password", password);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
                
                
                HttpSession session = request.getSession();
                session.setAttribute("user", u);

                switch (u.getRole()) {
                    case "admin":
                        response.sendRedirect("overviewadmin");
                        break;
                    case "customer":
                        response.sendRedirect("home");
                        break;
                    case "instructor":
                        response.sendRedirect("DashBoard");

                        break;
                }
            } else {
                request.setAttribute("message", "Invalid email or password.");
                request.setAttribute("email", email); 
                request.setAttribute("password", password); 
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Erorr. Please try again!.");
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}