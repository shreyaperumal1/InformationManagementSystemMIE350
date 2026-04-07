package com.group9.postal.controller;

import com.group9.postal.controller.exceptions.RouteNotFoundException;
import com.group9.postal.dto.RouteDTO;
import com.group9.postal.model.*;
import com.group9.postal.repository.OrderRepository;
import com.group9.postal.repository.UserRepository;
import com.group9.postal.repository.WarehouseRepository;
import com.group9.postal.service.RouteOptimizationService;
import com.group9.postal.service.RouteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    private final WarehouseRepository warehouseRepository;

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final RouteOptimizationService optimizationService;

    @Autowired
    public RouteController(WarehouseRepository warehouseRepository,
                           UserRepository userRepository,
                           OrderRepository orderRepository,
                           RouteOptimizationService optimizationService,
                           RouteService routeService) {

        this.warehouseRepository = warehouseRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.optimizationService = optimizationService;
        this.routeService = routeService;
    }
    @GetMapping
    public List<RouteDTO> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public RouteDTO getRoute(@PathVariable Long id) {
        return routeService.getRoute(id);
    }

    @GetMapping("/driver/{driverId}")
    public List<RouteDTO> getRoutesByDriver(@PathVariable Long driverId) {
        return routeService.getRoutesByDriver(driverId);
    }

    @PostMapping
    public RouteDTO createRoute(@RequestBody RouteDTO dto) {
        return routeService.saveRoute(dto);
    }

    @PutMapping("/{id}")
    public RouteDTO updateRoute(@PathVariable Long id, @RequestBody RouteDTO dto) {
        dto.setRouteId(id);
        return routeService.saveRoute(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
    }

    @PostMapping("/optimize")
    public List<Route> optimizeRoute(@RequestBody java.util.Map<String, Object> request) {
        Long warehouseId = ((Number) request.get("warehouseId")).longValue();
        List<Integer> driverIdInts = (List<Integer>) request.get("driverIds");
        List<Integer> orderIdInts = (List<Integer>) request.get("orderIds");

        List<Long> driverIds = driverIdInts.stream().map(Long::valueOf).collect(Collectors.toList());
        List<Long> orderIds = orderIdInts.stream().map(Long::valueOf).collect(Collectors.toList());

        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElse(null);
        if (warehouse == null || orderIds.isEmpty() || driverIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<User> drivers = userRepository.findAllById(driverIds);
        List<Order> orders = orderRepository.findAllById(orderIds);

        return optimizationService.createOptimizedRoutes(warehouse, orders, drivers);
    }

    /**
    @PostMapping("/route/{id}/optimize")
    public List<RouteStop> optimizeRoute(@PathVariable Long id) {
        Route route = repository.findById(id)
                .orElseThrow(() -> new RouteNotFoundException(id));

        Address warehouse = route.getWarehouse().getAddress();
        List<Address> stopAddresses = route.getStops().stream()
                .map(stop -> stop.getAddress())
                .collect(Collectors.toList());

        List<Address> optimized = optimizationService.runOptimizer(
                warehouse, stopAddresses
        );

        // Update stop sequence based on optimized order
        for (int i = 0; i < optimized.size(); i++) {
            Address currentAddress = optimized.get(i);

            RouteStop stop = route.getStops().stream()
                    .filter(s -> s.getAddress().equals(currentAddress))
                    .findFirst()
                    .orElseThrow();

            stop.setStopSequence(i + 1);
            stopRepository.save(stop);
        }

        return route.getStops();
    }
    ***/
}