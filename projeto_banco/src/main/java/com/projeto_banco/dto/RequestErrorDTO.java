package com.projeto_banco.dto;

public class RequestErrorDTO {
    public String message;
    public String error;

    public RequestErrorDTO(String message, String error) {
        this.message = message;
        this.error = error;
    }
}
