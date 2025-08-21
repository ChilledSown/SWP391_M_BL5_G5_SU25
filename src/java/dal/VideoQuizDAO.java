package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.VideoQuiz;

/**
 * Data Access Object for VideoQuiz-related database operations.
 */
public class VideoQuizDAO extends DBContext {
    
    /**
     * Get all video quizzes for a specific lesson
     */
    public List<VideoQuiz> getVideoQuizzesByLessonId(Long lessonId) {
        List<VideoQuiz> videoQuizzes = new ArrayList<>();
        String sql = "SELECT vq.*, l.Title as LessonTitle " +
                    "FROM VideoQuiz vq " +
                    "INNER JOIN Lesson l ON vq.Lesson_Id = l.Lesson_Id " +
                    "WHERE vq.Lesson_Id = ? AND vq.Is_Active = 1 " +
                    "ORDER BY vq.Timestamp ASC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, lessonId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                VideoQuiz videoQuiz = new VideoQuiz();
                videoQuiz.setVideoQuizId(rs.getLong("Video_Quiz_Id"));
                videoQuiz.setLessonId(rs.getLong("Lesson_Id"));
                videoQuiz.setTimestamp(rs.getInt("Timestamp"));
                videoQuiz.setQuestion(rs.getString("Question"));
                videoQuiz.setAnswerOptions(rs.getString("Answer_Options"));
                videoQuiz.setCorrectAnswer(rs.getString("Correct_Answer"));
                videoQuiz.setExplanation(rs.getString("Explanation"));
                videoQuiz.setIsActive(rs.getBoolean("Is_Active"));
                videoQuiz.setCreatedAt(rs.getTimestamp("Created_At"));
                videoQuiz.setUpdatedAt(rs.getTimestamp("Updated_At"));
                
                // Joined data
                videoQuiz.setLessonTitle(rs.getString("LessonTitle"));
                
                videoQuizzes.add(videoQuiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return videoQuizzes;
    }
    
    /**
     * Get a specific video quiz by ID
     */
    public VideoQuiz getVideoQuizById(Long videoQuizId) {
        String sql = "SELECT vq.*, l.Title as LessonTitle " +
                    "FROM VideoQuiz vq " +
                    "INNER JOIN Lesson l ON vq.Lesson_Id = l.Lesson_Id " +
                    "WHERE vq.Video_Quiz_Id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, videoQuizId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                VideoQuiz videoQuiz = new VideoQuiz();
                videoQuiz.setVideoQuizId(rs.getLong("Video_Quiz_Id"));
                videoQuiz.setLessonId(rs.getLong("Lesson_Id"));
                videoQuiz.setTimestamp(rs.getInt("Timestamp"));
                videoQuiz.setQuestion(rs.getString("Question"));
                videoQuiz.setAnswerOptions(rs.getString("Answer_Options"));
                videoQuiz.setCorrectAnswer(rs.getString("Correct_Answer"));
                videoQuiz.setExplanation(rs.getString("Explanation"));
                videoQuiz.setIsActive(rs.getBoolean("Is_Active"));
                videoQuiz.setCreatedAt(rs.getTimestamp("Created_At"));
                videoQuiz.setUpdatedAt(rs.getTimestamp("Updated_At"));
                
                // Joined data
                videoQuiz.setLessonTitle(rs.getString("LessonTitle"));
                
                return videoQuiz;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
