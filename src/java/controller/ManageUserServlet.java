package controller;

import dal.UserDAO;
import java.io.File;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.List;
import model.User;
import utils.PasswordHashUtil;

@WebServlet(name = "ManageUserServlet", urlPatterns = {"/manageuser"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class ManageUserServlet extends HttpServlet {

    private final int PAGE_SIZE = 8; // Records per page
    private static final String UPLOAD_DIR = "Upload"; // Directory to store uploaded avatars

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
            role = "all"; // Default to all
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
        request.setAttribute("selectedRole", role);

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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        UserDAO userDAO = new UserDAO();
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            long userId = Long.parseLong(request.getParameter("userId"));
            User existingUser = userDAO.getUserById(userId);
            if (existingUser != null && existingUser.getAvataUrl() != null && !existingUser.getAvataUrl().contains("default-avatar.png")) {
                String oldFilePath = getServletContext().getRealPath("") + File.separator + existingUser.getAvataUrl().replace(request.getContextPath() + "/", "");
                new File(oldFilePath).delete();
                System.out.println("Deleted old avatar: " + oldFilePath);
            }
            boolean success = userDAO.deleteUser(userId);
            request.getSession().setAttribute("message", success ? "User deleted successfully" : "Failed to delete user");
            response.sendRedirect("manageuser");
        } else if ("update".equals(action)) {
            String userIdStr = request.getParameter("userId");
            String firstName = request.getParameter("firstName");
            String middleName = request.getParameter("middleName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");
            String accountStatus = request.getParameter("accountStatus");

            String avataUrl = null;
            Part filePart = request.getPart("avataUrl");
            String fileName = null;

            // Validate inputs
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

            // Handle file upload
            if (filePart != null && filePart.getSize() > 0) {
                fileName = extractFileName(filePart);
                if (fileName != null && !fileName.isEmpty()) {
                    if (!fileName.matches(".*\\.(jpg|jpeg|png|gif)$")) {
                        error = "Only image files (jpg, jpeg, png, gif) are allowed.";
                    } else if (filePart.getSize() > 10 * 1024 * 1024) {
                        error = "File size exceeds 10MB limit.";
                    } else {
                        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }
                        String filePath = uploadPath + File.separator + fileName;
                        filePart.write(filePath);
                        avataUrl = request.getContextPath() + "/" + UPLOAD_DIR + "/" + fileName;
                        System.out.println("File saved at: " + filePath);
                        System.out.println("Avatar URL: " + avataUrl);

                        // Delete old avatar if it exists
                        User existingUser = userDAO.getUserById(Long.parseLong(userIdStr));
                        if (existingUser.getAvataUrl() != null && !existingUser.getAvataUrl().contains("default-avatar.png")) {
                            String oldFilePath = getServletContext().getRealPath("") + File.separator + existingUser.getAvataUrl().replace(request.getContextPath() + "/", "");
                            new File(oldFilePath).delete();
                            System.out.println("Deleted old avatar: " + oldFilePath);
                        }
                    }
                }
            }

            if (error != null) {
                User user = new User();
                user.setUser_id(Long.parseLong(userIdStr));
                user.setFirstName(firstName);
                user.setMiddleName(middleName);
                user.setLastName(lastName);
                user.setAvataUrl(avataUrl != null ? avataUrl : userDAO.getUserById(Long.parseLong(userIdStr)).getAvataUrl());
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
            user.setPhone(phone);
            user.setAddress(address);
            user.setEmail(email);
            user.setRole(role);
            user.setAccountStatus(accountStatus != null ? accountStatus : "active");

            // Set avatar URL
            if (avataUrl != null) {
                user.setAvataUrl(avataUrl);
            } else {
                User existingUser = userDAO.getUserById(Long.parseLong(userIdStr));
                user.setAvataUrl(existingUser.getAvataUrl());
            }

            // Handle password
            if (password != null && !password.isEmpty()) {
                try {
                    String hashedPassword = PasswordHashUtil.hashPassword(password);
                    user.setPasswordHash(hashedPassword);
                } catch (Exception e) {
                    request.setAttribute("user", user);
                    request.setAttribute("error", "Error hashing password");
                    request.getRequestDispatcher("updateuser.jsp").forward(request, response);
                    return;
                }
            } else {
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
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");
            String accountStatus = request.getParameter("accountStatus");

            String avataUrl = null;
            Part filePart = request.getPart("avataUrl");
            String fileName = null;

            // Validate inputs
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

            // Handle file upload
            if (filePart != null && filePart.getSize() > 0) {
                fileName = extractFileName(filePart);
                if (fileName != null && !fileName.isEmpty()) {
                    if (!fileName.matches(".*\\.(jpg|jpeg|png|gif)$")) {
                        error = "Only image files (jpg, jpeg, png, gif) are allowed.";
                    } else if (filePart.getSize() > 10 * 1024 * 1024) {
                        error = "File size exceeds 10MB limit.";
                    } else {
                        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }
                        String filePath = uploadPath + File.separator + fileName;
                        filePart.write(filePath);
                        avataUrl = request.getContextPath() + "/" + UPLOAD_DIR + "/" + fileName;
                        System.out.println("File saved at: " + filePath);
                        System.out.println("Avatar URL: " + avataUrl);
                    }
                }
            }

            if (error != null) {
                User user = new User();
                user.setFirstName(firstName);
                user.setMiddleName(middleName);
                user.setLastName(lastName);
                user.setAvataUrl(avataUrl != null ? avataUrl : "assets/img/default-avatar.png");
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
            user.setAvataUrl(avataUrl != null ? avataUrl : "assets/img/default-avatar.png");
            user.setPhone(phone);
            user.setAddress(address);
            user.setEmail(email);
            user.setRole(role);
            user.setAccountStatus(accountStatus != null ? accountStatus : "active");

            try {
                String hashedPassword = PasswordHashUtil.hashPassword(password);
                user.setPasswordHash(hashedPassword);
            } catch (Exception e) {
                request.setAttribute("user", user);
                request.setAttribute("error", "Error hashing password");
                request.getRequestDispatcher("adduser.jsp").forward(request, response);
                return;
            }

            boolean success = userDAO.insertUser(user);
            request.getSession().setAttribute("message", success ? "User created successfully" : "Failed to create user");
            response.sendRedirect("manageuser");
        }
    }

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return System.currentTimeMillis() + "_" + item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "";
    }
}