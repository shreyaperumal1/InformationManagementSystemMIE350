package com.group9.postal.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ShipmentNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ShipmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String shipmentNotFoundHandler(ShipmentNotFoundException ex) {
        return ex.getMessage();
    }
}
