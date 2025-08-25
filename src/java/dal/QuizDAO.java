package dal;

import java.util.List;
import model.Quiz;
import java.sql.*;
import java.util.ArrayList;

public class QuizDAO extends DBContext {

    public List<Quiz> getQuizzesByLessonId(long lessonId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT q.*, l.Title AS LessonTitle "
                + "FROM Quiz q "
                + "JOIN Lesson l ON q.Lesson_Id = l.Lesson_Id "
                + "WHERE q.Lesson_Id = ? "
                + "ORDER BY q.Created_At DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, lessonId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getLong("Quiz_Id"));
                quiz.setQuestion(rs.getString("Question"));
                quiz.setAnswerOptions(rs.getString("Answer_Options"));
                quiz.setCorrectAnswer(rs.getString("Correct_Answer"));
                quiz.setExplanation(rs.getString("Explanation"));
                quiz.setCreatedAt(rs.getTimestamp("Created_At"));
                quiz.setUpdatedAt(rs.getTimestamp("Updated_At"));
                quiz.setLessonId(rs.getLong("Lesson_Id"));
                quiz.setLessonTitle(rs.getString("LessonTitle"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public List<Quiz> getFilteredQuizzesByLessonPaged(long lessonId, String question, String startDate, String endDate, int offset, int limit) {
        List<Quiz> quizzes = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT q.*, l.Title AS LessonTitle ");
        sql.append("FROM Quiz q ");
        sql.append("JOIN Lesson l ON q.Lesson_Id = l.Lesson_Id ");
        sql.append("WHERE q.Lesson_Id = ? ");
        List<Object> params = new ArrayList<>();
        params.add(lessonId);
        if (question != null && !question.trim().isEmpty()) {
            String[] words = question.trim().split("\\s+");
            for (String word : words) {
                sql.append("AND q.Question LIKE ? ");
                params.add("%" + word + "%");
            }
        }
        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append("AND CAST(q.Created_At AS DATE) >= ? ");
            params.add(startDate);
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append("AND CAST(q.Created_At AS DATE) <= ? ");
            params.add(endDate);
        }
        sql.append("ORDER BY q.Created_At DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object param : params) {
                stmt.setObject(index++, param);
            }
            stmt.setInt(index++, offset);
            stmt.setInt(index, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getLong("Quiz_Id"));
                quiz.setQuestion(rs.getString("Question"));
                quiz.setAnswerOptions(rs.getString("Answer_Options"));
                quiz.setCorrectAnswer(rs.getString("Correct_Answer"));
                quiz.setExplanation(rs.getString("Explanation"));
                quiz.setCreatedAt(rs.getTimestamp("Created_At"));
                quiz.setUpdatedAt(rs.getTimestamp("Updated_At"));
                quiz.setLessonId(rs.getLong("Lesson_Id"));
                quiz.setLessonTitle(rs.getString("LessonTitle"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public int countFilteredQuizzesByLesson(long lessonId, String question, String startDate, String endDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS Total FROM Quiz q WHERE q.Lesson_Id = ? ");
        List<Object> params = new ArrayList<>();
        params.add(lessonId);
        if (question != null && !question.trim().isEmpty()) {
            String[] words = question.trim().split("\\s+");
            for (String word : words) {
                sql.append("AND q.Question LIKE ? ");
                params.add("%" + word + "%");
            }
        }
        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append("AND CAST(q.Created_At AS DATE) >= ? ");
            params.add(startDate);
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append("AND CAST(q.Created_At AS DATE) <= ? ");
            params.add(endDate);
        }
        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object param : params) {
                stmt.setObject(index++, param);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void createQuiz(Quiz quiz) {
        String sql = "INSERT INTO Quiz (Question, Answer_Options, Correct_Answer, Explanation, Created_At, Updated_At, Lesson_Id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, quiz.getQuestion());
            stmt.setString(2, quiz.getAnswerOptions());
            stmt.setString(3, quiz.getCorrectAnswer());
            stmt.setString(4, quiz.getExplanation());
            stmt.setTimestamp(5, new java.sql.Timestamp(quiz.getCreatedAt().getTime()));
            stmt.setTimestamp(6, new java.sql.Timestamp(quiz.getUpdatedAt().getTime()));
            stmt.setLong(7, quiz.getLessonId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuiz(Quiz quiz) throws Exception {
        String sql = "UPDATE Quiz SET Question = ?, Answer_Options = ?, Correct_Answer = ?, Explanation = ?, Updated_At = ? WHERE Quiz_Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, quiz.getQuestion());
            stmt.setString(2, quiz.getAnswerOptions());
            stmt.setString(3, quiz.getCorrectAnswer());
            stmt.setString(4, quiz.getExplanation());
            stmt.setTimestamp(5, quiz.getUpdatedAt() != null ? new Timestamp(quiz.getUpdatedAt().getTime()) : new Timestamp(System.currentTimeMillis()));
            stmt.setLong(6, quiz.getQuizId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Quiz getQuizById(long quizId) {
        String sql = "SELECT * FROM Quiz WHERE Quiz_Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, quizId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getLong("Quiz_Id"));
                quiz.setQuestion(rs.getString("Question"));
                quiz.setAnswerOptions(rs.getString("Answer_Options"));
                quiz.setCorrectAnswer(rs.getString("Correct_Answer"));
                quiz.setExplanation(rs.getString("Explanation"));
                quiz.setCreatedAt(rs.getTimestamp("Created_At"));
                quiz.setUpdatedAt(rs.getTimestamp("Updated_At"));
                quiz.setLessonId(rs.getLong("Lesson_Id"));
                return quiz;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteQuiz(long quizId) {
        String sql = "DELETE FROM Quiz WHERE Quiz_Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, quizId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuizzesByLessonId(long lessonId) {
        String sql = "DELETE FROM Quiz WHERE Lesson_Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, lessonId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isQuestionDuplicate(String question, Long lessonId) {
        String sql = "SELECT COUNT(*) FROM Quiz WHERE LOWER(Question) = LOWER(?) AND Lesson_Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, question.trim());
            stmt.setLong(2, lessonId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isQuestionDuplicateForUpdate(String question, Long lessonId, Long quizId) {
        String sql = "SELECT COUNT(*) FROM Quiz WHERE LOWER(Question) = LOWER(?) AND Lesson_Id = ? AND Quiz_Id != ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, question.trim());
            stmt.setLong(2, lessonId);
            stmt.setLong(3, quizId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasAssociatedQuizzes(long lessonId) {
        String sql = "SELECT COUNT(*) FROM Quiz WHERE Lesson_Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, lessonId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Quiz> getQuizzesByCourseId(long courseId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT q.*, l.Title AS LessonTitle, c.Title AS CourseTitle "
                + "FROM Quiz q "
                + "JOIN Lesson l ON q.Lesson_Id = l.Lesson_Id "
                + "JOIN Course c ON l.Course_Id = c.Course_Id "
                + "WHERE l.Course_Id = ? "
                + "ORDER BY l.Lesson_Id, q.Quiz_Id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getLong("Quiz_Id"));
                quiz.setQuestion(rs.getString("Question"));
                quiz.setAnswerOptions(rs.getString("Answer_Options"));
                quiz.setCorrectAnswer(rs.getString("Correct_Answer"));
                quiz.setCreatedAt(rs.getTimestamp("Created_At"));
                quiz.setUpdatedAt(rs.getTimestamp("Updated_At"));
                quiz.setLessonId(rs.getLong("Lesson_Id"));
                quiz.setExplanation(rs.getString("Explanation"));
                quiz.setLessonTitle(rs.getString("LessonTitle"));
                quiz.setCourseTitle(rs.getString("CourseTitle"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public int countQuizzesByCourseId(long courseId) {
        String sql = "SELECT COUNT(*) FROM Quiz q "
                + "JOIN Lesson l ON q.Lesson_Id = l.Lesson_Id "
                + "WHERE l.Course_Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args) {
        QuizDAO q = new QuizDAO();
        List<Quiz> lq = q.getQuizzesByCourseId(1);
        System.out.println(lq);
    }
}