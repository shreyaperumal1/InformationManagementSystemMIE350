package com.group9.postal.controller;

import com.group9.postal.controller.exceptions.RouteNotFoundException;
import com.group9.postal.model.Address;
import com.group9.postal.model.Route;
import com.group9.postal.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.group9.postal.model.User;
import com.group9.postal.model.Order;
import com.group9.postal.model.Warehouse;
import com.group9.postal.model.RouteStop;

import com.group9.postal.repository.UserRepository;
import com.group9.postal.repository.WarehouseRepository;
import com.group9.postal.repository.OrderRepository;

@CrossOrigin
@RestController
public class RouteController {
    @Autowired
    private final RouteRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private OrderRepository orderRepository;

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

    @PostMapping("/route/manual")
    Route setManualRoute(
            @RequestParam Long driverEmail,
            @RequestParam Long warehouseId,
            @RequestParam List<Long> orderIds) {

        User driver = userRepository.findById(driverEmail)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        List<Order> orders = orderRepository.findAllById(orderIds);

        if (orders.isEmpty()) {
            throw new RuntimeException("No valid orders found");
        }

        if (orders.size() != orderIds.size()) {
            throw new RuntimeException("One or more orderIds are invalid");
        }

        Route route = new Route();
        route.setDriver(driver);
        route.setWarehouse(warehouse);
        route.setRouteStatus("PLANNED");

        List<RouteStop> routeStops = new ArrayList<>();
        int sequence = 1;

        for (Order order : orders) {
            Address dropoff = order.getDropoffAddress();

            RouteStop stopAddress = new RouteStop(
                    route,
                    sequence,
                    dropoff,
                    null,
                    null
            );

            RouteStop stop = new RouteStop(
                    route,
                    sequence,
                    dropoff,
                    null,
                    null
            );
            routeStops.add(stop);
            sequence++;
        }

        route.setStops(routeStops);
        return repository.save(route);
    }

    @DeleteMapping("/route/{id}")
    void deleteRoute(@PathVariable("id") Long routeId) {
        repository.deleteById(routeId);
    }
}