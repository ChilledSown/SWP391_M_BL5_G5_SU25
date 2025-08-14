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
    
    public List<Course> getFilteredCourses(String searchTerm, String priceFilter, String ratingFilter, String sortBy, String topicFilter) {
        List<Course> courses = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT \n");
        sql.append("    c.*,\n");
        sql.append("    AVG(r.Rating) AS AverageRating\n");
        sql.append("FROM Course c\n");
        sql.append("LEFT JOIN Review r ON c.Course_Id = r.Course_Id\n");
        
        // Thêm WHERE clause cho search và filter
        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();
        
        // Search by title hoặc description
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            conditions.add("(c.Title LIKE ? OR c.Description LIKE ?)");
            parameters.add("%" + searchTerm.trim() + "%");
            parameters.add("%" + searchTerm.trim() + "%");
        }
        
        // Filter by price
        if (priceFilter != null && !priceFilter.trim().isEmpty()) {
            switch (priceFilter) {
                case "0-50":
                    conditions.add("c.Price BETWEEN 0 AND 50");
                    break;
                case "51-100":
                    conditions.add("c.Price BETWEEN 51 AND 100");
                    break;
                case "101-200":
                    conditions.add("c.Price BETWEEN 101 AND 200");
                    break;
                case "201+":
                    conditions.add("c.Price >= 201");
                    break;
            }
        }
        
        // Filter by topic (nếu có bảng Topic)
        if (topicFilter != null && !topicFilter.trim().isEmpty()) {
            conditions.add("c.Topic_Id IN (SELECT Topic_Id FROM Topic WHERE Name LIKE ?)");
            parameters.add("%" + topicFilter.trim() + "%");
        }
        
        // Thêm WHERE clause nếu có điều kiện
        if (!conditions.isEmpty()) {
            sql.append("WHERE ").append(String.join(" AND ", conditions)).append("\n");
        }
        
        sql.append("GROUP BY c.Course_Id, c.Title, c.Description, c.Price, c.Thumbnail_Url, c.Created_At, c.Updated_At, c.Topic_Id\n");
        
        // Thêm HAVING clause cho rating filter
        if (ratingFilter != null && !ratingFilter.trim().isEmpty()) {
            double minRating = Double.parseDouble(ratingFilter);
            sql.append("HAVING AVG(r.Rating) >= ?\n");
            parameters.add(minRating);
        }
        
        // Thêm ORDER BY clause
        sql.append("ORDER BY ");
        switch (sortBy != null ? sortBy : "newest") {
            case "oldest":
                sql.append("c.Created_At ASC");
                break;
            case "price-low":
                sql.append("c.Price ASC");
                break;
            case "price-high":
                sql.append("c.Price DESC");
                break;
            case "rating":
                sql.append("AverageRating DESC");
                break;
            default: // newest
                sql.append("c.Created_At DESC");
                break;
        }
        
        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            // Set parameters
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            
            ResultSet resultSet = statement.executeQuery();
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
