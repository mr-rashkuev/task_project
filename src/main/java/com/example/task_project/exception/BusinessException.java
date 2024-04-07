package com.example.task_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
