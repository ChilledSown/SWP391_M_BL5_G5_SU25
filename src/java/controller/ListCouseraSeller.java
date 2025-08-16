package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dal.CourseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Course;

/**
 *
 * @author Admin
 */
// list , pagintion , search

@WebServlet(name = "ListCouseraSeller", urlPatterns = {"/listCousera"})
//@WebServlet(urlPatterns = {"/listCousera"})
public class ListCouseraSeller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListCouseraSeller</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListCouseraSeller at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession();
    model.User currentUser = (model.User) session.getAttribute("user");

    if (currentUser == null || !"seller".equals(currentUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    int userId = currentUser.getUser_id().intValue();
    int page = 1;
    int pageSize = 3;

    String pageParam = request.getParameter("page");
    if (pageParam != null) {
        try {
            page = Math.max(1, Integer.parseInt(pageParam));
        } catch (NumberFormatException ignored) {}
    }
    int offset = (page - 1) * pageSize;

    // 🟡 Lấy các tham số filter
    String title = request.getParameter("title");
    String createdDate = request.getParameter("createdDate");
    String topicIdStr = request.getParameter("topicId");
    Long topicId = null;
    if (topicIdStr != null && !topicIdStr.isEmpty()) {
        try {
            topicId = Long.parseLong(topicIdStr);
        } catch (NumberFormatException e) {
            topicId = null;
        }
    }

    CourseDAO dao = new CourseDAO();

    // 🔵 Đếm tổng số khóa học phù hợp filter
    int totalItems = dao.countFilteredCoursesByCreator(userId, title, createdDate, topicId);
    int totalPages = (int) Math.ceil(totalItems / (double) pageSize);

    // 🔵 Lấy danh sách khóa học phân trang
    List<Course> courses = dao.getFilteredCoursesByCreatorPaged(userId, title, createdDate, topicId, offset, pageSize);

    // 🔁 Truyền ngược các filter về để giữ lại trong form
    request.setAttribute("title", title);
    request.setAttribute("createdDate", createdDate);
    request.setAttribute("topicId", topicIdStr); // để giữ selected trong <select>

    // Gửi sang JSP
    request.setAttribute("courses", courses);
    request.setAttribute("currentPage", page);
    request.setAttribute("totalPages", totalPages);

    // baseUrl giữ lại filter nếu có (để phân trang không mất filter)
    StringBuilder baseUrl = new StringBuilder("listCousera?");
    if (title != null) baseUrl.append("title=").append(title).append("&");
    if (createdDate != null) baseUrl.append("createdDate=").append(createdDate).append("&");
    if (topicIdStr != null) baseUrl.append("topicId=").append(topicIdStr).append("&");
    baseUrl.append("page"); // để phân trang hoạt động
    request.setAttribute("baseUrl", baseUrl.toString());

    request.getRequestDispatcher("seller.jsp").forward(request, response);
}

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
