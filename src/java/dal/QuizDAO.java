package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Quiz;

public class QuizDAO extends DBContext {

    // Get all quizzes by lesson ID (non-paginated, for ManageTopicServlet)
    public List<Quiz> getQuizzesByLessonId(long lessonId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM Quiz WHERE Lesson_Id = ? ORDER BY Quiz_Id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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
            System.out.println("Error getting quizzes by lesson ID: " + e.getMessage());
            e.printStackTrace();
        }
        return quizzes;
    }

    // Get quizzes by lesson ID with pagination (for ManageQuizServlet)
    public List<Quiz> getQuizzesByLessonId(long lessonId, int page, int pageSize) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM Quiz WHERE Lesson_Id = ? ORDER BY Quiz_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            ps.setInt(2, (page - 1) * pageSize);
            ps.setInt(3, pageSize);
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
            System.out.println("Error getting paginated quizzes by lesson ID: " + e.getMessage());
            e.printStackTrace();
        }
        return quizzes;
    }

    // Get total number of quizzes for a lesson
    public int getTotalQuizzesByLessonId(long lessonId) {
        String sql = "SELECT COUNT(*) FROM Quiz WHERE Lesson_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error counting quizzes by lesson ID: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // Get quiz by ID
    public Quiz getQuizById(long quizId) {
        String sql = "SELECT * FROM Quiz WHERE Quiz_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, quizId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Quiz q = new Quiz();
                    q.setQuiz_id(rs.getLong("Quiz_Id"));
                    q.setQuestion(rs.getString("Question"));
                    q.setAnswer_options(rs.getString("Answer_Options"));
                    q.setCorrect_answer(rs.getString("Correct_Answer"));
                    q.setLesson_id(rs.getLong("Lesson_Id"));
                    return q;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting quiz by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Check for duplicate question
    public Quiz getQuizByQuestion(String question) {
        String sql = "SELECT * FROM Quiz WHERE Question = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, question);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Quiz q = new Quiz();
                    q.setQuiz_id(rs.getLong("Quiz_Id"));
                    q.setQuestion(rs.getString("Question"));
                    q.setAnswer_options(rs.getString("Answer_Options"));
                    q.setCorrect_answer(rs.getString("Correct_Answer"));
                    q.setLesson_id(rs.getLong("Lesson_Id"));
                    return q;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking quiz by question: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Update quiz
    public boolean updateQuiz(Quiz quiz) {
        String sql = "UPDATE Quiz SET Question=?, Answer_Options=?, Correct_Answer=?, Updated_At=GETDATE() WHERE Quiz_Id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, quiz.getQuestion());
            ps.setString(2, quiz.getAnswer_options());
            ps.setString(3, quiz.getCorrect_answer());
            ps.setLong(4, quiz.getQuiz_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating quiz: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Get quizzes with pagination and search by lesson ID
    public List<Quiz> getQuizzesWithPagination(String query, long lessonId, int page, int pageSize) {
        List<Quiz> list = new ArrayList<>();
        String sql = "SELECT * FROM Quiz WHERE Lesson_Id = ? AND Question LIKE ? ORDER BY Quiz_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            ps.setString(2, "%" + (query == null ? "" : query) + "%");
            ps.setInt(3, (page - 1) * pageSize);
            ps.setInt(4, pageSize);
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
                    list.add(q);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting quizzes with pagination and search: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // Get total number of quizzes for a lesson with search
    public int getTotalQuizzes(String query, long lessonId) {
        String sql = "SELECT COUNT(*) FROM Quiz WHERE Lesson_Id = ? AND Question LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            ps.setString(2, "%" + (query == null ? "" : query) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error counting total quizzes with search: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}