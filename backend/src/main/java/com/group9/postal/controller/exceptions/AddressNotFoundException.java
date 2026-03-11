package com.group9.postal.controller.exceptions;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Long id) {
        super("Could not find address " + id);
    }
}