package dal;

import java.util.ArrayList;
import java.util.List;
import model.Blog;
import java.sql.*;
import java.time.LocalDate;


public class BlogDAO extends DBContext {

    public List<Blog> getBlogsByCreatorId(int createdBy) {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT Blog_Id, Title, Content, Thumbnail_Url, Created_At, Updated_At, Created_By "
                + "FROM Blog WHERE Created_By = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, createdBy);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                blogs.add(new Blog(
                        rs.getLong("Blog_Id"),
                        rs.getString("Title"),
                        rs.getString("Content"),
                        rs.getString("Thumbnail_Url"),
                        rs.getDate("Created_At").toLocalDate(),
                        rs.getDate("Updated_At").toLocalDate(),
                        rs.getString("Created_By")
                ));
            }
            System.out.println("Fetched " + blogs.size() + " blogs for creator: " + createdBy);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(BlogDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            System.out.println("SQL Error: " + ex.getMessage());
        }
        return blogs;
    }
}
