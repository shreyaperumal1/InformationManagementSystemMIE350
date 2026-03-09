package com.group9.postal.controller.exceptions;

public class WarehouseNotFoundException extends RuntimeException {
    public WarehouseNotFoundException(Long id) {
        super("Could not find warehouse with id: " + id);
    }
}