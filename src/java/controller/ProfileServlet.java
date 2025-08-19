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
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.User;
import utils.PasswordHashUtil;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ProfileServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "Upload"; // Tên thư mục trong đường dẫn tuyệt đối
    private static final String UPLOAD_ABSOLUTE_PATH = "D:\\SU25\\SWP391_M_BL5_G5_SU25\\web\\Upload\\"; // Đường dẫn tuyệt đối của bạn

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        UserDAO dao = new UserDAO();
        User updatedUser = dao.getUserById(user.getUser_id());
        if (updatedUser != null) {
            session.setAttribute("user", updatedUser);
            request.setAttribute("user", updatedUser);
        }

        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            String firstName = request.getParameter("firstName");
            String middleName = request.getParameter("middleName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Validate input
            if (firstName == null || firstName.trim().isEmpty() ||
                middleName == null || middleName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty() ||
                address == null || address.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
                request.setAttribute("error", "Please fill in all required fields.");
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                return;
            }

            // Validate phone number
            if (!phone.matches("0\\d{9}")) {
                request.setAttribute("error", "Phone number must be 10 digits and start with 0.");
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                return;
            }

            // Check if email or phone already exists for another user
            UserDAO dao = new UserDAO();
            User existingEmailUser = dao.getUserByEmail(email);
            User existingPhoneUser = dao.getUserByPhone(phone);

            if (existingEmailUser != null && existingEmailUser.getUser_id() != currentUser.getUser_id()) {
                request.setAttribute("error", "This email is already in use by another account.");
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                return;
            }

            if (existingPhoneUser != null && existingPhoneUser.getUser_id() != currentUser.getUser_id()) {
                request.setAttribute("error", "This phone number is already in use by another account.");
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                return;
            }

            // Handle file upload
            String avatarUrl = currentUser.getAvataUrl();
            Part filePart = request.getPart("avatar");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = extractFileName(filePart);
                File uploadDir = new File(UPLOAD_ABSOLUTE_PATH);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String filePath = UPLOAD_ABSOLUTE_PATH + fileName;
                filePart.write(filePath);
                avatarUrl = "/uploads/" + fileName; // Đường dẫn tương đối dựa trên alias /uploads
                System.out.println("File saved at: " + filePath); // Log để kiểm tra
            }

            // Create updated user object
            User updatedUser = new User();
            updatedUser.setUser_id(currentUser.getUser_id());
            updatedUser.setFirstName(firstName);
            updatedUser.setMiddleName(middleName);
            updatedUser.setLastName(lastName);
            updatedUser.setAvataUrl(avatarUrl);
            updatedUser.setPhone(phone);
            updatedUser.setAddress(address);
            updatedUser.setEmail(email);
            updatedUser.setRole(currentUser.getRole());
            updatedUser.setAccountStatus(currentUser.getAccountStatus());

            // Handle password
            if (password != null && !password.trim().isEmpty()) {
                updatedUser.setPasswordHash(PasswordHashUtil.hashPassword(password));
            } else {
                updatedUser.setPasswordHash(currentUser.getPasswordHash());
            }

            // Update user in database
            boolean success = dao.updateUser(updatedUser);

            if (success) {
                session.setAttribute("user", updatedUser);
                request.setAttribute("user", updatedUser);
                request.setAttribute("message", "Profile updated successfully!");
            } else {
                request.setAttribute("user", currentUser);
                request.setAttribute("error", "Profile update failed. Please try again.");
            }

            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("user", currentUser);
            request.setAttribute("error", "An error occurred. Please try again.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
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