package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Topic;
import model.Course;
import java.sql.Date;

public class TopicDAO {
    private DBContext dbContext = new DBContext();

    public List<Topic> getAllTopics(int page, int pageSize) {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT Topic_Id, Name, Thumbnail_Url, Description FROM Topic ORDER BY Topic_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = dbContext.connection.prepareStatement(sql);
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                Topic t = new Topic();
                t.setTopic_id(rs.getLong("Topic_Id"));
                t.setName(rs.getString("Name"));
                t.setThumbnail_Url(rs.getString("Thumbnail_Url"));
                t.setDescription(rs.getString("Description"));
                topics.add(t);
            }
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error retrieving topics", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error closing resources", e);
            }
        }
        return topics;
    }

    public List<Topic> searchTopics(String query, int page, int pageSize) {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT Topic_Id, Name, Thumbnail_Url, Description FROM Topic WHERE Name LIKE ? ORDER BY Topic_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = dbContext.connection.prepareStatement(sql);
            ps.setString(1, "%" + query + "%");
            ps.setInt(2, (page - 1) * pageSize);
            ps.setInt(3, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                Topic t = new Topic();
                t.setTopic_id(rs.getLong("Topic_Id"));
                t.setName(rs.getString("Name"));
                t.setThumbnail_Url(rs.getString("Thumbnail_Url"));
                t.setDescription(rs.getString("Description"));
                topics.add(t);
            }
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error searching topics", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error closing resources", e);
            }
        }
        return topics;
    }

    public int getTotalTopics() {
        String sql = "SELECT COUNT(*) FROM Topic";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = dbContext.connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error counting topics", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error closing resources", e);
            }
        }
        return 0;
    }

    public Topic getTopicById(long topicId) {
        String sql = "SELECT Topic_Id, Name, Thumbnail_Url, Description FROM Topic WHERE Topic_Id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = dbContext.connection.prepareStatement(sql);
            ps.setLong(1, topicId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Topic t = new Topic();
                t.setTopic_id(rs.getLong("Topic_Id"));
                t.setName(rs.getString("Name"));
                t.setThumbnail_Url(rs.getString("Thumbnail_Url"));
                t.setDescription(rs.getString("Description"));
                return t;
            }
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error retrieving topic by ID: " + topicId, e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error closing resources", e);
            }
        }
        return null;
    }

    public boolean insertTopic(Topic topic) {
        String sql = "INSERT INTO Topic (Name, Thumbnail_Url, Description) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = dbContext.connection.prepareStatement(sql);
            ps.setString(1, topic.getName());
            ps.setString(2, topic.getThumbnail_Url());
            ps.setString(3, topic.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error inserting topic", e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error closing resources", e);
            }
        }
        return false;
    }

    public boolean updateTopic(Topic topic) {
        String sql = "UPDATE Topic SET Name = ?, Thumbnail_Url = ?, Description = ? WHERE Topic_Id = ?";
        PreparedStatement ps = null;
        try {
            ps = dbContext.connection.prepareStatement(sql);
            ps.setString(1, topic.getName());
            ps.setString(2, topic.getThumbnail_Url());
            ps.setString(3, topic.getDescription());
            ps.setLong(4, topic.getTopic_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error updating topic with ID: " + topic.getTopic_id(), e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error closing resources", e);
            }
        }
        return false;
    }

    public String deleteTopic(long topicId) {
        Connection conn = dbContext.connection;
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Xóa topic
            String sqlDeleteTopic = "DELETE FROM Topic WHERE Topic_Id = ?";
            ps = conn.prepareStatement(sqlDeleteTopic);
            ps.setLong(1, topicId);
            int rowsAffected = ps.executeUpdate();

            conn.commit(); // Commit transaction nếu thành công
            return rowsAffected > 0 ? "Topic deleted successfully" : "Topic not found";
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Rollback nếu lỗi
            } catch (SQLException ex) {
                Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error rolling back transaction for topic ID: " + topicId, ex);
            }
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error deleting topic with ID: " + topicId, e);
            String errorMessage = e.getMessage();
            if (errorMessage.contains("foreign key constraint")) {
                return "Failed to delete topic: There are courses associated with this topic. Please delete related courses first or enable cascade delete.";
            }
            return "Failed to delete topic: " + errorMessage;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error closing resources", e);
            }
        }
    }

    public List<Course> getCoursesByTopicId(long topicId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT Course_Id, Title, Description, Price, Thumbnail_Url, Created_At, Updated_At, Topic_Id, AverageRating FROM Course WHERE Topic_Id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = dbContext.connection.prepareStatement(sql);
            ps.setLong(1, topicId);
            rs = ps.executeQuery();
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
                c.setAverageRating(rs.getDouble("AverageRating"));
                courses.add(c);
            }
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error retrieving courses for topic ID: " + topicId, e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error closing resources", e);
            }
        }
        return courses;
    }
}