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
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Validation
        if (firstName == null || firstName.trim().isEmpty() ||
            middleName == null || middleName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {
            request.setAttribute("message", "Please fill in all fields completely..");
            request.setAttribute("firstName", firstName);
            request.setAttribute("middleName", middleName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            request.setAttribute("message", "Invalid email.");
            request.setAttribute("firstName", firstName);
            request.setAttribute("middleName", middleName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Validate phone format (simple regex for 10-digit phone number)
        if (!phone.matches("^[0-9]{10}$")) {
            request.setAttribute("message", "Phone number must be 10 digits.");
            request.setAttribute("firstName", firstName);
            request.setAttribute("middleName", middleName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Validate password length
        if (password.length() < 6) {
            request.setAttribute("message", "Password must be at least 6 characters.");
            request.setAttribute("firstName", firstName);
            request.setAttribute("middleName", middleName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        UserDAO dao = new UserDAO();

        // Check for duplicate email
        if (dao.getUserByEmail(email) != null) {
            request.setAttribute("message", "Email already exists, please use another email!");
            request.setAttribute("firstName", firstName);
            request.setAttribute("middleName", middleName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Check for duplicate phone
        if (dao.getUserByPhone(phone) != null) {
            request.setAttribute("message", "Phone number already exists, please use another number!");
            request.setAttribute("firstName", firstName);
            request.setAttribute("middleName", middleName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try {
            // Hash password
            String hashedPassword = PasswordHashUtil.hashPassword(password);

            User user = new User();
            user.setFirstName(firstName);
            user.setMiddleName(middleName);
            user.setLastName(lastName);
            user.setAvataUrl("default-avatar.png");
            user.setPhone(phone);
            user.setAddress(address);
            user.setEmail(email);
            user.setPasswordHash(hashedPassword);
            user.setRole("customer");
            user.setAccountStatus("active");

            if (dao.insertUser(user)) {
                request.setAttribute("message", "Registration successful! Please login now.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Registration failed, please try again.");
                request.setAttribute("firstName", firstName);
                request.setAttribute("middleName", middleName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("address", address);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred, Please try again..");
            request.setAttribute("firstName", firstName);
            request.setAttribute("middleName", middleName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}