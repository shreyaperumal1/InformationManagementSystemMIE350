package com.group9.postal.controller;

import com.group9.postal.dto.StopShipmentRequest;
import com.group9.postal.model.RouteStop;
import com.group9.postal.model.Shipment;
import com.group9.postal.model.StopShipment;
import com.group9.postal.repository.RouteStopRepository;
import com.group9.postal.repository.ShipmentRepository;
import com.group9.postal.repository.StopShipmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/stop-shipments")
public class StopShipmentController {

    @Autowired
    private StopShipmentRepository repository;

    @Autowired
    private RouteStopRepository routeStopRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @GetMapping
    public List<StopShipment> getAll() {
        return repository.findAll();
    }

    @GetMapping("/stop/{stopId}")
    public List<StopShipment> getByStop(@PathVariable Long stopId) {
        return repository.findByRouteStop_StopId(stopId);
    }

    @PostMapping
    public StopShipment addStopShipment(@RequestBody StopShipmentRequest request) {

        RouteStop stop = routeStopRepository.findById(request.getStopId())
                .orElseThrow(() -> new RuntimeException("Stop not found"));

        Shipment shipment = shipmentRepository.findById(request.getShipmentId())
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        StopShipment ss = new StopShipment(stop, shipment, request.getAction());

        StopShipment.StopShipmentId id = new StopShipment.StopShipmentId();
        id.setStopId(stop.getStopId());
        id.setShipmentId(shipment.getShipmentId());
        ss.setId(id);

        return repository.save(ss);
    }

    @DeleteMapping
    public void deleteStopShipment(@RequestParam Long stopId,
                                   @RequestParam Long shipmentId) {

        StopShipment.StopShipmentId id = new StopShipment.StopShipmentId();
        id.setStopId(stopId);
        id.setShipmentId(shipmentId);

        repository.deleteById(id);
    }
}