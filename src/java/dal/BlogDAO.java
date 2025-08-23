package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Blog;

public class BlogDAO extends DBContext {

    public List<Blog> getAllBlogs() {
        return getBlogs(1, Integer.MAX_VALUE, null); // Fallback to new method for all blogs
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

    public List<Blog> getBlogs(int page, int pageSize, String search) {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT b.Blog_Id, b.Title, b.Content, b.Thumbnail_Url, b.Created_At, b.Updated_At, b.Created_By, " +
                     "CONCAT(u.FirstName, ' ', u.MiddleName, ' ', u.LastName) AS CreatedByName " +
                     "FROM Blog b " +
                     "INNER JOIN Users u ON b.Created_By = u.UserID ";
        if (search != null && !search.isEmpty()) {
            sql += "WHERE b.Title LIKE ? ";
        }
        sql += "ORDER BY b.Created_At DESC " +
               "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int paramIndex = 1;
            if (search != null && !search.isEmpty()) {
                ps.setString(paramIndex++, "%" + search + "%");
            }
            int offset = (page - 1) * pageSize;
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex, pageSize);
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

    public int getTotalBlogs(String search) {
        String sql = "SELECT COUNT(*) FROM Blog b ";
        if (search != null && !search.isEmpty()) {
            sql += "WHERE b.Title LIKE ?";
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (search != null && !search.isEmpty()) {
                ps.setString(1, "%" + search + "%");
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Blog> getBlogsByTitle(String searchQuery, int page, int pageSize) {
        List<Blog> blogs = new ArrayList<>();
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return getBlogs(page, pageSize, null);
        }
        searchQuery = searchQuery.trim().replaceAll("\\s+", " ");
        String[] keywords = searchQuery.split(" ");
        if (keywords.length == 0) {
            return getBlogs(page, pageSize, null);
        }
        StringBuilder sqlBuilder = new StringBuilder(
            "SELECT b.Blog_Id, b.Title, b.Content, b.Thumbnail_Url, b.Created_At, b.Updated_At, b.Created_By, " +
            "CONCAT(u.FirstName, ' ', u.MiddleName, ' ', u.LastName) AS CreatedByName " +
            "FROM Blog b " +
            "INNER JOIN Users u ON b.Created_By = u.UserID WHERE ");
        for (int i = 0; i < keywords.length; i++) {
            if (i > 0) {
                sqlBuilder.append(" AND ");
            }
            sqlBuilder.append("(LOWER(b.Title) LIKE ?)");
        }
        sqlBuilder.append(" ORDER BY b.Created_At DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        String sql = sqlBuilder.toString();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int paramIndex = 1;
            for (String keyword : keywords) {
                ps.setString(paramIndex++, "%" + keyword.toLowerCase() + "%");
            }
            ps.setInt(paramIndex++, (page - 1) * pageSize);
            ps.setInt(paramIndex, pageSize);
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

    public int getTotalBlogsByTitle(String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return getTotalBlogs(null);
        }
        searchQuery = searchQuery.trim().replaceAll("\\s+", " ");
        String[] keywords = searchQuery.split(" ");
        if (keywords.length == 0) {
            return getTotalBlogs(null);
        }
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM Blog b WHERE ");
        for (int i = 0; i < keywords.length; i++) {
            if (i > 0) {
                sqlBuilder.append(" AND ");
            }
            sqlBuilder.append("(LOWER(b.Title) LIKE ?)");
        }
        String sql = sqlBuilder.toString();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int paramIndex = 1;
            for (String keyword : keywords) {
                ps.setString(paramIndex++, "%" + keyword.toLowerCase() + "%");
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}