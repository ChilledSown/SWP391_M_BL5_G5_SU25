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

public class TopicDAO {
    private Connection connection;

    public TopicDAO() {
        DBContext dbContext = new DBContext();
        this.connection = dbContext.connection;
    }

    // Lấy danh sách tất cả topic từ bảng Topic
    public List<Topic> getAllTopics() {
        List<Topic> topicList = new ArrayList<>();
        String sql = "SELECT Topic_Id, Name, Description FROM Topic";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setTopicId(rs.getLong("Topic_Id"));
                topic.setName(rs.getString("Name"));
                topic.setDescription(rs.getString("Description"));
                topicList.add(topic);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return topicList;
    }

    // Lấy topic theo ID
    public Topic getTopicById(long topicId) {
        String sql = "SELECT Topic_Id, Name, Description FROM Topic WHERE Topic_Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, topicId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopicId(rs.getLong("Topic_Id"));
                    topic.setName(rs.getString("Name"));
                    topic.setDescription(rs.getString("Description"));
                    return topic;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Thêm topic mới
    public boolean addTopic(Topic topic) {
        String sql = "INSERT INTO Topic (Name, Description) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, topic.getName());
            stmt.setString(2, topic.getDescription());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // Cập nhật topic
    public boolean updateTopic(Topic topic) {
        String sql = "UPDATE Topic SET Name = ?, Description = ? WHERE Topic_Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, topic.getName());
            stmt.setString(2, topic.getDescription());
            stmt.setLong(3, topic.getTopicId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // Xóa topic theo ID
    public boolean deleteTopic(long topicId) {
        String sql = "DELETE FROM Topic WHERE Topic_Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, topicId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}