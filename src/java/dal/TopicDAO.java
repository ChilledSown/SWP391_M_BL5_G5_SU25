package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Topic;

/**
 * Data Access Object for Topic-related database operations.
 */
public class TopicDAO extends DBContext {
    
    // Get all topics
    public List<Topic> getAllTopics() {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT * FROM topic ORDER BY topic_id";
        
        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setTopic_id(rs.getLong("Topic_Id"));
                topic.setName(rs.getString("Name"));
                topic.setDescription(rs.getString("Description"));
                topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all topics: " + e.getMessage());
        }
        
        return topics;
    }
    
    // Get topic by ID
    public Topic getTopicById(Long topicId) {
        String sql = "SELECT * FROM topic WHERE topic_id = ?";
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, topicId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopic_id(rs.getLong("Topic_Id"));
                    topic.setName(rs.getString("Name"));
                    topic.setDescription(rs.getString("Description"));
                    topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                    return topic;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting topic by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    // Get top 8 topics (for display in courses page)
    public List<Topic> getTopTopics() {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT TOP 8 * FROM topic ORDER BY topic_id";
        
        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setTopic_id(rs.getLong("Topic_Id"));
                topic.setName(rs.getString("Name"));
                topic.setDescription(rs.getString("Description"));
                topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            System.out.println("Error getting top topics: " + e.getMessage());
        }
        
        return topics;
    }
    
    // Get topic by course ID
    public Topic getTopicByCourseId(Long courseId) {
        String sql = "SELECT t.* FROM topic t INNER JOIN course c ON t.topic_id = c.topic_id WHERE c.course_id = ?";
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, courseId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopic_id(rs.getLong("Topic_Id"));
                    topic.setName(rs.getString("Name"));
                    topic.setDescription(rs.getString("Description"));
                    topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                    return topic;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting topic by course ID: " + e.getMessage());
        }
        
        return null;
    }

    // Get topic by name (for uniqueness validation)
    public Topic getTopicByName(String name) {
        String sql = "SELECT * FROM topic WHERE Name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopic_id(rs.getLong("Topic_Id"));
                    topic.setName(rs.getString("Name"));
                    topic.setDescription(rs.getString("Description"));
                    topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                    return topic;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Topic> getTopicsWithPagination(String query, int page, int pageSize) {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT * FROM Topic WHERE Name LIKE ? ORDER BY Topic_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + (query == null ? "" : query) + "%");
            ps.setInt(2, (page - 1) * pageSize);
            ps.setInt(3, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Topic t = new Topic();
                    t.setTopic_id(rs.getLong("Topic_Id"));
                    t.setName(rs.getString("Name"));
                    t.setThumbnail_url(rs.getString("Thumbnail_Url"));
                    t.setDescription(rs.getString("Description"));
                    topics.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topics;
    }

    public int getTotalTopics(String query) {
        String sql = "SELECT COUNT(*) FROM Topic WHERE Name LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + (query == null ? "" : query) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateTopic(Topic topic) {
        String sql = "UPDATE Topic SET Name = ?, Thumbnail_Url = ?, Description = ? WHERE Topic_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, topic.getName());
            ps.setString(2, topic.getThumbnail_url());
            ps.setString(3, topic.getDescription());
            ps.setLong(4, topic.getTopic_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTopic(long topicId) {
        Connection conn = null;
        try {
            conn = connection;
            conn.setAutoCommit(false); // Start transaction

            // 1. Delete Order_Detail
            String sqlOrderDetail = "DELETE FROM Order_Detail WHERE Course_Id IN (SELECT Course_Id FROM Course WHERE Topic_Id = ?)";
            try (PreparedStatement psOrderDetail = conn.prepareStatement(sqlOrderDetail)) {
                psOrderDetail.setLong(1, topicId);
                psOrderDetail.executeUpdate();
            }

            // 2. Delete Cart
            String sqlCart = "DELETE FROM Cart WHERE Course_Id IN (SELECT Course_Id FROM Course WHERE Topic_Id = ?)";
            try (PreparedStatement psCart = conn.prepareStatement(sqlCart)) {
                psCart.setLong(1, topicId);
                psCart.executeUpdate();
            }

            // 3. Delete Review
            String sqlReview = "DELETE FROM Review WHERE Course_Id IN (SELECT Course_Id FROM Course WHERE Topic_Id = ?)";
            try (PreparedStatement psReview = conn.prepareStatement(sqlReview)) {
                psReview.setLong(1, topicId);
                psReview.executeUpdate();
            }

            // 4. Delete Quizzes
            String sqlQuiz = "DELETE FROM Quiz WHERE Lesson_Id IN (SELECT Lesson_Id FROM Lesson WHERE Course_Id IN (SELECT Course_Id FROM Course WHERE Topic_Id = ?))";
            try (PreparedStatement psQuiz = conn.prepareStatement(sqlQuiz)) {
                psQuiz.setLong(1, topicId);
                psQuiz.executeUpdate();
            }

            // 5. Delete Lessons
            String sqlLesson = "DELETE FROM Lesson WHERE Course_Id IN (SELECT Course_Id FROM Course WHERE Topic_Id = ?)";
            try (PreparedStatement psLesson = conn.prepareStatement(sqlLesson)) {
                psLesson.setLong(1, topicId);
                psLesson.executeUpdate();
            }

            // 6. Delete Courses
            String sqlCourse = "DELETE FROM Course WHERE Topic_Id = ?";
            try (PreparedStatement psCourse = conn.prepareStatement(sqlCourse)) {
                psCourse.setLong(1, topicId);
                psCourse.executeUpdate();
            }

            // 7. Delete Topic
            String sqlTopic = "DELETE FROM Topic WHERE Topic_Id = ?";
            try (PreparedStatement psTopic = conn.prepareStatement(sqlTopic)) {
                psTopic.setLong(1, topicId);
                int rowsAffected = psTopic.executeUpdate();
                conn.commit(); // Commit transaction
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            System.out.println("Error deleting topic ID " + topicId + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restore auto-commit
                } catch (SQLException e) {
                    System.out.println("Error restoring auto-commit: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
    // insert Topic for Courses for Linh
    public long insertTopic(Topic topic) {
    String sql = "INSERT INTO Topic (Name, Description, Thumbnail_Url) VALUES (?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, topic.getName());
        ps.setString(2, topic.getDescription());
        ps.setString(3, topic.getThumbnail_url());
        ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1); // return topic_id
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;
}


    public static void main(String[] args) {
        TopicDAO topic = new TopicDAO();
        List<Topic> topics = topic.getAllTopics();
        for (Topic topic1 : topics) {
            System.out.println(topic1);
        }
    }
}