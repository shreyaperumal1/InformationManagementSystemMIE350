package com.group9.postal.controller;

import com.group9.postal.controller.exceptions.RouteNotFoundException;
import com.group9.postal.model.Route;
import com.group9.postal.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.group9.postal.model.User;
import com.group9.postal.model.Order;
import com.group9.postal.model.Warehouse;

import com.group9.postal.repository.UserRepository;
import com.group9.postal.repository.UserRepository;
import com.group9.postal.repository.UserRepository;

@CrossOrigin
@RestController
public class RouteController {
    @Autowired
    private final RouteRepository repository;

    public RouteController(RouteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/route")
    List<Route> retrieveAllRoutes() {
        return repository.findAll();
    }

    @GetMapping("/route/{id}")
    Route retrieveRoute(@PathVariable("id") Long routeId) {
        return repository.findById(routeId)
                .orElseThrow(() -> new RouteNotFoundException(routeId));
    }

    @GetMapping("/route/driver/{driverId}")
    List<Route> retrieveRoutesByDriver(@PathVariable("driverId") Long driverId) {
        return repository.findByDriverUserId(driverId);
    }

    @GetMapping("/route/status/{status}")
    List<Route> retrieveRoutesByStatus(@PathVariable("status") String status) {
        return repository.findByRouteStatus(status);
    }

    @PostMapping("/route")
    Route createRoute(@RequestBody Route newRoute) {
        return repository.save(newRoute);
    }

    @PutMapping("/route/{id}")
    Route updateRoute(@RequestBody Route newRoute, @PathVariable("id") Long routeId) {
        return repository.findById(routeId)
                .map(route -> {
                    route.setStops(newRoute.getStops());
                    route.setRouteStatus(newRoute.getRouteStatus());
                    route.setPlannedStartTime(newRoute.getPlannedStartTime());
                    route.setPlannedEndTime(newRoute.getPlannedEndTime());
                    route.setDriver(newRoute.getDriver());
                    route.setWarehouse(newRoute.getWarehouse());
                    return repository.save(route);
                })
                .orElseGet(() -> {
                    newRoute.setRouteId(routeId);
                    return repository.save(newRoute);
                });
    }

    //Set route
    @PostMapping("/route/manual")
    Route setManualRoute(
            @RequestParam Long driverId,
            @RequestParam Long warehouseId,
            @RequestParam List<Long> orderIds ) {

    }

    @DeleteMapping("/route/{id}")
    void deleteRoute(@PathVariable("id") Long routeId) {
        repository.deleteById(routeId);
    }
}
