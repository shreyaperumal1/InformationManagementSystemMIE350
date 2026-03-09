package com.group9.postal.controller.exceptions;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(Long id) {
        super("Could not find vehicle with id: " + id);
    }
}
