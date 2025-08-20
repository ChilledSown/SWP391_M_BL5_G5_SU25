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
        String sql = "SELECT * FROM Blog ORDER BY Created_At DESC"; // Sắp xếp theo ngày tạo mới nhất
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                blogs.add(new Blog(
                    rs.getLong("BlogId"),
                    rs.getString("Title"),
                    rs.getString("Content"),
                    rs.getString("ThumbnailUrl"),
                    rs.getTimestamp("Created_At"),
                    rs.getTimestamp("Updated_At"),
                    rs.getString("CreatedBy")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    public Blog getBlogById(long blogId) {
        String sql = "SELECT * FROM Blog WHERE BlogId = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, blogId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Blog(
                    rs.getLong("BlogId"),
                    rs.getString("Title"),
                    rs.getString("Content"),
                    rs.getString("ThumbnailUrl"),
                    rs.getTimestamp("Created_At"),
                    rs.getTimestamp("Updated_At"),
                    rs.getString("CreatedBy")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}