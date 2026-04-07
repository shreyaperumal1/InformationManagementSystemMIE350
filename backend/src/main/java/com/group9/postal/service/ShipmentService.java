package com.group9.postal.service;

import com.group9.postal.model.Shipment;
import com.group9.postal.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public List<Shipment> getShipmentsByStatusAndAddress(String status, Long addressId) {
        return shipmentRepository.findByStatusAndAddress(status, addressId);
    }
}