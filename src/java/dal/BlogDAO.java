package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Blog;

public class BlogDAO extends DBContext {

    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT b.Blog_Id, b.Title, b.Content, b.Thumbnail_Url, b.Created_At, b.Updated_At, b.Created_By, " +
                     "CONCAT(u.FirstName, ' ', u.MiddleName, ' ', u.LastName) AS CreatedByName " +
                     "FROM Blog b " +
                     "INNER JOIN Users u ON b.Created_By = u.UserID " +
                     "ORDER BY b.Created_At DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                blogs.add(new Blog(
                    rs.getLong("Blog_Id"),
                    rs.getString("Title"),
                    rs.getString("Content"),
                    rs.getString("Thumbnail_Url"),
                    rs.getTimestamp("Created_At"),
                    rs.getTimestamp("Updated_At"),
                    rs.getInt("Created_By"),
                    rs.getString("CreatedByName")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    public Blog getBlogById(long blogId) {
        String sql = "SELECT b.Blog_Id, b.Title, b.Content, b.Thumbnail_Url, b.Created_At, b.Updated_At, b.Created_By, " +
                     "CONCAT(u.FirstName, ' ', u.MiddleName, ' ', u.LastName) AS CreatedByName " +
                     "FROM Blog b " +
                     "INNER JOIN Users u ON b.Created_By = u.UserID " +
                     "WHERE b.Blog_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, blogId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Blog(
                    rs.getLong("Blog_Id"),
                    rs.getString("Title"),
                    rs.getString("Content"),
                    rs.getString("Thumbnail_Url"),
                    rs.getTimestamp("Created_At"),
                    rs.getTimestamp("Updated_At"),
                    rs.getInt("Created_By"),
                    rs.getString("CreatedByName")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Blog> getBlogsByCreatorId(int createdBy, String title, String createdDate) {
        List<Blog> blogs = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT b.Blog_Id, b.Title, b.Content, b.Thumbnail_Url, b.Created_At, b.Updated_At, b.Created_By, " +
            "CONCAT(u.FirstName, ' ', u.MiddleName, ' ', u.LastName) AS CreatedByName " +
            "FROM Blog b " +
            "INNER JOIN Users u ON b.Created_By = u.UserID " +
            "WHERE b.Created_By = ?"
        );

        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();
        parameters.add(createdBy);

        if (title != null && !title.trim().isEmpty()) {
            conditions.add("b.Title LIKE ?");
            parameters.add("%" + title + "%");
        }
        if (createdDate != null && !createdDate.trim().isEmpty()) {
            conditions.add("CAST(b.Created_At AS DATE) = ?");
            parameters.add(createdDate);
        }

        if (!conditions.isEmpty()) {
            sql.append(" AND ").append(String.join(" AND ", conditions));
        }
        sql.append(" ORDER BY b.Created_At DESC");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                blogs.add(new Blog(
                    rs.getLong("Blog_Id"),
                    rs.getString("Title"),
                    rs.getString("Content"),
                    rs.getString("Thumbnail_Url"),
                    rs.getTimestamp("Created_At"),
                    rs.getTimestamp("Updated_At"),
                    rs.getInt("Created_By"),
                    rs.getString("CreatedByName")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    public void createBlog(Blog blog) {
        String sql = "INSERT INTO Blog (Title, Content, Thumbnail_Url, Created_At, Updated_At, Created_By) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, blog.getTitle());
            ps.setString(2, blog.getContent());
            ps.setString(3, blog.getThumbnailUrl());
            ps.setTimestamp(4, blog.getCreatedAt());
            ps.setTimestamp(5, blog.getUpdatedAt());
            ps.setInt(6, blog.getCreatedBy());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBlog(Blog blog) {
        String sql = "UPDATE Blog SET Title = ?, Content = ?, Thumbnail_Url = ?, Updated_At = ? WHERE Blog_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, blog.getTitle());
            ps.setString(2, blog.getContent());
            ps.setString(3, blog.getThumbnailUrl());
            ps.setTimestamp(4, blog.getUpdatedAt());
            ps.setLong(5, blog.getBlogId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isBlogTitleExists(String title, Long excludeBlogId) {
        String sql = "SELECT COUNT(*) FROM Blog WHERE Title = ? AND (? IS NULL OR Blog_Id != ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            if (excludeBlogId != null) {
                ps.setLong(2, excludeBlogId);
                ps.setLong(3, excludeBlogId);
            } else {
                ps.setNull(2, java.sql.Types.BIGINT);
                ps.setNull(3, java.sql.Types.BIGINT);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteBlog(long blogId) {
        String sql = "DELETE FROM Blog WHERE Blog_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, blogId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}