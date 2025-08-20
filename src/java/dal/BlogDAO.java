package dal;

import java.util.ArrayList;
import java.util.List;
import model.Blog;
import java.sql.*;
import java.time.LocalDate;

public class BlogDAO extends DBContext {
    public List<Blog> getBlogsByCreatorId(int createdBy, String title, String createdDate) {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT Blog_Id, Title, Content, Thumbnail_Url, Created_At, Updated_At, Created_By " +
                     "FROM Blog WHERE Created_By = ? AND Title LIKE ? AND CAST(Created_At AS DATE) LIKE ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, createdBy);
            stm.setString(2, "%" + (title != null ? title : "") + "%");
            stm.setString(3, createdDate != null ? createdDate : "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                blogs.add(new Blog(
                    rs.getLong("Blog_Id"),
                    rs.getString("Title"),
                    rs.getString("Content"),
                    rs.getString("Thumbnail_Url"),
                    rs.getDate("Created_At").toLocalDate(),
                    rs.getDate("Updated_At").toLocalDate(),
                    rs.getInt("Created_By") // Changed to getInt
                ));
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(BlogDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return blogs;
    }

    public Blog getBlogById(long blogId) {
        String sql = "SELECT Blog_Id, Title, Content, Thumbnail_Url, Created_At, Updated_At, Created_By " +
                     "FROM Blog WHERE Blog_Id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, blogId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new Blog(
                    rs.getLong("Blog_Id"),
                    rs.getString("Title"),
                    rs.getString("Content"),
                    rs.getString("Thumbnail_Url"),
                    rs.getDate("Created_At").toLocalDate(),
                    rs.getDate("Updated_At").toLocalDate(),
                    rs.getInt("Created_By") // Changed to getInt
                );
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(BlogDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }

    public void createBlog(Blog blog) {
        String sql = "INSERT INTO Blog (Title, Content, Thumbnail_Url, Created_At, Updated_At, Created_By) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, blog.getTitle());
            stm.setString(2, blog.getContent());
            stm.setString(3, blog.getThumbnailUrl());
            stm.setDate(4, java.sql.Date.valueOf(blog.getCreatedAt()));
            stm.setDate(5, java.sql.Date.valueOf(blog.getUpdatedAt()));
            stm.setInt(6, blog.getCreatedBy()); // Changed to setInt
            stm.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(BlogDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void updateBlog(Blog blog) {
        String sql = "UPDATE Blog SET Title = ?, Content = ?, Thumbnail_Url = ?, Updated_At = ? WHERE Blog_Id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, blog.getTitle());
            stm.setString(2, blog.getContent());
            stm.setString(3, blog.getThumbnailUrl());
            stm.setDate(4, java.sql.Date.valueOf(blog.getUpdatedAt()));
            stm.setLong(5, blog.getBlogId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(BlogDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public boolean isBlogTitleExists(String title, Long excludeBlogId) {
        String sql = "SELECT COUNT(*) FROM Blog WHERE Title = ? AND (? IS NULL OR Blog_Id != ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, title);
            if (excludeBlogId != null) {
                stm.setLong(2, excludeBlogId);
                stm.setLong(3, excludeBlogId);
            } else {
                stm.setNull(2, Types.BIGINT);
                stm.setNull(3, Types.BIGINT);
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(BlogDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return false;
    }
}