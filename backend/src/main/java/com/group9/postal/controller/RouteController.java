package com.group9.postal.controller;

import com.group9.postal.dto.RouteDTO;
import com.group9.postal.service.RouteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

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