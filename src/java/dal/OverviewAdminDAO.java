package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OverviewAdminDAO extends DBContext {

    // Get total number of users
    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM Users";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get total number of courses
    public int getTotalCourses() {
        String sql = "SELECT COUNT(*) FROM Course";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get enrollment counts per course
    public List<Map<String, Object>> getCourseEnrollmentCounts() {
        List<Map<String, Object>> enrollments = new ArrayList<>();
        String sql = "SELECT c.Course_Id, c.Title, COUNT(DISTINCT o.User_Id) AS EnrollmentCount " +
                     "FROM Course c " +
                     "LEFT JOIN Order_Detail od ON c.Course_Id = od.Course_Id " +
                     "LEFT JOIN [Order] o ON od.Order_Id = o.Order_Id AND o.Status = 'Completed' " +
                     "GROUP BY c.Course_Id, c.Title " +
                     "ORDER BY c.Course_Id";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> enrollment = new HashMap<>();
                enrollment.put("courseId", rs.getLong("Course_Id"));
                enrollment.put("title", rs.getString("Title"));
                enrollment.put("enrollmentCount", rs.getInt("EnrollmentCount"));
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    // Get user growth data (daily registrations for the last 30 days)
    public Map<String, Integer> getUserGrowthData() {
        Map<String, Integer> growthData = new HashMap<>();
        String sql = "SELECT CAST(Created_At AS DATE) AS RegistrationDate, COUNT(*) AS UserCount " +
                     "FROM Users " +
                     "WHERE Created_At >= DATEADD(DAY, -30, GETDATE()) " +
                     "GROUP BY CAST(Created_At AS DATE) " +
                     "ORDER BY RegistrationDate";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String date = rs.getString("RegistrationDate");
                int count = rs.getInt("UserCount");
                growthData.put(date, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return growthData;
    }

    // Get enrollment growth data (daily enrollments for the last 30 days)
    public Map<String, Integer> getEnrollmentGrowthData() {
        Map<String, Integer> growthData = new HashMap<>();
        String sql = "SELECT CAST(o.Created_At AS DATE) AS EnrollmentDate, COUNT(DISTINCT o.User_Id) AS EnrollmentCount " +
                     "FROM [Order] o " +
                     "JOIN Order_Detail od ON o.Order_Id = od.Order_Id " +
                     "WHERE o.Status = 'Completed' AND o.Created_At >= DATEADD(DAY, -30, GETDATE()) " +
                     "GROUP BY CAST(o.Created_At AS DATE) " +
                     "ORDER BY EnrollmentDate";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String date = rs.getString("EnrollmentDate");
                int count = rs.getInt("EnrollmentCount");
                growthData.put(date, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return growthData;
    }
}