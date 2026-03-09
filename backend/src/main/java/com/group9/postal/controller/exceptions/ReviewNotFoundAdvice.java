package com.group9.postal.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ReviewNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String reviewNotFoundHandler(ReviewNotFoundException ex) {
        return ex.getMessage();
    }
}