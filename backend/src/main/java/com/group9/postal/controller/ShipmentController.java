package com.group9.postal.controller;

import com.group9.postal.controller.exceptions.ShipmentNotFoundException;
import com.group9.postal.model.Shipment;
import com.group9.postal.repository.ShipmentRepository;
import com.group9.postal.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
public class ShipmentController {
    @Autowired
    private final ShipmentRepository repository;
    private final ShipmentService shipmentService;


    public ShipmentController(ShipmentRepository repository, ShipmentService shipmentService) {
        this.repository = repository;
        this.shipmentService = shipmentService;
    }

    @GetMapping("/shipment")
    List<Shipment> retrieveAllShipments() {
        return repository.findAll();
    }

    @GetMapping("/shipment/{id}")
    Shipment retrieveShipment(@PathVariable("id") Long shipmentId) {
        return repository.findById(shipmentId)
                .orElseThrow(() -> new ShipmentNotFoundException(shipmentId));
    }

    @GetMapping("/shipment/track/{trackingNumber}")
    Shipment trackShipment(@PathVariable("trackingNumber") String trackingNumber) {
        return repository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ShipmentNotFoundException(trackingNumber));
    }

    @GetMapping("/shipment/status/{status}")
    List<Shipment> retrieveShipmentsByStatus(@PathVariable("status") String status) {
        return repository.findByCurrentStatus(status);
    }
    @GetMapping("/filter")
    public List<Shipment> getByStatusAndAddress(
            @RequestParam String status,
            @RequestParam Long addressId) {

        return shipmentService.getShipmentsByStatusAndAddress(status, addressId);
    }

    @PostMapping("/shipment")
    Shipment createShipment(@RequestBody Shipment newShipment) {
        return repository.save(newShipment);
    }

    @PutMapping("/shipment/{id}")
    Shipment updateShipment(@RequestBody Shipment newShipment, @PathVariable("id") Long shipmentId) {
        return repository.findById(shipmentId)
                .map(shipment -> {
                    shipment.setType(newShipment.getType());
                    shipment.setWeight(newShipment.getWeight());
                    shipment.setVolume(newShipment.getVolume());
                    shipment.setFragileFlag(newShipment.isFragileFlag());
                    shipment.setCurrentStatus(newShipment.getCurrentStatus());
                    shipment.setCurrentWarehouse(newShipment.getCurrentWarehouse());
                    return repository.save(shipment);
                })
                .orElseGet(() -> {
                    newShipment.setShipmentId(shipmentId);
                    return repository.save(newShipment);
                });
    }

    @DeleteMapping("/shipment/{id}")
    void deleteShipment(@PathVariable("id") Long shipmentId) {
        repository.deleteById(shipmentId);
    }

}
