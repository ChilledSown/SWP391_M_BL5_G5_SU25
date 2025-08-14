package model;

import java.util.Date;

public class Course {
    private Long course_id;
    private String title;
    private String description;
    private Integer price;
    private String thumbnailUrl;
    private Date createdAt;
    private Date updatedAt;
    private Long topicId;

    public Course() {
    }

    public Course(Long course_id, String title, String description, Integer price, String thumbnailUrl, Date createdAt, Date updatedAt, Long topicId) {
        this.course_id = course_id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.topicId = topicId;
    }

    public Long getCourseId() {
        return course_id;
    }

    public void setCourseId(Long course_id) {
        this.course_id = course_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    @Override
    public String toString() {
        return "Course{" + "courseId=" + course_id + ", title=" + title + ", description=" + description + ", price=" + price + ", thumbnailUrl=" + thumbnailUrl + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", topicId=" + topicId + '}';
    }
}