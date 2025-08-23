package model;

import java.util.Date;

/**
 * Model for inline video quizzes that appear at specific timestamps during video playback
 */
public class VideoQuiz {
    private Long videoQuizId;
    private Long lessonId;
    private Integer timestamp; 
    private String question; 
    private String answerOptions;
    private String correctAnswer;
    private String explanation; 
    private Boolean isActive; 
    private Date createdAt;
    private Date updatedAt;
    private String lessonTitle;

    public VideoQuiz() {
    }

    public VideoQuiz(Long videoQuizId, Long lessonId, Integer timestamp,
                    String question, String answerOptions, String correctAnswer, String explanation,
                    Boolean isActive, Date createdAt, Date updatedAt) {
        this.videoQuizId = videoQuizId;
        this.lessonId = lessonId;
        this.timestamp = timestamp;
        this.question = question;
        this.answerOptions = answerOptions;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getVideoQuizId() {
        return videoQuizId;
    }

    public void setVideoQuizId(Long videoQuizId) {
        this.videoQuizId = videoQuizId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(String answerOptions) {
        this.answerOptions = answerOptions;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    // Convenience getters for joined data
    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    @Override
    public String toString() {
        return "VideoQuiz{" + "videoQuizId=" + videoQuizId + ", lessonId=" + lessonId + 
               ", timestamp=" + timestamp + ", question=" + question + ", isActive=" + isActive + 
               ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
