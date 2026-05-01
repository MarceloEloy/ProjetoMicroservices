package com.example.pedidos.model.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException{
    public ValidationException(String field, String message){
        super(message);
        this.field = field;
        this.message = message;


    }

    private String field;

    private String message;



}
