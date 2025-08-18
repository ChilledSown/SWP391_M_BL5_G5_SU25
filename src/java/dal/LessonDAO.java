/*
 * Click nbfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nb://SystemFileSystem/Templates/Classes/Class.java to edit this template
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
        String sql = "SELECT * FROM Lesson WHERE Course_Id = ? ORDER BY Created_At ASC";
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

    public Lesson getLessonByIdAlt(long lessonId) {
        Lesson lesson = null;
        String sql = "SELECT * FROM Lesson WHERE Lesson_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lesson = new Lesson(
                        rs.getLong("Lesson_Id"),
                        rs.getString("Title"),
                        rs.getString("Video_Url"),
                        rs.getString("Content"),
                        rs.getTimestamp("Created_At"),
                        rs.getTimestamp("Updated_At"),
                        rs.getLong("Course_Id")
                );
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

    public List<Lesson> getFilteredLessonsByCoursePaged(long courseId, String title, String createdDate, int offset, int limit) {
        List<Lesson> lessons = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM Lesson WHERE Course_Id = ? ");

        List<Object> params = new ArrayList<>();
        params.add(courseId);

        // Filter title (dạng LIKE nhiều từ)
        if (title != null && !title.trim().isEmpty()) {
            String[] words = title.trim().split("\\s+");
            for (String word : words) {
                sql.append("AND Title LIKE ? ");
                params.add("%" + word + "%");
            }
        }

        // Filter created date
        if (createdDate != null && !createdDate.trim().isEmpty()) {
            sql.append("AND CAST(Created_At AS DATE) = ? ");
            params.add(createdDate);
        }

        sql.append("ORDER BY Created_At DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object param : params) {
                stmt.setObject(index++, param);
            }
            stmt.setInt(index++, offset);
            stmt.setInt(index, limit);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Lesson lesson = new Lesson();
                lesson.setLessonId(rs.getLong("Lesson_Id"));
                lesson.setCourseId(rs.getLong("Course_Id"));
                lesson.setTitle(rs.getString("Title"));
                lesson.setContent(rs.getString("Content"));
                lesson.setVideoUrl(rs.getString("Video_Url"));
                lesson.setCreatedAt(rs.getTimestamp("Created_At"));
                lesson.setUpdatedAt(rs.getTimestamp("Updated_At"));
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public int countFilteredLessonsByCourse(long courseId, String title, String createdDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS Total FROM Lesson WHERE Course_Id = ? ");

        List<Object> params = new ArrayList<>();
        params.add(courseId);

        // Filter title
        if (title != null && !title.trim().isEmpty()) {
            String[] words = title.trim().split("\\s+");
            for (String word : words) {
                sql.append("AND Title LIKE ? ");
                params.add("%" + word + "%");
            }
        }

        // Filter created date
        if (createdDate != null && !createdDate.trim().isEmpty()) {
            sql.append("AND CAST(Created_At AS DATE) = ? ");
            params.add(createdDate);
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

    public List<Lesson> getLessonsByCourseIdWithSearch(String query, long courseId, int offset, int limit) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM Lesson WHERE Course_Id = ? AND (Title LIKE ? OR Content LIKE ?) ORDER BY Created_At DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String like = "%" + (query == null ? "" : query) + "%";
            ps.setLong(1, courseId);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setInt(4, offset);
            ps.setInt(5, limit);
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

    public int getTotalLessonsByCourseIdWithSearch(String query, long courseId) {
        String sql = "SELECT COUNT(*) FROM Lesson WHERE Course_Id = ? AND (Title LIKE ? OR Content LIKE ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String like = "%" + (query == null ? "" : query) + "%";
            ps.setLong(1, courseId);
            ps.setString(2, like);
            ps.setString(3, like);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}