package com.group9.postal.controller;

import com.group9.postal.controller.exceptions.RouteNotFoundException;
import com.group9.postal.model.Address;
import com.group9.postal.model.Route;
import com.group9.postal.repository.*;
import com.group9.postal.service.RouteOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.group9.postal.model.User;
import com.group9.postal.model.Order;
import com.group9.postal.model.Warehouse;
import com.group9.postal.model.RouteStop;

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

    @Autowired
    private RouteStopRepository stopRepository;

    @Autowired
    private RouteOptimizationService optimizationService;



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

            String stopAddress =
                    (dropoff.getAptNum() != null && !dropoff.getAptNum().isBlank()
                            ? dropoff.getAptNum() + "-" : "") +
                    dropoff.getStreetNum() + " " +
                    dropoff.getStreetName() + " " +
                    dropoff.getStreetType() + ", " +
                    dropoff.getCity() + ", " +
                    dropoff.getProvinceState() + ", " +
                    dropoff.getCountry() + ", " +
                    dropoff.getPostalZip();

            RouteStop stop = new RouteStop(
                    route,
                    sequence,
                    dropoff, //formerly stopAddress
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

    @PostMapping("/route/{id}/optimize")
    public List<RouteStop> optimizeRoute(@PathVariable Long id) {
        Route route = repository.findById(id)
                .orElseThrow(() -> new RouteNotFoundException(id));

        Address warehouse = route.getWarehouse().getAddress();
        List<Address> stopAddresses = route.getStops().stream()
                .map(stop -> stop.getStopAddress())
                .collect(Collectors.toList());

        List<Address> optimized = optimizationService.optimizeRoute(
                warehouse, stopAddresses
        );

        // Update stop sequence based on optimized order
        for (int i = 0; i < optimized.size(); i++) {
            Address currentAddress = optimized.get(i);

            RouteStop stop = route.getStops().stream()
                    .filter(s -> s.getStopAddress().equals(currentAddress))
                    .findFirst()
                    .orElseThrow();

            stop.setStopSequence(i + 1);
            stopRepository.save(stop);
        }

        return route.getStops();
    }
}