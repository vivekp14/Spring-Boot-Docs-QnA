package com.App.Spring.Boot.Docs.QnA.dto;
import jakarta.validation.constraints.NotBlank;

public class QnaRequestDTO {
    @NotBlank(message = "Question is required")
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
