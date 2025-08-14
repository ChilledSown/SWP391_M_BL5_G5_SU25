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

@WebServlet(name="LoginServlet", urlPatterns={"/login"})
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

        try {
            // Hash mật khẩu nhập vào để so sánh
            String hashedPassword = PasswordHashUtil.hashPassword(password);

            UserDAO dao = new UserDAO();
            User u = dao.login(email, hashedPassword);

            if (u != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", u);

                switch (u.getRole()) {
                    case "admin":
                         request.getRequestDispatcher("admin.jsp").forward(request, response);
                        break;
                    case "seller":
                         request.getRequestDispatcher("seller.jsp").forward(request, response);
                        break;
                    case "customer":
                         request.getRequestDispatcher("customer.jsp").forward(request, response);
                        break;
                }
            } else {
                request.setAttribute("message", "Email hoặc mật khẩu không đúng");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();         
        }
    }
}
