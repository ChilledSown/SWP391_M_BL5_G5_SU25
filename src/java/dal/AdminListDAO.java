package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Topic;
import model.Course;
import model.Lesson;
import model.Quiz;

public class AdminListDAO extends DBContext {

    // Topic Methods 
    public List<Topic> getAllTopics() {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT * FROM Topic ORDER BY Topic_Id";
        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setTopic_id(rs.getLong("Topic_Id"));
                topic.setName(rs.getString("Name"));
                topic.setDescription(rs.getString("Description"));
                topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all topics: " + e.getMessage());
        }
        return topics;
    }

    public Topic getTopicById(Long topicId) {
        String sql = "SELECT * FROM Topic WHERE Topic_Id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, topicId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopic_id(rs.getLong("Topic_Id"));
                    topic.setName(rs.getString("Name"));
                    topic.setDescription(rs.getString("Description"));
                    topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                    return topic;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting topic by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Topic> getTopTopics() {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT TOP 8 * FROM Topic ORDER BY Topic_Id";
        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setTopic_id(rs.getLong("Topic_Id"));
                topic.setName(rs.getString("Name"));
                topic.setDescription(rs.getString("Description"));
                topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            System.out.println("Error getting top topics: " + e.getMessage());
        }
        return topics;
    }

    public Topic getTopicByCourseId(Long courseId) {
        String sql = "SELECT t.* FROM Topic t INNER JOIN Course c ON t.Topic_Id = c.Topic_Id WHERE c.Course_Id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, courseId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopic_id(rs.getLong("Topic_Id"));
                    topic.setName(rs.getString("Name"));
                    topic.setDescription(rs.getString("Description"));
                    topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                    return topic;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting topic by course ID: " + e.getMessage());
        }
        return null;
    }

    public Topic getTopicByName(String name) {
        String sql = "SELECT * FROM Topic WHERE Name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopic_id(rs.getLong("Topic_Id"));
                    topic.setName(rs.getString("Name"));
                    topic.setDescription(rs.getString("Description"));
                    topic.setThumbnail_url(rs.getString("Thumbnail_Url"));
                    return topic;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Topic> getTopicsWithPagination(String query, int page, int pageSize) {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT * FROM Topic WHERE Name LIKE ? ORDER BY Topic_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + (query == null ? "" : query) + "%");
            ps.setInt(2, (page - 1) * pageSize);
            ps.setInt(3, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Topic t = new Topic();
                    t.setTopic_id(rs.getLong("Topic_Id"));
                    t.setName(rs.getString("Name"));
                    t.setThumbnail_url(rs.getString("Thumbnail_Url"));
                    t.setDescription(rs.getString("Description"));
                    topics.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topics;
    }

    public int getTotalTopics(String query) {
        String sql = "SELECT COUNT(*) FROM Topic WHERE Name LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + (query == null ? "" : query) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Course Methods
    public List<Course> getCoursesByTopicId(Long topicId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, COALESCE(AVG(r.Rating), 0) as AverageRating " +
                     "FROM Course c LEFT JOIN Review r ON c.Course_Id = r.Course_Id " +
                     "WHERE c.Topic_Id = ? GROUP BY c.Course_Id, c.Title, c.Description, " +
                     "c.Price, c.Thumbnail_Url, c.Created_At, c.Updated_At, c.Topic_Id, c.Status, c.Created_By";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, topicId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourse_id(rs.getLong("Course_Id"));
                course.setTitle(rs.getString("Title"));
                course.setDescription(rs.getString("Description"));
                course.setPrice(rs.getInt("Price"));
                course.setThumbnail_url(rs.getString("Thumbnail_Url"));
                course.setCreated_at(rs.getDate("Created_At"));
                course.setUpdated_at(rs.getDate("Updated_At"));
                course.setTopic_id(rs.getLong("Topic_Id"));
                course.setAverageRating(rs.getDouble("AverageRating"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public Course getCourseById(Long courseId) {
        String sql = "SELECT c.*, COALESCE(AVG(r.Rating), 0) as AverageRating " +
                     "FROM Course c LEFT JOIN Review r ON c.Course_Id = r.Course_Id " +
                     "WHERE c.Course_Id = ? GROUP BY c.Course_Id, c.Title, c.Description, " +
                     "c.Price, c.Thumbnail_Url, c.Created_At, c.Updated_At, c.Topic_Id, c.Status, c.Created_By";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Course course = new Course();
                    course.setCourse_id(rs.getLong("Course_Id"));
                    course.setTitle(rs.getString("Title"));
                    course.setDescription(rs.getString("Description"));
                    course.setPrice(rs.getInt("Price"));
                    course.setThumbnail_url(rs.getString("Thumbnail_Url"));
                    course.setCreated_at(rs.getDate("Created_At"));
                    course.setUpdated_at(rs.getDate("Updated_At"));
                    course.setTopic_id(rs.getLong("Topic_Id"));
                    course.setAverageRating(rs.getDouble("AverageRating"));
                    return course;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Course> getCoursesWithPagination(String query, int page, int pageSize, Long topicId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, COALESCE(AVG(r.Rating), 0) as AverageRating " +
                     "FROM Course c LEFT JOIN Review r ON c.Course_Id = r.Course_Id " +
                     "WHERE c.Topic_Id = ? AND c.Title LIKE ? " +
                     "GROUP BY c.Course_Id, c.Title, c.Description, c.Price, c.Thumbnail_Url, " +
                     "c.Created_At, c.Updated_At, c.Topic_Id, c.Status, c.Created_By " +
                     "ORDER BY c.Course_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, topicId);
            ps.setString(2, "%" + (query == null ? "" : query) + "%");
            ps.setInt(3, (page - 1) * pageSize);
            ps.setInt(4, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course();
                    course.setCourse_id(rs.getLong("Course_Id"));
                    course.setTitle(rs.getString("Title"));
                    course.setDescription(rs.getString("Description"));
                    course.setPrice(rs.getInt("Price"));
                    course.setThumbnail_url(rs.getString("Thumbnail_Url"));
                    course.setCreated_at(rs.getDate("Created_At"));
                    course.setUpdated_at(rs.getDate("Updated_At"));
                    course.setTopic_id(rs.getLong("Topic_Id"));
                    course.setAverageRating(rs.getDouble("AverageRating"));
                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public int getTotalCourses(String query, Long topicId) {
        String sql = "SELECT COUNT(*) FROM Course WHERE Topic_Id = ? AND Title LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, topicId);
            ps.setString(2, "%" + (query == null ? "" : query) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lesson Methods 
    public List<Lesson> getLessonsByCourseId(Long courseId) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM Lesson WHERE Course_Id = ? ORDER BY Lesson_Id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lesson lesson = new Lesson();
                lesson.setLessonId(rs.getLong("Lesson_Id"));
                lesson.setTitle(rs.getString("Title"));
                lesson.setVideoUrl(rs.getString("Video_Url"));
                lesson.setContent(rs.getString("Content"));
                lesson.setCreatedAt(rs.getDate("Created_At"));
                lesson.setUpdatedAt(rs.getDate("Updated_At"));
                lesson.setCourseId(rs.getLong("Course_Id"));
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public Lesson getLessonById(Long lessonId) {
        String sql = "SELECT * FROM Lesson WHERE Lesson_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            try (ResultSet rs = ps.executeQuery()) {
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
            e.printStackTrace();
        }
        return null;
    }

    public List<Lesson> getLessonsWithPagination(String query, int page, int pageSize, Long courseId) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM Lesson WHERE Course_Id = ? AND Title LIKE ? " +
                     "ORDER BY Lesson_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            ps.setString(2, "%" + (query == null ? "" : query) + "%");
            ps.setInt(3, (page - 1) * pageSize);
            ps.setInt(4, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setLessonId(rs.getLong("Lesson_Id"));
                    lesson.setTitle(rs.getString("Title"));
                    lesson.setVideoUrl(rs.getString("Video_Url"));
                    lesson.setContent(rs.getString("Content"));
                    lesson.setCreatedAt(rs.getDate("Created_At"));
                    lesson.setUpdatedAt(rs.getDate("Updated_At"));
                    lesson.setCourseId(rs.getLong("Course_Id"));
                    lessons.add(lesson);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public int getTotalLessons(String query, Long courseId) {
        String sql = "SELECT COUNT(*) FROM Lesson WHERE Course_Id = ? AND Title LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            ps.setString(2, "%" + (query == null ? "" : query) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Quiz Methods
    public List<Quiz> getQuizzesByLessonId(Long lessonId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT q.*, l.Title AS LessonTitle " +
                     "FROM Quiz q INNER JOIN Lesson l ON q.Lesson_Id = l.Lesson_Id " +
                     "WHERE q.Lesson_Id = ? ORDER BY q.Quiz_Id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getLong("Quiz_Id"));
                quiz.setQuestion(rs.getString("Question"));
                quiz.setAnswerOptions(rs.getString("Answer_Options"));
                quiz.setCorrectAnswer(rs.getString("Correct_Answer"));
                quiz.setCreatedAt(rs.getDate("Created_At"));
                quiz.setUpdatedAt(rs.getDate("Updated_At"));
                quiz.setLessonId(rs.getLong("Lesson_Id"));
                quiz.setLessonTitle(rs.getString("LessonTitle"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            System.out.println("Error getting quizzes by lesson ID: " + e.getMessage());
            e.printStackTrace();
        }
        return quizzes;
    }

    public Quiz getQuizById(Long quizId) {
        String sql = "SELECT q.*, l.Title AS LessonTitle " +
                     "FROM Quiz q INNER JOIN Lesson l ON q.Lesson_Id = l.Lesson_Id " +
                     "WHERE q.Quiz_Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, quizId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setQuizId(rs.getLong("Quiz_Id"));
                    quiz.setQuestion(rs.getString("Question"));
                    quiz.setAnswerOptions(rs.getString("Answer_Options"));
                    quiz.setCorrectAnswer(rs.getString("Correct_Answer"));
                    quiz.setCreatedAt(rs.getDate("Created_At"));
                    quiz.setUpdatedAt(rs.getDate("Updated_At"));
                    quiz.setLessonId(rs.getLong("Lesson_Id"));
                    quiz.setLessonTitle(rs.getString("LessonTitle"));
                    return quiz;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting quiz by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Quiz> getQuizzesWithPagination(String query, int page, int pageSize, Long lessonId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT q.*, l.Title AS LessonTitle " +
                     "FROM Quiz q INNER JOIN Lesson l ON q.Lesson_Id = l.Lesson_Id " +
                     "WHERE q.Lesson_Id = ? AND q.Question LIKE ? " +
                     "ORDER BY q.Quiz_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            ps.setString(2, "%" + (query == null ? "" : query) + "%");
            ps.setInt(3, (page - 1) * pageSize);
            ps.setInt(4, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setQuizId(rs.getLong("Quiz_Id"));
                    quiz.setQuestion(rs.getString("Question"));
                    quiz.setAnswerOptions(rs.getString("Answer_Options"));
                    quiz.setCorrectAnswer(rs.getString("Correct_Answer"));
                    quiz.setCreatedAt(rs.getDate("Created_At"));
                    quiz.setUpdatedAt(rs.getDate("Updated_At"));
                    quiz.setLessonId(rs.getLong("Lesson_Id"));
                    quiz.setLessonTitle(rs.getString("LessonTitle"));
                    quizzes.add(quiz);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting quizzes with pagination: " + e.getMessage());
            e.printStackTrace();
        }
        return quizzes;
    }

    public int getTotalQuizzes(String query, Long lessonId) {
        String sql = "SELECT COUNT(*) FROM Quiz q INNER JOIN Lesson l ON q.Lesson_Id = l.Lesson_Id " +
                     "WHERE q.Lesson_Id = ? AND q.Question LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lessonId);
            ps.setString(2, "%" + (query == null ? "" : query) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error counting quizzes: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}