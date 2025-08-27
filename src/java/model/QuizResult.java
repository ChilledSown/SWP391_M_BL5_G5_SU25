/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sondo
 */
public class QuizResult {
        private Long quizId;
        private String userAnswer;
        private String correctAnswer;
        private boolean isCorrect;
        private boolean isAnswered;
        private String question;
        private String explanation;
        
        public Long getQuizId() { return quizId; }
        public void setQuizId(Long quizId) { this.quizId = quizId; }
        
        public String getUserAnswer() { return userAnswer; }
        public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }
        
        public String getCorrectAnswer() { return correctAnswer; }
        public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
        
        public boolean isCorrect() { return isCorrect; }
        public void setCorrect(boolean correct) { isCorrect = correct; }
        
        public boolean isAnswered() { return isAnswered; }
        public void setAnswered(boolean answered) { isAnswered = answered; }
        
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        
        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }
    }