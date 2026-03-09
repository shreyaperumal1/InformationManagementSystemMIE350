package com.group9.postal.controller;

import com.group9.postal.controller.exceptions.VehicleNotFoundException;
import com.group9.postal.model.Vehicle;
import com.group9.postal.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
public class VehicleController {
    @Autowired
    private final VehicleRepository repository;

    public VehicleController(VehicleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/vehicle")
    List<Vehicle> retrieveAllVehicles() {
        return repository.findAll();
    }

    @GetMapping("/vehicle/{id}")
    Vehicle retrieveVehicle(@PathVariable("id") Long vehicleId) {
        return repository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));
    }

    @PostMapping("/vehicle")
    Vehicle createVehicle(@RequestBody Vehicle newVehicle) {
        return repository.save(newVehicle);
    }

    @PutMapping("/vehicle/{id}")
    Vehicle updateVehicle(@RequestBody Vehicle newVehicle, @PathVariable("id") Long vehicleId) {
        return repository.findById(vehicleId)
                .map(vehicle -> {
                    vehicle.setPlate(newVehicle.getPlate());
                    vehicle.setType(newVehicle.getType());
                    vehicle.setCapacityWeight(newVehicle.getCapacityWeight());
                    vehicle.setCapacityVolume(newVehicle.getCapacityVolume());
                    vehicle.setAssignedDriver(newVehicle.getAssignedDriver());
                    return repository.save(vehicle);
                })
                .orElseGet(() -> {
                    newVehicle.setVehicleId(vehicleId);
                    return repository.save(newVehicle);
                });
    }

    @DeleteMapping("/vehicle/{id}")
    void deleteVehicle(@PathVariable("id") Long vehicleId) {
        repository.deleteById(vehicleId);
    }
}
