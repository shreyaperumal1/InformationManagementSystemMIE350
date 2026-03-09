package com.group9.postal.controller.exceptions;

public class ShipmentNotFoundException extends RuntimeException {
    public ShipmentNotFoundException(Long id) {
        super("Could not find shipment with id: " + id);
    }
    public ShipmentNotFoundException(String trackingNumber) {
        super("Could not find shipment with tracking number: " + trackingNumber);
    }
}
