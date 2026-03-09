package com.group9.postal.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class RouteNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(RouteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String routeNotFoundHandler(RouteNotFoundException ex) {
        return ex.getMessage();
    }
}
