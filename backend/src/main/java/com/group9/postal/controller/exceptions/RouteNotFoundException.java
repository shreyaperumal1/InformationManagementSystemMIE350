package com.group9.postal.controller.exceptions;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(Long id) {
        super("Could not find route with id: " + id);
    }
}
