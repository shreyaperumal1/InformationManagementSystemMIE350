package com.group9.postal.controller.exceptions;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(Long id) {
        super("Could not find review with id: " + id);
    }
}