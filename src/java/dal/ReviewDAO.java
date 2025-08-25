package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Review;

/**
 *
 * @author sondo
 */
public class ReviewDAO extends DBContext {
    public List<Review> getReviewsByCourseId(long courseId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM Review WHERE Course_Id = ? ORDER BY Created_At DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setReview_id(rs.getLong("Review_Id"));
                review.setCourse_id(rs.getLong("Course_Id"));
                review.setUser_id(rs.getLong("User_Id"));
                review.setRating(rs.getInt("Rating"));
                review.setComment(rs.getString("Comment"));
                review.setCreated_at(rs.getTimestamp("Created_At"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public Review getReviewByUserAndCourse(long userId, long courseId) {
        String sql = "SELECT * FROM Review WHERE User_Id = ? AND Course_Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Review review = new Review();
                review.setReview_id(rs.getLong("Review_Id"));
                review.setCourse_id(rs.getLong("Course_Id"));
                review.setUser_id(rs.getLong("User_Id"));
                review.setRating(rs.getInt("Rating"));
                review.setComment(rs.getString("Comment"));
                review.setCreated_at(rs.getTimestamp("Created_At"));
                return review;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Review getReviewById(long reviewId) {
        String sql = "SELECT * FROM Review WHERE Review_Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, reviewId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Review review = new Review();
                review.setReview_id(rs.getLong("Review_Id"));
                review.setCourse_id(rs.getLong("Course_Id"));
                review.setUser_id(rs.getLong("User_Id"));
                review.setRating(rs.getInt("Rating"));
                review.setComment(rs.getString("Comment"));
                review.setCreated_at(rs.getTimestamp("Created_At"));
                return review;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertReview(Review review) {
        String sql = "INSERT INTO Review (Course_Id, User_Id, Rating, Comment, Created_At) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, review.getCourse_id());
            ps.setLong(2, review.getUser_id());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateReview(Review review) {
        String sql = "UPDATE Review SET Rating = ?, Comment = ? WHERE Review_Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setLong(3, review.getReview_id());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReview(long reviewId) {
        String sql = "DELETE FROM Review WHERE Review_Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, reviewId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getAverageRatingByCourseId(long courseId) {
        String sql = "SELECT AVG(Rating) as AverageRating FROM Review WHERE Course_Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double avgRating = rs.getDouble("AverageRating");
                return avgRating > 0 ? avgRating : 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public int getReviewCountByCourseId(long courseId) {
        String sql = "SELECT COUNT(*) as ReviewCount FROM Review WHERE Course_Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("ReviewCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Review> getReviewsByUserId(long userId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM Review WHERE User_Id = ? ORDER BY Created_At DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setReview_id(rs.getLong("Review_Id"));
                review.setCourse_id(rs.getLong("Course_Id"));
                review.setUser_id(rs.getLong("User_Id"));
                review.setRating(rs.getInt("Rating"));
                review.setComment(rs.getString("Comment"));
                review.setCreated_at(rs.getTimestamp("Created_At"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public boolean hasUserReviewedCourse(long userId, long courseId) {
        String sql = "SELECT COUNT(*) FROM Review WHERE User_Id = ? AND Course_Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Existing getReviewsByInstructor method (unchanged but confirmed compatible)
    public List<Review> getReviewsByInstructor(long instructorId, String courseTitle, String reviewDate, String rating) {
        List<Review> reviews = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT r.Review_Id, r.Course_Id, r.User_Id, r.Rating, r.Comment, r.Created_At, c.Title AS courseTitle, u.FirstName + ' ' + u.LastName AS studentName " +
                "FROM Review r " +
                "JOIN Course c ON r.Course_Id = c.Course_Id " +
                "JOIN Users u ON r.User_Id = u.UserID " +
                "WHERE c.Created_By = ?"
        );
        List<Object> parameters = new ArrayList<>();
        parameters.add(instructorId);

        if (courseTitle != null && !courseTitle.isEmpty()) {
            sql.append(" AND c.Title LIKE ?");
            parameters.add("%" + courseTitle + "%");
        }
        if (reviewDate != null && !reviewDate.isEmpty()) {
            sql.append(" AND CAST(r.Created_At AS DATE) = ?");
            parameters.add(reviewDate);
        }
        if (rating != null && !rating.isEmpty() && !rating.equals("0")) {
            sql.append(" AND r.Rating = ?");
            parameters.add(Integer.parseInt(rating));
        }
        sql.append(" ORDER BY r.Created_At DESC");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Review review = new Review(
                    rs.getLong("Review_Id"),
                    rs.getLong("Course_Id"),
                    rs.getInt("Rating"),
                    rs.getString("Comment"),
                    rs.getTimestamp("Created_At"),
                    rs.getLong("User_Id"),
                    rs.getString("courseTitle"),
                    rs.getString("studentName")
                );
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}