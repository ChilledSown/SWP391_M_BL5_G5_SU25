package model;

import java.sql.Date;

public class Quiz {
    private Long quiz_id;
    private String question;
    private String answer_options;
    private String correct_answer;
    private Date created_at;
    private Date updated_at;
    private Long lesson_id;

    // Constructor
    public Quiz() {
    }

    // Getters and Setters
    public Long getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(Long quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer_options() {
        return answer_options;
    }

    public void setAnswer_options(String answer_options) {
        this.answer_options = answer_options;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Long getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(Long lesson_id) {
        this.lesson_id = lesson_id;
    }
}