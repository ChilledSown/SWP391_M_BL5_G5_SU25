package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Lesson;
import model.VideoQuiz;

public class InstructorVideoDAO extends DBContext {

    public List<VideoQuiz> getAllVideoQuizzes() {
        List<VideoQuiz> quizzes = new ArrayList<>();
        String sql = "SELECT vq.*, l.Title AS LessonTitle FROM VideoQuiz vq " +
                     "INNER JOIN Lesson l ON vq.Lesson_Id = l.Lesson_Id " +
                     "ORDER BY vq.Video_Quiz_Id";

        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                VideoQuiz quiz = mapResultSetToVideoQuiz(rs);
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all video quizzes: " + e.getMessage());
        }

        return quizzes;
    }

    public VideoQuiz getVideoQuizById(Long videoQuizId) {
        String sql = "SELECT vq.*, l.Title AS LessonTitle FROM VideoQuiz vq " +
                     "INNER JOIN Lesson l ON vq.Lesson_Id = l.Lesson_Id " +
                     "WHERE vq.Video_Quiz_Id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, videoQuizId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToVideoQuiz(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting video quiz by ID: " + e.getMessage());
        }

        return null;
    }

    public List<VideoQuiz> searchVideoQuizzes(String question, Long lessonId, int page, int pageSize) {
        List<VideoQuiz> quizzes = new ArrayList<>();
        String sql = "SELECT vq.*, l.Title AS LessonTitle FROM VideoQuiz vq " +
                     "INNER JOIN Lesson l ON vq.Lesson_Id = l.Lesson_Id " +
                     "WHERE (? IS NULL OR vq.Question LIKE ?) " +
                     "AND (? IS NULL OR vq.Lesson_Id = ?) " +
                     "ORDER BY vq.Video_Quiz_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, question);
            ps.setString(2, "%" + (question == null ? "" : question) + "%");
            if (lessonId == null) {
                ps.setObject(3, null);
                ps.setObject(4, null);
            } else {
                ps.setLong(3, lessonId);
                ps.setLong(4, lessonId);
            }
            ps.setInt(5, (page - 1) * pageSize);
            ps.setInt(6, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    quizzes.add(mapResultSetToVideoQuiz(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching video quizzes: " + e.getMessage());
        }
        return quizzes;
    }

    public int getTotalVideoQuizzes(String question, Long lessonId) {
        String sql = "SELECT COUNT(*) FROM VideoQuiz vq " +
                     "WHERE (? IS NULL OR vq.Question LIKE ?) " +
                     "AND (? IS NULL OR vq.Lesson_Id = ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, question);
            ps.setString(2, "%" + (question == null ? "" : question) + "%");
            if (lessonId == null) {
                ps.setObject(3, null);
                ps.setObject(4, null);
            } else {
                ps.setLong(3, lessonId);
                ps.setLong(4, lessonId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting total video quizzes: " + e.getMessage());
        }
        return 0;
    }

    public List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT Lesson_Id, Title, Video_Url FROM Lesson ORDER BY Title";

        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Lesson lesson = new Lesson();
                lesson.setLessonId(rs.getLong("Lesson_Id"));
                lesson.setTitle(rs.getString("Title"));
                lesson.setVideoUrl(rs.getString("Video_Url"));
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all lessons: " + e.getMessage());
        }

        return lessons;
    }

    public Lesson getLessonById(Long lessonId) {
        String sql = "SELECT Lesson_Id, Title, Video_Url, Content, Created_At, Updated_At, Course_Id FROM Lesson WHERE Lesson_Id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, lessonId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setLessonId(rs.getLong("Lesson_Id"));
                    lesson.setTitle(rs.getString("Title"));
                    lesson.setVideoUrl(rs.getString("Video_Url"));
                    lesson.setContent(rs.getString("Content"));
                    lesson.setCreatedAt(rs.getDate("Created_At"));
                    lesson.setUpdatedAt(rs.getDate("Updated_At"));
                    lesson.setCourseId(rs.getLong("Course_Id"));
                    return lesson;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting lesson by ID: " + e.getMessage());
        }
        return null;
    }

    public boolean insertVideoQuiz(VideoQuiz quiz) {
        if (!isValidVideoQuiz(quiz)) {
            System.out.println("Invalid video quiz data: " + quiz);
            return false;
        }

        String sql = "INSERT INTO VideoQuiz (Lesson_Id, Timestamp, Question, Answer_Options, Correct_Answer, Explanation, Is_Active, Created_At, Updated_At) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, quiz.getLessonId());
            ps.setInt(2, quiz.getTimestamp());
            ps.setString(3, quiz.getQuestion());
            ps.setString(4, quiz.getAnswerOptions());
            ps.setString(5, quiz.getCorrectAnswer());
            ps.setString(6, quiz.getExplanation() != null ? quiz.getExplanation() : "");
            ps.setBoolean(7, quiz.getIsActive());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting video quiz: " + e.getMessage());
            return false;
        }
    }

    public boolean updateVideoQuiz(VideoQuiz quiz) {
        if (!isValidVideoQuiz(quiz)) {
            System.out.println("Invalid video quiz data: " + quiz);
            return false;
        }

        String sql = "UPDATE VideoQuiz SET Lesson_Id = ?, Timestamp = ?, Question = ?, Answer_Options = ?, Correct_Answer = ?, Explanation = ?, Is_Active = ?, Updated_At = GETDATE() " +
                     "WHERE Video_Quiz_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, quiz.getLessonId());
            ps.setInt(2, quiz.getTimestamp());
            ps.setString(3, quiz.getQuestion());
            ps.setString(4, quiz.getAnswerOptions());
            ps.setString(5, quiz.getCorrectAnswer());
            ps.setString(6, quiz.getExplanation() != null ? quiz.getExplanation() : "");
            ps.setBoolean(7, quiz.getIsActive());
            ps.setLong(8, quiz.getVideoQuizId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating video quiz: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteVideoQuiz(long videoQuizId) {
        String sql = "DELETE FROM VideoQuiz WHERE Video_Quiz_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, videoQuizId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting video quiz ID " + videoQuizId + ": " + e.getMessage());
            return false;
        }
    }

    public boolean checkDuplicateLessonAndTimestamp(Long lessonId, Integer timestamp, Long excludeVideoQuizId) {
        String sql = "SELECT COUNT(*) FROM VideoQuiz WHERE Lesson_Id = ? AND Timestamp = ? AND (? IS NULL OR Video_Quiz_Id != ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            ps.setInt(2, timestamp);
            if (excludeVideoQuizId == null) {
                ps.setNull(3, java.sql.Types.BIGINT);
                ps.setNull(4, java.sql.Types.BIGINT);
            } else {
                ps.setLong(3, excludeVideoQuizId);
                ps.setLong(4, excludeVideoQuizId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking duplicate lesson and timestamp: " + e.getMessage());
        }
        return false;
    }

    private VideoQuiz mapResultSetToVideoQuiz(ResultSet rs) throws SQLException {
        VideoQuiz quiz = new VideoQuiz();
        quiz.setVideoQuizId(rs.getLong("Video_Quiz_Id"));
        quiz.setLessonId(rs.getLong("Lesson_Id"));
        quiz.setTimestamp(rs.getInt("Timestamp"));
        quiz.setQuestion(rs.getString("Question"));
        quiz.setAnswerOptions(rs.getString("Answer_Options"));
        quiz.setCorrectAnswer(rs.getString("Correct_Answer"));
        quiz.setExplanation(rs.getString("Explanation"));
        quiz.setIsActive(rs.getBoolean("Is_Active"));
        quiz.setCreatedAt(rs.getDate("Created_At"));
        quiz.setUpdatedAt(rs.getDate("Updated_At"));
        quiz.setLessonTitle(rs.getString("LessonTitle"));
        return quiz;
    }

    private boolean isValidVideoQuiz(VideoQuiz quiz) {
        if (quiz.getAnswerOptions() == null || quiz.getAnswerOptions().trim().isEmpty()) {
            System.out.println("Validation failed: Answer options are null or empty.");
            return false;
        }
        if (quiz.getCorrectAnswer() == null || quiz.getCorrectAnswer().trim().isEmpty()) {
            System.out.println("Validation failed: Correct answer is null or empty.");
            return false;
        }
        String normalizedAnswerOptions = quiz.getAnswerOptions().trim().replaceAll("\\s*;\\s*", "; ");
        List<String> options = Arrays.asList(normalizedAnswerOptions.split("; "));
        if (options.size() < 2) {
            System.out.println("Validation failed: At least two answer options are required.");
            return false;
        }
        if (options.stream().noneMatch(opt -> opt.startsWith("A. ") && !opt.substring(2).trim().isEmpty()) ||
            options.stream().noneMatch(opt -> opt.startsWith("B. ") && !opt.substring(2).trim().isEmpty())) {
            System.out.println("Validation failed: Options A and B must be non-empty.");
            return false;
        }
        String normalizedCorrectAnswer = quiz.getCorrectAnswer().trim().replaceAll("\\s*;\\s*", "; ");
        List<String> correctList = Arrays.asList(normalizedCorrectAnswer.split("; "));
        if (correctList.isEmpty()) {
            System.out.println("Validation failed: At least one correct answer is required.");
            return false;
        }
        boolean allValid = true;
        for (String corr : correctList) {
            if (!options.contains(corr)) {
                allValid = false;
                break;
            }
        }
        if (!allValid) {
            System.out.println("Validation failed: Some correct answers do not match options.");
            return false;
        }
        return true;
    }
}