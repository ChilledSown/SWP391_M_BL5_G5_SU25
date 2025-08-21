package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
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
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
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
}