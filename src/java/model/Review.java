/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author sondo
 */
public class Review {

    private Long review_id;
    private Long course_id;
    private int rating;
    private String comment;
    private Date created_at;
    private Long user_id;
    private String courseTitle;
    private String studentName;

    public Review(Long review_id, Long course_id, int rating, String comment, Date created_at, Long user_id, String courseTitle, String studentName) {
        this.review_id = review_id;
        this.course_id = course_id;
        this.rating = rating;
        this.comment = comment;
        this.created_at = created_at;
        this.user_id = user_id;
        this.courseTitle = courseTitle;
        this.studentName = studentName;
    }
    

    public Review(Long review_id, Long course_id, int rating, String comment, Date created_at, Long user_id) {
        this.review_id = review_id;
        this.course_id = course_id;
        this.rating = rating;
        this.comment = comment;
        this.created_at = created_at;
        this.user_id = user_id;
    }

    public Review() {
    }

    public Long getReview_id() {
        return review_id;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setReview_id(Long review_id) {
        this.review_id = review_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

}
