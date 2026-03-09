package com.group9.postal.controller;

import com.group9.postal.model.TrackingEvent;
import com.group9.postal.repository.TrackingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
public class TrackingEventController {
    @Autowired
    private final TrackingEventRepository repository;

    public TrackingEventController(TrackingEventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/tracking")
    List<TrackingEvent> retrieveAllEvents() {
        return repository.findAll();
    }

    @GetMapping("/tracking/shipment/{shipmentId}")
    List<TrackingEvent> retrieveEventsByShipment(@PathVariable("shipmentId") Long shipmentId) {
        return repository.findByShipmentIdOrderByTimestamp(shipmentId);
    }

    @PostMapping("/tracking")
    TrackingEvent createEvent(@RequestBody TrackingEvent newEvent) {
        return repository.save(newEvent);
    }

    @DeleteMapping("/tracking/{id}")
    void deleteEvent(@PathVariable("id") Long eventId) {
        repository.deleteById(eventId);
    }
}
