package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import utils.PasswordHashUtil;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        request.setAttribute("fullName", fullName);
        request.setAttribute("email", email);
        request.setAttribute("password", password);
        request.setAttribute("phone", phone);
        request.setAttribute("address", address);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        UserDAO dao = new UserDAO();

         // Validate password length
        if (password != null && password.length() < 6) {
            request.setAttribute("message", "Password must be at least 6 characters long.");
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("password", password); // Không hiển thị lại password (bảo mật)
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Check email trùng
        if (dao.getUserByEmail(email) != null) {
            request.setAttribute("message", "Email đã tồn tại, vui lòng dùng email khác!");
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Check số điện thoại trùng
        if (dao.getUserByPhone(phone) != null) {
            request.setAttribute("message", "Số điện thoại đã tồn tại, vui lòng dùng số khác!");
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("password", password); 
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try {
            // Hash password
            String hashedPassword = PasswordHashUtil.hashPassword(password);

            User user = new User();
            user.setFullName(fullName);
            user.setAvataUrl("default-avatar.png"); // nếu chưa có avatar
            user.setPhone(phone);
            user.setAddress(address);
            user.setEmail(email);
            user.setPasswordHash(hashedPassword);
            user.setRole("customer");

            if (dao.insertUser(user)) {
                doGet(request, response);
                request.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                doGet(request, response);
                request.setAttribute("message", "Đăng ký thất bại, vui lòng thử lại.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Có lỗi xảy ra, vui lòng thử lại.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

}
