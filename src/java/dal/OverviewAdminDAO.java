package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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

    // Get total number of admins
    public int getTotalAdmins() {
        String sql = "SELECT COUNT(*) FROM Users WHERE role = 'admin'";
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

    // Get total number of sellers
    public int getTotalInstructors() {
        String sql = "SELECT COUNT(*) FROM Users WHERE role = 'instructor'";
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

    // Get total number of customers
    public int getTotalCustomers() {
        String sql = "SELECT COUNT(*) FROM Users WHERE role = 'customer'";
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

    // Get total number of courses (giữ lại nếu cần, nhưng có thể xóa nếu không dùng)
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
}