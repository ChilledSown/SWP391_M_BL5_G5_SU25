package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.AdminListDAO;
import model.Topic;
import java.util.List;

@WebServlet(name="AdminTopicServlet", urlPatterns={"/admintopic"})
public class AdminTopicServlet extends HttpServlet {

    private final int PAGE_SIZE = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AdminListDAO dao = new AdminListDAO();

        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
            }
        }

        String searchQuery = request.getParameter("query");
        List<Topic> topics;
        int totalTopics;
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            topics = dao.getTopicsWithPagination(searchQuery, page, PAGE_SIZE);
            totalTopics = dao.getTotalTopics(searchQuery);
        } else {
            topics = dao.getTopicsWithPagination(null, page, PAGE_SIZE);
            totalTopics = dao.getTotalTopics(null);
        }
        int totalPages = (int) Math.ceil((double) totalTopics / PAGE_SIZE);

        request.setAttribute("topics", topics);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchQuery", searchQuery);

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }
        request.getRequestDispatcher("admintopic.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); 
    }
}