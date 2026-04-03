package com.group9.postal.controller;

import com.group9.postal.dto.TrackingEventRequest;
import com.group9.postal.model.Shipment;
import com.group9.postal.model.TrackingEvent;
import com.group9.postal.repository.ShipmentRepository;
import com.group9.postal.repository.TrackingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
public class TrackingEventController {
    @Autowired
    private final TrackingEventRepository repository;
    @Autowired
    private ShipmentRepository shipmentRepository;

    public TrackingEventController(TrackingEventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/tracking")
    List<TrackingEvent> retrieveAllEvents() {
        return repository.findAll();
    }

    @GetMapping("/tracking/shipment/{shipmentId}")
    List<TrackingEvent> retrieveEventsByShipment(@PathVariable("shipmentId") Long shipmentId) {
        return repository.findByShipmentShipmentIdOrderByTimestamp(shipmentId);
    }

    @PostMapping("/tracking")
    TrackingEvent createEvent(@RequestBody TrackingEventRequest req) {

        Shipment shipment = shipmentRepository.findById(req.getShipmentId())
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        TrackingEvent event = new TrackingEvent(
                shipment,
                req.getStatus(),
                req.getLocationText(),
                req.getNote()
        );

        return repository.save(event);
    }

    @DeleteMapping("/tracking/{id}")
    void deleteEvent(@PathVariable("id") Long eventId) {
        repository.deleteById(eventId);
    }
}
