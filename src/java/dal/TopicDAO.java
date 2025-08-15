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
import model.Lesson;
import model.Quiz;

public class TopicDAO {
    private DBContext dbContext = new DBContext();

    public List<Topic> getAllTopics(int page, int pageSize) {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT Topic_Id, Name, Thumbnail_Url, Description FROM Topic ORDER BY Topic_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Topic t = new Topic();
                    t.setTopic_id(rs.getLong("Topic_Id"));
                    t.setName(rs.getString("Name"));
                    t.setThumbnail_Url(rs.getString("Thumbnail_Url"));
                    t.setDescription(rs.getString("Description"));
                    topics.add(t);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error retrieving topics", e);
        }
        return topics;
    }

    public List<Topic> searchTopics(String query, int page, int pageSize) {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT Topic_Id, Name, Thumbnail_Url, Description FROM Topic WHERE Name LIKE ? ORDER BY Topic_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setString(1, "%" + query + "%");
            ps.setInt(2, (page - 1) * pageSize);
            ps.setInt(3, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Topic t = new Topic();
                    t.setTopic_id(rs.getLong("Topic_Id"));
                    t.setName(rs.getString("Name"));
                    t.setThumbnail_Url(rs.getString("Thumbnail_Url"));
                    t.setDescription(rs.getString("Description"));
                    topics.add(t);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error searching topics", e);
        }
        return topics;
    }

    public int getTotalTopics() {
        String sql = "SELECT COUNT(*) FROM Topic";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error counting topics", e);
        }
        return 0;
    }

    public Topic getTopicById(long topicId) {
        String sql = "SELECT Topic_Id, Name, Thumbnail_Url, Description FROM Topic WHERE Topic_Id = ?";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setLong(1, topicId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Topic t = new Topic();
                    t.setTopic_id(rs.getLong("Topic_Id"));
                    t.setName(rs.getString("Name"));
                    t.setThumbnail_Url(rs.getString("Thumbnail_Url"));
                    t.setDescription(rs.getString("Description"));
                    return t;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error retrieving topic by ID: " + topicId, e);
        }
        return null;
    }

    public List<Course> getCoursesByTopicId(long topicId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT Course_Id, Title, Description, Price, Thumbnail_Url, Created_At, Updated_At, Topic_Id FROM Course WHERE Topic_Id = ?";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setLong(1, topicId);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error retrieving courses for topic ID: " + topicId, e);
        }
        return courses;
    }

    public List<Lesson> getLessonsByCourseId(long courseId) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT Lesson_Id, Title, Video_Url, Content, Created_At, Updated_At, Course_Id FROM Lesson WHERE Course_Id = ?";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lesson l = new Lesson();
                    l.setLesson_id(rs.getLong("Lesson_Id"));
                    l.setTitle(rs.getString("Title"));
                    l.setVideo_url(rs.getString("Video_Url"));
                    l.setContent(rs.getString("Content"));
                    l.setCreated_at(rs.getDate("Created_At"));
                    l.setUpdated_at(rs.getDate("Updated_At"));
                    l.setCourse_id(rs.getLong("Course_Id"));
                    lessons.add(l);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error retrieving lessons for course ID: " + courseId, e);
        }
        return lessons;
    }

    public List<Quiz> getQuizzesByLessonId(long lessonId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT Quiz_Id, Question, Answer_Options, Correct_Answer, Created_At, Updated_At, Lesson_Id FROM Quiz WHERE Lesson_Id = ?";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Quiz q = new Quiz();
                    q.setQuiz_id(rs.getLong("Quiz_Id"));
                    q.setQuestion(rs.getString("Question"));
                    q.setAnswer_options(rs.getString("Answer_Options"));
                    q.setCorrect_answer(rs.getString("Correct_Answer"));
                    q.setCreated_at(rs.getDate("Created_At"));
                    q.setUpdated_at(rs.getDate("Updated_At"));
                    q.setLesson_id(rs.getLong("Lesson_Id"));
                    quizzes.add(q);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error retrieving quizzes for lesson ID: " + lessonId, e);
        }
        return quizzes;
    }

    public boolean insertTopic(Topic topic) {
        String sql = "INSERT INTO Topic (Name, Thumbnail_Url, Description) VALUES (?, ?, ?)";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setString(1, topic.getName());
            ps.setString(2, topic.getThumbnail_Url());
            ps.setString(3, topic.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error inserting topic", e);
        }
        return false;
    }

    public boolean updateTopic(Topic topic) {
        String sql = "UPDATE Topic SET Name = ?, Thumbnail_Url = ?, Description = ? WHERE Topic_Id = ?";
        try (PreparedStatement ps = dbContext.connection.prepareStatement(sql)) {
            ps.setString(1, topic.getName());
            ps.setString(2, topic.getThumbnail_Url());
            ps.setString(3, topic.getDescription());
            ps.setLong(4, topic.getTopic_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error updating topic with ID: " + topic.getTopic_id(), e);
        }
        return false;
    }

    public String deleteTopic(long topicId) {
        Connection conn = dbContext.connection;
        try {
            conn.setAutoCommit(false);

            List<Long> courseIds = new ArrayList<>();
            String sqlCourses = "SELECT Course_Id FROM Course WHERE Topic_Id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlCourses)) {
                ps.setLong(1, topicId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        courseIds.add(rs.getLong("Course_Id"));
                    }
                }
            }

            if (!courseIds.isEmpty()) {
                String courseIdsStr = String.join(",", courseIds.stream().map(String::valueOf).toArray(String[]::new));
                String sqlQuizzes = "DELETE FROM Quiz WHERE Lesson_Id IN (SELECT Lesson_Id FROM Lesson WHERE Course_Id IN (" + courseIdsStr + "))";
                try (PreparedStatement ps = conn.prepareStatement(sqlQuizzes)) {
                    ps.executeUpdate();
                }
                String sqlLessons = "DELETE FROM Lesson WHERE Course_Id IN (" + courseIdsStr + ")";
                try (PreparedStatement ps = conn.prepareStatement(sqlLessons)) {
                    ps.executeUpdate();
                }
                String sqlOrderDetails = "DELETE FROM Order_Detail WHERE Course_Id IN (" + courseIdsStr + ")";
                try (PreparedStatement ps = conn.prepareStatement(sqlOrderDetails)) {
                    ps.executeUpdate();
                }
                String sqlCart = "DELETE FROM Cart WHERE Course_Id IN (" + courseIdsStr + ")";
                try (PreparedStatement ps = conn.prepareStatement(sqlCart)) {
                    ps.executeUpdate();
                }
                String sqlReviews = "DELETE FROM Review WHERE Course_Id IN (" + courseIdsStr + ")";
                try (PreparedStatement ps = conn.prepareStatement(sqlReviews)) {
                    ps.executeUpdate();
                }
            }

            String sqlDeleteCourses = "DELETE FROM Course WHERE Topic_Id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteCourses)) {
                ps.setLong(1, topicId);
                ps.executeUpdate();
            }

            String sqlDeleteTopic = "DELETE FROM Topic WHERE Topic_Id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteTopic)) {
                ps.setLong(1, topicId);
                int rowsAffected = ps.executeUpdate();
                conn.commit();
                return rowsAffected > 0 ? "Topic deleted successfully" : "Topic not found";
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error rolling back transaction", ex);
            }
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error deleting topic with ID: " + topicId, e);
            return "Failed to delete topic: " + e.getMessage();
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, "Error resetting auto-commit", e);
            }
        }
    }
}