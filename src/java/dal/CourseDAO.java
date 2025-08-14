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
import model.Course;

/**
 *
 * @author sondo
 */
public class CourseDAO extends DBContext{
     public List<Course> getAllCourse() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT \n"
                + "    c.*,\n"
                + "    AVG(r.Rating) AS AverageRating\n"
                + "FROM Course c\n"
                + "LEFT JOIN Review r ON c.Course_Id = r.Course_Id\n"
                + "GROUP BY c.Course_Id, c.Title, c.Description, c.Price, c.Thumbnail_Url, c.Created_At, c.Updated_At, c.Topic_Id\n"
                + "ORDER BY c.Created_At DESC;";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
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
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
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
}
