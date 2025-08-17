/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Quiz;

public class QuizDAO extends DBContext {
    public List<Quiz> getQuizzesByLessonId(long lessonId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM Quiz WHERE Lesson_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            ResultSet rs = ps.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }
}
