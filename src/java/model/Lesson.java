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
public class Lesson {
    private Long lessonId;
    private String title;
    private String videoUrl;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private Long courseId;

    public Lesson() {
    }

    public Lesson(Long lessonId, String title, String videoUrl, String content, Date createdAt, Date updatedAt, Long courseId) {
        this.lessonId = lessonId;
        this.title = title;
        this.videoUrl = videoUrl;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.courseId = courseId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Lesson{" + "lessonId=" + lessonId + ", title=" + title + ", videoUrl=" + videoUrl + ", content=" + content + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", courseId=" + courseId + '}';
    }

    
    
}
