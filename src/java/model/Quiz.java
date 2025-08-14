package model;

import java.util.Date;

public class Quiz {
    private Long quiz_id;
    private String question;
    private String answerOptions;
    private String correctAnswer;
    private Date createdAt;
    private Date updatedAt;
    private Long lessonId;

    public Quiz() {
    }

    public Quiz(Long quiz_id, String question, String answerOptions, String correctAnswer, Date createdAt, Date updatedAt, Long lessonId) {
        this.quiz_id = quiz_id;
        this.question = question;
        this.answerOptions = answerOptions;
        this.correctAnswer = correctAnswer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lessonId = lessonId;
    }

    public Long getQuizId() {
        return quiz_id;
    }

    public void setQuizId(Long quiz_id) {
        this.quiz_id = quiz_id;
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
        return "Quiz{" + "quizId=" + quiz_id + ", question=" + question + ", answerOptions=" + answerOptions + ", correctAnswer=" + correctAnswer + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", lessonId=" + lessonId + '}';
    }
}