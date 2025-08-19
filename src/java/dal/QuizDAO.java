/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.List;
import model.Quiz;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author sondo
 */
public class QuizDAO extends DBContext {

    public List<Quiz> getQuizzesByLessonId(long lessonId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT q.*, l.Title AS LessonTitle "
                + "FROM Quiz q "
                + "JOIN Lesson l ON q.Lesson_Id = l.Lesson_Id "
                + "WHERE q.Lesson_Id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, lessonId);
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
                quiz.setLessonTitle(rs.getString("LessonTitle"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
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

    public void updateQuiz(Quiz quiz) {
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

}
