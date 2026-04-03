package com.group9.postal.controller;

import com.group9.postal.model.RouteStop;
import com.group9.postal.repository.RouteStopRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/routeStop")
public class RouteStopController {

    private final RouteStopRepository routeStopRepository;

    public RouteStopController(RouteStopRepository routeStopRepository) {
        this.routeStopRepository = routeStopRepository;
    }

    @PutMapping("/{id}/complete")
    public RouteStop completeStop(@PathVariable Long id) {
        RouteStop stop = routeStopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stop not found"));

        stop.setCompletedTime(LocalDateTime.now());
        return routeStopRepository.save(stop);
    }
}