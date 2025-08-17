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

    public static void main(String[] args) {
        QuizDAO quizDAO = new QuizDAO();

        // Ví dụ: lấy quiz theo Lesson_Id = 1
        long lessonId = 1;

        List<Quiz> quizzes = quizDAO.getQuizzesByLessonId(lessonId);

        // In ra kết quả
        if (quizzes.isEmpty()) {
            System.out.println("Không có quiz nào cho lessonId = " + lessonId);
        } else {
            for (Quiz quiz : quizzes) {
                System.out.println("Quiz_Id: " + quiz.getQuizId());
                System.out.println("Question: " + quiz.getQuestion());
                System.out.println("Answer Options: " + quiz.getAnswerOptions());
                System.out.println("Correct Answer: " + quiz.getCorrectAnswer());
                System.out.println("Created At: " + quiz.getCreatedAt());
                System.out.println("Updated At: " + quiz.getUpdatedAt());
                System.out.println("Lesson_Id: " + quiz.getLessonId());
                System.out.println("-------------------------------");
            }
        }
    }
}