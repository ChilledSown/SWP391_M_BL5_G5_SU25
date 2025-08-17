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
public class Course {
    private Long course_id;
    private String title;
    private String description;
    private int price;
    private String thumbnail_url;
    private Date created_at;
    private Date updated_at;
    private Long topic_id;
    private Double averageRating;
    
    // Default constructor
    public Course() {
    }
    
    // Constructor with all parameters
    public Course(Long course_id, String title, String description, int price, String thumbnail_url, Date created_at, Date updated_at, Long topic_id, Double averageRating) {
        this.course_id = course_id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.thumbnail_url = thumbnail_url;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.topic_id = topic_id;
        this.averageRating = averageRating;
    }
    
    // Getter methods
    public Long getCourse_id() {
        return course_id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getPrice() {
        return price;
    }
    
    public String getThumbnail_url() {
        return thumbnail_url;
    }
    
    public Date getCreated_at() {
        return created_at;
    }
    
    public Date getUpdated_at() {
        return updated_at;
    }

   
    
    public Long getTopic_id() {
        return topic_id;
    }
    
    public Double getAverageRating() {
        return averageRating;
    }
    
    // Setter methods
    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }
    
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
    
    public void setTopic_id(Long topic_id) {
        this.topic_id = topic_id;
    }
    
    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
    @Override
    public String toString() {
        return "Course{" + "course_id=" + course_id + ", title=" + title + ", description=" + description + ", price=" + price + ", thumbnail_url=" + thumbnail_url + ", created_at=" + created_at + ", updated_at=" + updated_at + ", topic_id=" + topic_id + ", averageRating=" + averageRating + '}';
    }

}