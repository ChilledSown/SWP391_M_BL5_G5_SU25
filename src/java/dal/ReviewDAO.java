/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
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
    
    /**
     * Get all reviews for a specific course
     */
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
    
    /**
     * Get review by user and course
     */
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
    
    /**
     * Get review by ID
     */
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
    
    /**
     * Insert new review
     */
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
    
    /**
     * Update existing review
     */
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
    
    /**
     * Delete review by ID
     */
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
    
    /**
     * Get average rating for a course
     */
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
    
    /**
     * Get review count for a course
     */
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
    
    /**
     * Get all reviews by user ID
     */
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
    
    /**
     * Check if user has reviewed a course
     */
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
}
