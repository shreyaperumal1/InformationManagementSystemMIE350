package com.group9.postal.controller;

import com.group9.postal.controller.exceptions.WarehouseNotFoundException;
import com.group9.postal.model.Warehouse;
import com.group9.postal.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
public class WarehouseController {
    @Autowired
    private final WarehouseRepository repository;

    public WarehouseController(WarehouseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/warehouse")
    List<Warehouse> retrieveAllWarehouses() {
        return repository.findAll();
    }

    @GetMapping("/warehouse/{id}")
    Warehouse retrieveWarehouse(@PathVariable("id") Long warehouseId) {
        return repository.findById(warehouseId)
                .orElseThrow(() -> new WarehouseNotFoundException(warehouseId));
    }

    @PostMapping("/warehouse")
    Warehouse createWarehouse(@RequestBody Warehouse newWarehouse) {
        return repository.save(newWarehouse);
    }

    @PutMapping("/warehouse/{id}")
    Warehouse updateWarehouse(@RequestBody Warehouse newWarehouse, @PathVariable("id") Long warehouseId) {
        return repository.findById(warehouseId)
                .map(warehouse -> {
                    warehouse.setName(newWarehouse.getName());
                    warehouse.setAddress(newWarehouse.getAddress());
                    warehouse.setCapacityMaxPackages(newWarehouse.getCapacityMaxPackages());
                    warehouse.setRegion(newWarehouse.getRegion());
                    warehouse.setPostalCode(newWarehouse.getPostalCode());
                    return repository.save(warehouse);
                })
                .orElseGet(() -> {
                    newWarehouse.setWarehouseId(warehouseId);
                    return repository.save(newWarehouse);
                });
    }

    @DeleteMapping("/warehouse/{id}")
    void deleteWarehouse(@PathVariable("id") Long warehouseId) {
        repository.deleteById(warehouseId);
    }
}
