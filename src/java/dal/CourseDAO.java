/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;

/**
 *
 * @author sondo
 */
public class CourseDAO extends DBContext {

    public List<Course> getAllCourse() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT \n"
                + "    c.*,\n"
                + "    AVG(r.Rating) AS AverageRating\n"
                + "FROM Course c\n"
                + "LEFT JOIN Review r ON c.Course_Id = r.Course_Id\n"
                + "GROUP BY c.Course_Id, c.Title, c.Description, c.Price, c.Thumbnail_Url, c.Created_At, c.Updated_At, c.Topic_Id\n"
                + "ORDER BY c.Created_At DESC;";
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Course course = Course.builder()
                        .course_id(resultSet.getLong("Course_Id"))
                        .title(resultSet.getString("Title"))
                        .description(resultSet.getString("Description"))
                        .price(resultSet.getInt("Price"))
                        .thumbnail_url(resultSet.getString("Thumbnail_Url"))
                        .created_at(resultSet.getTimestamp("Created_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Created_At").getTime()))
                        .updated_at(resultSet.getTimestamp("Updated_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Updated_At").getTime()))
                        .topic_id(resultSet.getLong("Topic_Id"))
                        .averageRating(resultSet.getObject("AverageRating") != null ? resultSet.getDouble("AverageRating") : 0.0)
                        .build();
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public List<Course> get3LatestCourse() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT TOP 3\n"
                + "    c.*,\n"
                + "    AVG(r.Rating) AS AverageRating\n"
                + "FROM Course c\n"
                + "LEFT JOIN Review r ON c.Course_Id = r.Course_Id\n"
                + "GROUP BY c.Course_Id, c.Title, c.Description, c.Price, c.Thumbnail_Url, c.Created_At, c.Updated_At, c.Topic_Id\n"
                + "ORDER BY c.Created_At DESC;";
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Course course = Course.builder()
                        .course_id(resultSet.getLong("Course_Id"))
                        .title(resultSet.getString("Title"))
                        .description(resultSet.getString("Description"))
                        .price(resultSet.getInt("Price"))
                        .thumbnail_url(resultSet.getString("Thumbnail_Url"))
                        .created_at(resultSet.getTimestamp("Created_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Created_At").getTime()))
                        .updated_at(resultSet.getTimestamp("Updated_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Updated_At").getTime()))
                        .topic_id(resultSet.getLong("Topic_Id"))
                        .averageRating(resultSet.getDouble("AverageRating"))
                        .build();
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    // lay theo ID for Seller ;

//    public List<Course> getCoursesByCreator(int userId) {
//        List<Course> courses = new ArrayList<>();
//        String sql = "SELECT Course_Id, Title, Description, Price, Thumbnail_Url, Created_At, Updated_At, Topic_Id "
//                + "FROM Course WHERE Created_By = ?";
//
//        try {
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setInt(1, userId);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                Course c = Course.builder()
//                        .course_id(rs.getLong("Course_Id"))
//                        .title(rs.getString("Title"))
//                        .description(rs.getString("Description"))
//                        .price(rs.getInt("Price"))
//                        .thumbnail_url(rs.getString("Thumbnail_Url"))
//                        .created_at(rs.getDate("Created_At"))
//                        .updated_at(rs.getDate("Updated_At"))
//                        .topic_id(rs.getLong("Topic_Id"))
//                        .build();
//                courses.add(c);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return courses;
//    }
    //delete = cach chang status
    public void markCourseAsDeleted(long courseId) {
        String sql = "UPDATE Course SET Status = 'deleted' WHERE Course_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
// lay status hoat dong 

    public List<Course> getCoursesByCreator(int userId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT Course_Id, Title, Description, Price, Thumbnail_Url, Created_At, Updated_At, Topic_Id "
                + "FROM Course WHERE Created_By = ? AND Status = 'active'";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course c = Course.builder()
                        .course_id(rs.getLong("Course_Id"))
                        .title(rs.getString("Title"))
                        .description(rs.getString("Description"))
                        .price(rs.getInt("Price"))
                        .thumbnail_url(rs.getString("Thumbnail_Url"))
                        .created_at(rs.getDate("Created_At"))
                        .updated_at(rs.getDate("Updated_At"))
                        .topic_id(rs.getLong("Topic_Id"))
                        .build();
                courses.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public void insertCourse(Course course, Long creatorId) {
        String sql = "INSERT INTO Course (Title, Description, Price, Thumbnail_Url, Created_At, Topic_Id, Created_By, Status) "
                + "VALUES (?, ?, ?, ?, GETDATE(), ?, ?, 'active')";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setInt(3, course.getPrice());
            ps.setString(4, course.getThumbnail_url());
            ps.setLong(5, course.getTopic_id());
            ps.setLong(6, creatorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCourse(long id, String title, String description, int price, String thumbnailUrl, long topicId) {
        String sql = "UPDATE Course SET Title=?, Description=?, Price=?, Thumbnail_Url=?, Topic_Id=?, Updated_At=GETDATE() WHERE Course_Id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setInt(3, price);
            ps.setString(4, thumbnailUrl);
            ps.setLong(5, topicId);
            ps.setLong(6, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Course getCourseById(long courseId) {
    String sql = "SELECT * FROM Course WHERE Course_Id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setLong(1, courseId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Course.builder()
                    .course_id(rs.getLong("Course_Id"))
                    .title(rs.getString("Title"))
                    .description(rs.getString("Description"))
                    .price(rs.getInt("Price"))
                    .thumbnail_url(rs.getString("Thumbnail_Url"))
                    .created_at(rs.getTimestamp("Created_At"))
                    .updated_at(rs.getTimestamp("Updated_At"))
                    .topic_id(rs.getLong("Topic_Id"))
                    .build();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


    public static void main(String[] args) {
        // Tạo đối tượng DAO
        CourseDAO dao = new CourseDAO();

        // Gọi hàm lấy course theo id, ví dụ id = 1
        Long courseId = 1L;
        List<Course> courses = dao.getCoursesByCreator(1);

        // In ra để kiểm tra
        for (Course c : courses) {
            System.out.println("ID: " + c.getCourse_id());
            System.out.println("Title: " + c.getTitle());
            System.out.println("Description: " + c.getDescription());
            System.out.println("Price: " + c.getPrice());
            System.out.println("Thumbnail URL: " + c.getThumbnail_url());
            System.out.println("Created At: " + c.getCreated_at());
            System.out.println("Updated At: " + c.getUpdated_at());
            System.out.println("Topic ID: " + c.getTopic_id());
            System.out.println("-----------------------");
        }
    }

}
