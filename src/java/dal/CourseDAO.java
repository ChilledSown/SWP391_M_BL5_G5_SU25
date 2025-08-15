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
        sql.append("SELECT c.*, ISNULL(ar.AverageRating, 0) AS AverageRating\n");
        sql.append("FROM Course c\n");
        sql.append("LEFT JOIN (SELECT Course_Id, AVG(Rating) AS AverageRating FROM Review GROUP BY Course_Id) ar ON ar.Course_Id = c.Course_Id\n");
        
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
        
        // Filter by topic
        if (topicFilter != null && !topicFilter.trim().isEmpty()) {
            String topicValue = topicFilter.trim();
            
            // Kiểm tra xem có phải là "Topic X" format không (từ fallback)
            if (topicValue.startsWith("Topic ")) {
                try {
                    String topicIdStr = topicValue.substring(6); // Bỏ "Topic " prefix
                    Long topicId = Long.parseLong(topicIdStr);
                    conditions.add("c.Topic_Id = ?");
                    parameters.add(topicId);
                } catch (NumberFormatException e) {
                    // Nếu không parse được, bỏ qua filter này
                    System.err.println("Invalid topic ID format: " + topicValue);
                }
            } else {
                // Thử filter theo topic name
                try {
                    conditions.add("c.Topic_Id IN (SELECT Topic_Id FROM Topic WHERE Name = ?)");
                    parameters.add(topicValue);
                } catch (Exception e) {
                    // Nếu bảng Topic không tồn tại, bỏ qua filter này
                    System.err.println("Topic table not accessible, skipping topic filter");
                }
            }
        }
        
        // Thêm WHERE clause nếu có điều kiện
        if (!conditions.isEmpty()) {
            sql.append("WHERE ").append(String.join(" AND ", conditions)).append("\n");
        }
        
        // Rating filter works on aggregated table alias
        if (ratingFilter != null && !ratingFilter.trim().isEmpty()) {
            double minRating = Double.parseDouble(ratingFilter);
            conditions.add("ISNULL(ar.AverageRating, 0) >= ?");
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
                sql.append("ar.AverageRating DESC");
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
                Course course = new Course();
                course.setCourse_id(resultSet.getLong("Course_Id"));
                course.setTitle(resultSet.getString("Title"));
                course.setDescription(resultSet.getString("Description"));
                course.setPrice(resultSet.getInt("Price"));
                course.setThumbnail_url(resultSet.getString("Thumbnail_Url"));
                course.setCreated_at(resultSet.getTimestamp("Created_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Created_At").getTime()));
                course.setUpdated_at(resultSet.getTimestamp("Updated_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Updated_At").getTime()));
                course.setTopic_id(resultSet.getLong("Topic_Id"));
                course.setAverageRating(resultSet.getObject("AverageRating") != null ? resultSet.getDouble("AverageRating") : 0.0);
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
                Course course = new Course();
                course.setCourse_id(resultSet.getLong("Course_Id"));
                course.setTitle(resultSet.getString("Title"));
                course.setDescription(resultSet.getString("Description"));
                course.setPrice(resultSet.getInt("Price"));
                course.setThumbnail_url(resultSet.getString("Thumbnail_Url"));
                course.setCreated_at(resultSet.getTimestamp("Created_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Created_At").getTime()));
                course.setUpdated_at(resultSet.getTimestamp("Updated_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Updated_At").getTime()));
                course.setTopic_id(resultSet.getLong("Topic_Id"));
                course.setAverageRating(resultSet.getObject("AverageRating") != null ? resultSet.getDouble("AverageRating") : 0.0);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    // Pagination helpers
    public int countFilteredCourses(String searchTerm, String priceFilter, String ratingFilter, String topicFilter) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS Total FROM (\n");
        sql.append("SELECT c.Course_Id\n");
        sql.append("FROM Course c\n");
        sql.append("LEFT JOIN Review r ON c.Course_Id = r.Course_Id\n");

        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            conditions.add("(c.Title LIKE ? OR c.Description LIKE ?)");
            parameters.add("%" + searchTerm.trim() + "%");
            parameters.add("%" + searchTerm.trim() + "%");
        }

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

        if (topicFilter != null && !topicFilter.trim().isEmpty()) {
            String topicValue = topicFilter.trim();
            if (topicValue.startsWith("Topic ")) {
                try {
                    String topicIdStr = topicValue.substring(6);
                    Long topicId = Long.parseLong(topicIdStr);
                    conditions.add("c.Topic_Id = ?");
                    parameters.add(topicId);
                } catch (NumberFormatException e) {
                    // ignore invalid
                }
            } else {
                conditions.add("c.Topic_Id IN (SELECT Topic_Id FROM Topic WHERE Name = ?)");
                parameters.add(topicValue);
            }
        }

        if (!conditions.isEmpty()) {
            sql.append("WHERE ").append(String.join(" AND ", conditions)).append("\n");
        }

        sql.append("GROUP BY c.Course_Id\n");

        if (ratingFilter != null && !ratingFilter.trim().isEmpty()) {
            sql.append("HAVING AVG(r.Rating) >= ?\n");
            parameters.add(Double.parseDouble(ratingFilter));
        }

        sql.append(") t");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countAllCourses() {
        String sql = "SELECT COUNT(*) AS Total FROM Course";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Course> getFilteredCoursesPaged(String searchTerm, String priceFilter, String ratingFilter, String sortBy, String topicFilter, int offset, int limit) {
        List<Course> courses = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.*, AVG(r.Rating) AS AverageRating\n");
        sql.append("FROM Course c\n");
        sql.append("LEFT JOIN Review r ON c.Course_Id = r.Course_Id\n");

        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            conditions.add("(c.Title LIKE ? OR c.Description LIKE ?)");
            parameters.add("%" + searchTerm.trim() + "%");
            parameters.add("%" + searchTerm.trim() + "%");
        }

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

        if (topicFilter != null && !topicFilter.trim().isEmpty()) {
            String topicValue = topicFilter.trim();
            if (topicValue.startsWith("Topic ")) {
                try {
                    String topicIdStr = topicValue.substring(6);
                    Long topicId = Long.parseLong(topicIdStr);
                    conditions.add("c.Topic_Id = ?");
                    parameters.add(topicId);
                } catch (NumberFormatException e) {
                    // ignore
                }
            } else {
                conditions.add("c.Topic_Id IN (SELECT Topic_Id FROM Topic WHERE Name = ?)");
                parameters.add(topicValue);
            }
        }

        if (!conditions.isEmpty()) {
            sql.append("WHERE ").append(String.join(" AND ", conditions)).append("\n");
        }

        sql.append("GROUP BY c.Course_Id, c.Title, c.Description, c.Price, c.Thumbnail_Url, c.Created_At, c.Updated_At, c.Topic_Id\n");

        if (ratingFilter != null && !ratingFilter.trim().isEmpty()) {
            sql.append("HAVING AVG(r.Rating) >= ?\n");
            parameters.add(Double.parseDouble(ratingFilter));
        }

        sql.append("ORDER BY ");
        switch (sortBy != null ? sortBy : "newest") {
            case "oldest":
                sql.append("c.Created_At ASC\n");
                break;
            case "price-low":
                sql.append("c.Price ASC\n");
                break;
            case "price-high":
                sql.append("c.Price DESC\n");
                break;
            case "rating":
                sql.append("AverageRating DESC\n");
                break;
            default:
                sql.append("c.Created_At DESC\n");
                break;
        }
        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY\n");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            for (Object param : parameters) {
                statement.setObject(paramIndex++, param);
            }
            statement.setInt(paramIndex++, offset);
            statement.setInt(paramIndex, limit);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourse_id(resultSet.getLong("Course_Id"));
                course.setTitle(resultSet.getString("Title"));
                course.setDescription(resultSet.getString("Description"));
                course.setPrice(resultSet.getInt("Price"));
                course.setThumbnail_url(resultSet.getString("Thumbnail_Url"));
                course.setCreated_at(resultSet.getTimestamp("Created_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Created_At").getTime()));
                course.setUpdated_at(resultSet.getTimestamp("Updated_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Updated_At").getTime()));
                course.setTopic_id(resultSet.getLong("Topic_Id"));
                course.setAverageRating(resultSet.getObject("AverageRating") != null ? resultSet.getDouble("AverageRating") : 0.0);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public List<Course> getAllCoursePaged(int offset, int limit) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, ISNULL(ar.AverageRating, 0) AS AverageRating "
                   + "FROM Course c "
                   + "LEFT JOIN (SELECT Course_Id, AVG(Rating) AS AverageRating FROM Review GROUP BY Course_Id) ar ON ar.Course_Id = c.Course_Id "
                   + "ORDER BY c.Created_At DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, offset);
            statement.setInt(2, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourse_id(resultSet.getLong("Course_Id"));
                course.setTitle(resultSet.getString("Title"));
                course.setDescription(resultSet.getString("Description"));
                course.setPrice(resultSet.getInt("Price"));
                course.setThumbnail_url(resultSet.getString("Thumbnail_Url"));
                course.setCreated_at(resultSet.getTimestamp("Created_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Created_At").getTime()));
                course.setUpdated_at(resultSet.getTimestamp("Updated_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Updated_At").getTime()));
                course.setTopic_id(resultSet.getLong("Topic_Id"));
                course.setAverageRating(resultSet.getObject("AverageRating") != null ? resultSet.getDouble("AverageRating") : 0.0);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    public List<Course> get3LatestCourse() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT TOP 3 c.*, ISNULL(ar.AverageRating, 0) AS AverageRating\n"
                + "FROM Course c\n"
                + "LEFT JOIN (SELECT Course_Id, AVG(Rating) AS AverageRating FROM Review GROUP BY Course_Id) ar ON ar.Course_Id = c.Course_Id\n"
                + "ORDER BY c.Created_At DESC;";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourse_id(resultSet.getLong("Course_Id"));
                course.setTitle(resultSet.getString("Title"));
                course.setDescription(resultSet.getString("Description"));
                course.setPrice(resultSet.getInt("Price"));
                course.setThumbnail_url(resultSet.getString("Thumbnail_Url"));
                course.setCreated_at(resultSet.getTimestamp("Created_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Created_At").getTime()));
                course.setUpdated_at(resultSet.getTimestamp("Updated_At") == null ? null : new java.util.Date(resultSet.getTimestamp("Updated_At").getTime()));
                course.setTopic_id(resultSet.getLong("Topic_Id"));
                course.setAverageRating(resultSet.getObject("AverageRating") != null ? resultSet.getDouble("AverageRating") : 0.0);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    public static void main(String[] args) {
        CourseDAO cdao = new CourseDAO();
        List<Course> listCourse = cdao.get3LatestCourse();
        System.out.println(listCourse);
    }
}
