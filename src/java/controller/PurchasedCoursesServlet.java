package controller;

import dal.CourseDAO;
import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.User;

@WebServlet(name = "PurchasedCoursesServlet", urlPatterns = {"/purchased-courses"})
public class PurchasedCoursesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) { response.sendRedirect("login.jsp"); return; }
        User user = (User) session.getAttribute("user");
        if (user == null) { response.sendRedirect("login.jsp"); return; }

        List<Course> courses = new ArrayList<>();
        try {
            DBContext db = new DBContext();
            String sql = "SELECT c.* FROM Course c JOIN Order_Detail od ON od.Course_Id = c.Course_Id JOIN [Order] o ON o.Order_Id = od.Order_Id WHERE o.User_Id = ? AND o.Status = 'paid'";
            PreparedStatement ps = db.connection.prepareStatement(sql);
            ps.setLong(1, user.getUser_id());
            ResultSet rs = ps.executeQuery();
            CourseDAO cdao = new CourseDAO();
            while (rs.next()) {
                Course c = new Course();
                c.setCourse_id(rs.getLong("Course_Id"));
                c.setTitle(rs.getString("Title"));
                c.setDescription(rs.getString("Description"));
                c.setPrice(rs.getInt("Price"));
                c.setThumbnail_url(rs.getString("Thumbnail_Url"));
                c.setCreated_at(rs.getTimestamp("Created_At"));
                c.setUpdated_at(rs.getTimestamp("Updated_At"));
                c.setTopic_id(rs.getLong("Topic_Id"));
                courses.add(c);
            }
        } catch (Exception e) { e.printStackTrace(); }

        request.setAttribute("courses", courses);
        request.getRequestDispatcher("purchased-courses.jsp").forward(request, response);
    }
}



