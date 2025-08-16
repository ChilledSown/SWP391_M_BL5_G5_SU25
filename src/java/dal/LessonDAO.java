/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.Lesson;
import java.sql.*;

/**
 *
 * @author sondo
 */
public class LessonDAO extends DBContext {

    public List<Lesson> getLessonsByCourseId(long courseId) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM Lesson WHERE Course_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lesson lesson = new Lesson(
                        rs.getLong("Lesson_Id"),
                        rs.getString("Title"),
                        rs.getString("Video_Url"),
                        rs.getString("Content"),
                        rs.getTimestamp("Created_At"),
                        rs.getTimestamp("Updated_At"),
                        rs.getLong("Course_Id")
                );
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public Lesson getLessonById(long lessonId) {
        Lesson lesson = null;
        String sql = "SELECT * FROM Lesson WHERE Lesson_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lesson = new Lesson();
                lesson.setLessonId(rs.getLong("Lesson_Id"));
                lesson.setTitle(rs.getString("Title"));
                lesson.setVideoUrl(rs.getString("Video_Url"));
                lesson.setContent(rs.getString("Content"));
                lesson.setCreatedAt(rs.getTimestamp("Created_At"));
                lesson.setUpdatedAt(rs.getTimestamp("Updated_At"));
                lesson.setCourseId(rs.getLong("Course_Id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesson;
    }

    public void insertLesson(Lesson lesson) {
        String sql = "INSERT INTO Lesson (Title, Video_Url, Content, Created_At, Course_Id) "
                + "VALUES (?, ?, ?, GETDATE(), ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lesson.getTitle());
            ps.setString(2, lesson.getVideoUrl());
            ps.setString(3, lesson.getContent());
            ps.setLong(4, lesson.getCourseId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLesson(Lesson lesson) {
        String sql = "UPDATE Lesson SET Title = ?, Video_Url = ?, Content = ?, Updated_At = GETDATE() WHERE Lesson_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lesson.getTitle());
            ps.setString(2, lesson.getVideoUrl());
            ps.setString(3, lesson.getContent());
            ps.setLong(4, lesson.getLessonId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLesson(long lessonId) {
        String sql = "DELETE FROM Lesson WHERE Lesson_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Lesson> getLessonsByCourse(long courseId) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM Lesson WHERE Course_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lesson lesson = new Lesson();
                lesson.setLessonId(rs.getLong("Lesson_Id"));
                lesson.setTitle(rs.getString("Title"));
                lesson.setVideoUrl(rs.getString("Video_Url"));
                lesson.setContent(rs.getString("Content"));
                lesson.setCreatedAt(rs.getTimestamp("Created_At"));
                lesson.setUpdatedAt(rs.getTimestamp("Updated_At"));
                lesson.setCourseId(rs.getLong("Course_Id"));
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

 
    public static void main(String[] args) {
        LessonDAO lessonDAO = new LessonDAO();
        long testCourseId = 1;
        List<Lesson> lessons = lessonDAO.getLessonsByCourseId(testCourseId);
        for (Lesson lesson : lessons) {
            System.out.println("Lesson ID: " + lesson.getLessonId());
            System.out.println("Title: " + lesson.getTitle());
            System.out.println("Video URL: " + lesson.getVideoUrl());
            System.out.println("Content: " + lesson.getContent());
            System.out.println("Created At: " + lesson.getCreatedAt());
            System.out.println("Updated At: " + lesson.getUpdatedAt());
            System.out.println("Course Id: " + lesson.getCourseId());
            System.out.println("-----------------------------");
        }
    }
}
