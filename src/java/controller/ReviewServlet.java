package controller;

import dal.ReviewDAO;
import model.Review;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReviewServlet", urlPatterns = {"/listReviews"})
public class ReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        model.User user = (model.User) session.getAttribute("user");
        if (user == null || user.getUser_id() == null || !"instructor".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        ReviewDAO reviewDAO = new ReviewDAO();
        String courseTitle = request.getParameter("courseTitle");
        String reviewDate = request.getParameter("reviewDate");
        String rating = request.getParameter("rating");
        long createdBy = user.getUser_id();

        int page = 1;
        int pageSize = 10; 
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // Fetch reviews (pagination not implemented in DAO yet, assume full list for now)
        List<Review> reviews = reviewDAO.getReviewsByInstructor(createdBy, courseTitle, reviewDate, rating);
        int totalReviews = reviews.size(); // Replace with DAO count method if implemented

        request.setAttribute("reviews", reviews);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", (int) Math.ceil((double) totalReviews / pageSize));
        request.getRequestDispatcher("/reviewsForInstrructor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}