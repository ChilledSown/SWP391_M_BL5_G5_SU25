package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.User;
import utils.PasswordHashUtil;

@WebServlet(name = "ManageUserServlet", urlPatterns = {"/manageuser"})
public class ManageUserServlet extends HttpServlet {

    private final int PAGE_SIZE = 8; // Records per page

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();

        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            long userId = Long.parseLong(request.getParameter("userId"));
            User user = userDAO.getUserById(userId);
            request.setAttribute("user", user);
            request.getRequestDispatcher("updateuser.jsp").forward(request, response);
            return;
        } else if ("add".equals(action)) {   
            request.getRequestDispatcher("adduser.jsp").forward(request, response);
            return;
        }

        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                // Default to page 1 if invalid
            }
        }

        String searchQuery = request.getParameter("searchQuery");
        String role = request.getParameter("role");
        if (role == null || role.trim().isEmpty()) {
            role = "all"; // Mặc định all
        }

        List<User> users;
        int totalUsers;
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            users = userDAO.getUsersByName(searchQuery, page, PAGE_SIZE, role);
            totalUsers = userDAO.getTotalUsersByName(searchQuery, role);
        } else {
            users = userDAO.getUsers(page, PAGE_SIZE, role);
            totalUsers = userDAO.getTotalUsers(role);
        }
        int totalPages = (int) Math.ceil((double) totalUsers / PAGE_SIZE);

        request.setAttribute("users", users);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("selectedRole", role); // Để giữ selected trong dropdown

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }
        request.getRequestDispatcher("manageuser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            long userId = Long.parseLong(request.getParameter("userId"));
            boolean success = userDAO.deleteUser(userId);
            request.getSession().setAttribute("message", success ? "User deleted successfully" : "Failed to delete user");
            response.sendRedirect("manageuser");
        } else if ("update".equals(action)) {
            String userIdStr = request.getParameter("userId");
            String firstName = request.getParameter("firstName");
            String middleName = request.getParameter("middleName");
            String lastName = request.getParameter("lastName");
            String avataUrl = request.getParameter("avataUrl");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");
            String accountStatus = request.getParameter("accountStatus");

            String error = null;
            if (firstName == null || firstName.trim().isEmpty() ||
                middleName == null || middleName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty()) {
                error = "All name fields are required.";
            } else if (password != null && !password.isEmpty() && password.length() < 6) {
                error = "Password must be at least 6 characters long.";
            } else if (!email.contains("@")) {
                error = "Email must contain '@' symbol.";
            } else if (!phone.matches("0\\d{9}")) {
                error = "Phone must contain exactly 10 digits and start with 0.";
            } else {
                User existingUserByEmail = userDAO.getUserByEmail(email);
                if (existingUserByEmail != null && existingUserByEmail.getUser_id() != Long.parseLong(userIdStr)) {
                    error = "Email already exists.";
                } else {
                    User existingUserByPhone = userDAO.getUserByPhone(phone);
                    if (existingUserByPhone != null && existingUserByPhone.getUser_id() != Long.parseLong(userIdStr)) {
                        error = "Phone number already exists.";
                    }
                }
            }

            if (error != null) {
                User user = new User();
                user.setUser_id(Long.parseLong(userIdStr));
                user.setFirstName(firstName);
                user.setMiddleName(middleName);
                user.setLastName(lastName);
                user.setAvataUrl(avataUrl);
                user.setPhone(phone);
                user.setAddress(address);
                user.setEmail(email);
                user.setRole(role);
                user.setAccountStatus(accountStatus);
                request.setAttribute("user", user);
                request.setAttribute("error", error);
                request.getRequestDispatcher("updateuser.jsp").forward(request, response);
                return;
            }

            User user = new User();
            user.setUser_id(Long.parseLong(userIdStr));
            user.setFirstName(firstName);
            user.setMiddleName(middleName);
            user.setLastName(lastName);
            user.setAvataUrl(avataUrl != null ? avataUrl : "default-avatar.png");
            user.setPhone(phone);
            user.setAddress(address);
            user.setEmail(email);
            user.setRole(role);
            user.setAccountStatus(accountStatus != null ? accountStatus : "active");

            if (password != null && !password.isEmpty()) {
                try {
                    String hashedPassword = PasswordHashUtil.hashPassword(password);
                    user.setPasswordHash(hashedPassword);
                } catch (Exception e) {
                    request.getSession().setAttribute("message", "Error hashing password");
                    response.sendRedirect("manageuser");
                    return;
                }
            } else {
                // Fetch existing password if not updating
                User existingUser = userDAO.getUserById(Long.parseLong(userIdStr));
                user.setPasswordHash(existingUser.getPasswordHash());
            }

            boolean success = userDAO.updateUser(user);
            request.getSession().setAttribute("message", success ? "User updated successfully" : "Failed to update user");
            response.sendRedirect("manageuser");
        } else if ("add".equals(action)) {
            String firstName = request.getParameter("firstName");
            String middleName = request.getParameter("middleName");
            String lastName = request.getParameter("lastName");
            String avataUrl = request.getParameter("avataUrl");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");
            String accountStatus = request.getParameter("accountStatus");

            String error = null;
            if (firstName == null || firstName.trim().isEmpty() ||
                middleName == null || middleName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty()) {
                error = "All name fields are required.";
            } else if (password == null || password.length() < 6) {
                error = "Password must be at least 6 characters long.";
            } else if (!email.contains("@")) {
                error = "Email must contain '@' symbol.";
            } else if (!phone.matches("0\\d{9}")) {
                error = "Phone must contain exactly 10 digits and start with 0.";
            } else {
                User existingUserByEmail = userDAO.getUserByEmail(email);
                if (existingUserByEmail != null) {
                    error = "Email already exists.";
                } else {
                    User existingUserByPhone = userDAO.getUserByPhone(phone);
                    if (existingUserByPhone != null) {
                        error = "Phone number already exists.";
                    }
                }
            }

            if (error != null) {
                User user = new User();
                user.setFirstName(firstName);
                user.setMiddleName(middleName);
                user.setLastName(lastName);
                user.setAvataUrl(avataUrl);
                user.setPhone(phone);
                user.setAddress(address);
                user.setEmail(email);
                user.setRole(role);
                user.setAccountStatus(accountStatus);
                request.setAttribute("user", user);
                request.setAttribute("error", error);
                request.getRequestDispatcher("adduser.jsp").forward(request, response);
                return;
            }

            User user = new User();
            user.setFirstName(firstName);
            user.setMiddleName(middleName);
            user.setLastName(lastName);
            user.setAvataUrl(avataUrl != null ? avataUrl : "default-avatar.png");
            user.setPhone(phone);
            user.setAddress(address);
            user.setEmail(email);
            user.setRole(role);
            user.setAccountStatus(accountStatus != null ? accountStatus : "active");

            try {
                String hashedPassword = PasswordHashUtil.hashPassword(password);
                user.setPasswordHash(hashedPassword);
            } catch (Exception e) {
                request.getSession().setAttribute("message", "Error hashing password");
                response.sendRedirect("manageuser");
                return;
            }

            boolean success = userDAO.insertUser(user);
            request.getSession().setAttribute("message", success ? "User created successfully" : "Failed to create user");
            response.sendRedirect("manageuser");
        }
    }
}