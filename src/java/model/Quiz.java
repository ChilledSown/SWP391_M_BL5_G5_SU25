package model;

import java.util.Date;

public class Quiz {

    private Long quizId;
    private String question;
    private String answerOptions;
    private String correctAnswer;
    private Date createdAt;
    private Date updatedAt;
    private Long lessonId;
    private String lessonTitle;
    private String courseTitle;
    private String explanation;

    public Quiz() {
    }

    public Quiz(Long quizId, String question, String answerOptions, String correctAnswer, Date createdAt, Date updatedAt, Long lessonId, String lessonTitle, String courseTitle, String explanation) {
        this.quizId = quizId;
        this.question = question;
        this.answerOptions = answerOptions;
        this.correctAnswer = correctAnswer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.courseTitle = courseTitle;
        this.explanation = explanation;
    }

    
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    // Getter & Setter
    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
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

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    @Override
    public String toString() {
        return "Quiz{" + "quizId=" + quizId + ", question=" + question + ", answerOptions=" + answerOptions + ", correctAnswer=" + correctAnswer + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", lessonId=" + lessonId + ", lessonTitle=" + lessonTitle + ", courseTitle=" + courseTitle + ", explanation=" + explanation + '}';
    }
    
}
