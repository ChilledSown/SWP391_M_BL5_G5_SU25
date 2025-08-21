/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbproject://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public class CourseDAO extends DBContext {

    public List<Course> getFilteredCourses(String searchTerm, String priceFilter, String ratingFilter, String sortBy, String topicFilter) {
        List<Course> courses = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.*, ISNULL(ar.AverageRating, 0) AS AverageRating\n");
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

        sql.append("GROUP BY c.Course_Id, c.Title, c.Description, c.Price, c.Thumbnail_Url, c.Created_At, c.Updated_At, c.Topic_Id\n");

        // Thêm HAVING clause cho rating filter
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
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
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
        sql.append("SELECT c.*, ISNULL(ar.AverageRating, 0) AS AverageRating\n");
        sql.append("FROM Course c\n");
        sql.append("LEFT JOIN (SELECT Course_Id, AVG(Rating) AS AverageRating FROM Review GROUP BY Course_Id) ar ON ar.Course_Id = c.Course_Id\n");

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

        // Không cần GROUP BY nữa vì đã dùng subquery
        if (ratingFilter != null && !ratingFilter.trim().isEmpty()) {
            sql.append("HAVING ISNULL(ar.AverageRating, 0) >= ?\n");
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
                sql.append("ISNULL(ar.AverageRating, 0) DESC\n");
                break;
            default:
                sql.append("c.Created_At DESC\n");
                break;
        }

        if (limit != Integer.MAX_VALUE) {
            sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY\n");
        }

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            for (Object param : parameters) {
                statement.setObject(paramIndex++, param);
            }
            if (limit != Integer.MAX_VALUE) {
                statement.setInt(paramIndex++, offset);
                statement.setInt(paramIndex, limit);
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
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
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

    public List<Course> getPurchasedCoursesByUser(long userId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT DISTINCT c.* "
                + "FROM Course c "
                + "JOIN Order_Detail od ON od.Course_Id = c.Course_Id "
                + "JOIN [Order] o ON o.Order_Id = od.Order_Id "
                + "LEFT JOIN Payment p ON p.Order_Id = o.Order_Id "
                + "WHERE o.User_Id = ? AND o.Status = 'paid'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                c.setCourse_id(rs.getLong("Course_Id"));
                c.setTitle(rs.getString("Title"));
                c.setDescription(rs.getString("Description"));
                c.setPrice(rs.getInt("Price"));
                c.setThumbnail_url(rs.getString("Thumbnail_Url"));
                c.setCreated_at(rs.getTimestamp("Created_At") == null ? null : new java.util.Date(rs.getTimestamp("Created_At").getTime()));
                c.setUpdated_at(rs.getTimestamp("Updated_At") == null ? null : new java.util.Date(rs.getTimestamp("Updated_At").getTime()));
                c.setTopic_id(rs.getLong("Topic_Id"));
                courses.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public boolean hasUserPurchasedCourse(long userId, long courseId) {
        String sql = "SELECT 1 "
                + "FROM [Order] o "
                + "JOIN Order_Detail od ON od.Order_Id = o.Order_Id "
                + "LEFT JOIN Payment p ON p.Order_Id = o.Order_Id "
                + "WHERE o.User_Id = ? AND od.Course_Id = ? AND (o.Status = 'paid' OR p.Payment_Status = 'completed')";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, courseId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void markCourseAsDeleted(long courseId) {
        String sql = "UPDATE Course SET Status = 'inactive' WHERE Course_Id = ?";
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
                Course c = new Course(
                        rs.getLong("Course_Id"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getInt("Price"),
                        rs.getString("Thumbnail_Url"),
                        rs.getDate("Created_At"),
                        rs.getDate("Updated_At"),
                        rs.getLong("Topic_Id"),
                        null // averageRating nếu chưa có trong query
                );
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
                Course c = new Course();
                c.setCourse_id(rs.getLong("Course_Id"));
                c.setTitle(rs.getString("Title"));
                c.setDescription(rs.getString("Description"));
                c.setPrice(rs.getInt("Price"));
                c.setThumbnail_url(rs.getString("Thumbnail_Url"));
                c.setCreated_at(rs.getTimestamp("Created_At"));
                c.setUpdated_at(rs.getTimestamp("Updated_At"));
                c.setTopic_id(rs.getLong("Topic_Id"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Course> getCoursesByTopicId(long topicId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course WHERE Topic_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, topicId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                c.setCourse_id(rs.getLong("Course_Id"));
                c.setTitle(rs.getString("Title"));
                c.setDescription(rs.getString("Description"));
                c.setPrice(rs.getInt("Price"));
                c.setThumbnail_url(rs.getString("Thumbnail_Url"));
                c.setCreated_at(rs.getDate("Created_At"));
                c.setUpdated_at(rs.getDate("Updated_At"));
                c.setTopic_id(rs.getLong("Topic_Id"));
                courses.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    // lay phan trang theo ID chu khong phai All tren kia 
    public List<Course> getCoursesByCreatorPaged(int creatorId, int offset, int limit) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, ISNULL(ar.AverageRating, 0) AS AverageRating\n"
                + "FROM Course c\n"
                + "LEFT JOIN (\n"
                + "    SELECT Course_Id, AVG(Rating) AS AverageRating\n"
                + "    FROM Review\n"
                + "    GROUP BY Course_Id\n"
                + ") ar ON ar.Course_Id = c.Course_Id\n"
                + "WHERE c.Created_By = ? AND c.Status = 'active'\n"
                + "ORDER BY c.Title ASC\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, creatorId);
            statement.setInt(2, offset);
            statement.setInt(3, limit);
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

    // lay count de phan trang 
    public int getTotalCoursesByCreator(int creatorId) {
        String sql = "SELECT COUNT(*) FROM Course WHERE Created_By = ? AND Status = 'active'";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, creatorId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //check title trung 
    public boolean isTitleExists(String title) {
        String sql = "SELECT 1 FROM Course WHERE Title = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Nếu có bản ghi => title đã tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // filter by ID 
    public List<Course> getFilteredCoursesByCreatorPaged(int creatorId, String title, String createdDate, Long topicId, int offset, int limit) {
        List<Course> courses = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.Course_Id, c.Title, c.Description, c.Price, c.Thumbnail_Url, ");
        sql.append("c.Created_At, c.Updated_At, c.Topic_Id, c.Status, ");
        sql.append("ISNULL(ar.AverageRating, 0) AS AverageRating ");
        sql.append("FROM Course c ");
        sql.append("LEFT JOIN ( ");
        sql.append("    SELECT Course_Id, AVG(Rating) AS AverageRating ");
        sql.append("    FROM Review GROUP BY Course_Id ");
        sql.append(") ar ON ar.Course_Id = c.Course_Id ");
        sql.append("WHERE c.Created_By = ? AND c.Status = 'active' ");

        List<Object> parameters = new ArrayList<>();
        parameters.add(creatorId);

        // 🔍 Fuzzy search theo title
        sql.append(buildFuzzySqlCondition(title, parameters));

        // Filter by created date
        if (createdDate != null && !createdDate.trim().isEmpty()) {
            sql.append(" AND CAST(c.Created_At AS DATE) = ? ");
            parameters.add(createdDate);
        }

        // Filter by topic
        if (topicId != null && topicId > 0) {
            sql.append(" AND c.Topic_Id = ? ");
            parameters.add(topicId);
        }

        sql.append("ORDER BY c.Title ASC ");
        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

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

    public int countFilteredCoursesByCreator(int creatorId, String title, String createdDate, Long topicId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS Total ");
        sql.append("FROM Course c ");
        sql.append("WHERE c.Created_By = ? AND c.Status = 'active' ");

        List<Object> parameters = new ArrayList<>();
        parameters.add(creatorId);

        // 🔍 Fuzzy search theo title
        sql.append(buildFuzzySqlCondition(title, parameters));

        // Filter by created date
        if (createdDate != null && !createdDate.trim().isEmpty()) {
            sql.append(" AND CAST(c.Created_At AS DATE) = ? ");
            parameters.add(createdDate);
        }

        // Filter by topic
        if (topicId != null && topicId > 0) {
            sql.append(" AND c.Topic_Id = ? ");
            parameters.add(topicId);
        }

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            for (Object param : parameters) {
                statement.setObject(paramIndex++, param);
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

    // Tách từ khoá và build SQL LIKE nhiều phần validate seacj
    private String buildFuzzySqlCondition(String input, List<Object> params) {
        StringBuilder condition = new StringBuilder();
        if (input != null && !input.trim().isEmpty()) {
            String cleaned = input.trim().toLowerCase().replaceAll("\\s+", " ");
            String[] keywords = cleaned.split(" ");

            for (String keyword : keywords) {
                condition.append(" AND LOWER(c.Title) LIKE ? ");
                params.add("%" + keyword + "%");
            }
        }
        return condition.toString();
    }

     public List<Course> getCoursesByTopicIdWithSearch(String query, long topicId, int offset, int limit) {
        List<Course> courses = new ArrayList<>();
        String search = (query == null ? "" : query.trim());
        String like = "%" + search + "%";
        String sql = "SELECT c.*, ISNULL(ar.AverageRating, 0) AS AverageRating\n"
                + "FROM Course c\n"
                + "LEFT JOIN (SELECT Course_Id, AVG(Rating) AS AverageRating FROM Review GROUP BY Course_Id) ar ON ar.Course_Id = c.Course_Id\n"
                + "WHERE c.Topic_Id = ? AND (c.Title LIKE ? OR c.Description LIKE ?) AND c.Status = 'active'\n"
                + "ORDER BY c.Created_At DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, topicId);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setInt(4, offset);
            ps.setInt(5, limit);
            ResultSet resultSet = ps.executeQuery();
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

    public int getTotalCoursesByTopicIdWithSearch(String query, long topicId) {
        String search = (query == null ? "" : query.trim());
        String like = "%" + search + "%";
        String sql = "SELECT COUNT(*) FROM Course c WHERE c.Topic_Id = ? AND (c.Title LIKE ? OR c.Description LIKE ?) AND c.Status = 'active'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, topicId);
            ps.setString(2, like);
            ps.setString(3, like);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}