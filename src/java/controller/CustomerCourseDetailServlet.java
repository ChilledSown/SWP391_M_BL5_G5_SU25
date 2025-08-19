/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.CartDAO;
import dal.CourseDAO;
import dal.LessonDAO;
import dal.ReviewDAO;
import dal.TopicDAO;
import dal.UserDAO;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Course;
import model.Lesson;
import model.Review;
import model.Topic;
import model.User;

/**
 *
 * @author sondo
 */
@WebServlet(name="CustomerCourseDetailServlet", urlPatterns={"/customer-course-detail"})
public class CustomerCourseDetailServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            String courseIdStr = request.getParameter("id");
            if (courseIdStr == null || courseIdStr.trim().isEmpty()) {
                response.sendRedirect("courses");
                return;
            }
            
            long courseId = Long.parseLong(courseIdStr);
            
            // Get DAOs
            CourseDAO courseDAO = new CourseDAO();
            LessonDAO lessonDAO = new LessonDAO();
            ReviewDAO reviewDAO = new ReviewDAO();
            CartDAO cartDAO = new CartDAO();
            
            // Get course details
            Course course = courseDAO.getCourseById(courseId);
            if (course == null) {
                response.sendRedirect("courses");
                return;
            }
            
            // Get topic for this course
            TopicDAO topicDAO = new TopicDAO();
            Topic topic = topicDAO.getTopicByCourseId(courseId);
            
            // Get lessons for this course
            List<Lesson> lessons = lessonDAO.getLessonsByCourseId(courseId);
            
            // Get reviews for this course
            List<Review> reviews = reviewDAO.getReviewsByCourseId(courseId);
            // Map user info for each review
            UserDAO userDAO = new UserDAO();
            Map<Long, User> reviewUsersMap = new HashMap<>(); // key = user_id
            for (Review r : reviews) {
                if (r != null && r.getReview_id() != null) {
                    User reviewUser = userDAO.getUserByReview(r.getReview_id());
                    if (reviewUser != null) {
                        reviewUsersMap.put(r.getUser_id(), reviewUser);
                    }
                }
            }
            
            // Get average rating and review count
            double averageRating = reviewDAO.getAverageRatingByCourseId(courseId);
            int reviewCount = reviewDAO.getReviewCountByCourseId(courseId);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            long userId = user.getUser_id();
            Review userReview = null;
            Cart userCart = null;
            boolean isCourseInCart = false;
            
            // Get user's review for this course
            userReview = reviewDAO.getReviewByUserAndCourse(userId, courseId);
            
            // Check purchased status
            boolean hasPurchased = courseDAO.hasUserPurchasedCourse(userId, courseId);

            // Get user's cart
            userCart = cartDAO.getCartByUserId(userId);
            if (userCart != null) {
                isCourseInCart = cartDAO.isCourseInCart(userCart.getCart_id(), courseId);
            }
            
            // Set attributes for JSP
            request.setAttribute("course", course);
            request.setAttribute("topic", topic);
            request.setAttribute("lessons", lessons);
            request.setAttribute("reviews", reviews);
            request.setAttribute("reviewUsersMap", reviewUsersMap);
            request.setAttribute("averageRating", averageRating);
            request.setAttribute("reviewCount", reviewCount);
            request.setAttribute("userReview", userReview);
            request.setAttribute("userCart", userCart);
            request.setAttribute("isCourseInCart", isCourseInCart);
            request.setAttribute("hasPurchased", hasPurchased);
            
            // Forward to JSP
            request.getRequestDispatcher("customer-course-detail.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("courses");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Customer Course Detail Servlet";
    }
}
