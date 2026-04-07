package com.group9.postal.service;

import com.group9.postal.dto.RouteDTO;
import com.group9.postal.dto.RouteStopDTO;
import com.group9.postal.dto.StopShipmentDTO;
import com.group9.postal.model.*;
import com.group9.postal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    @Autowired private RouteRepository routeRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private WarehouseRepository warehouseRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private ShipmentRepository shipmentRepository;
    @Autowired private RouteStopRepository routeStopRepository;

    /*
     ========================================
     SAVE / UPDATE ROUTE
     ========================================
     */
    public RouteDTO saveRoute(RouteDTO dto) {

        Route route;

        // ========================
        // LOAD OR CREATE ROUTE
        // ========================
        if (dto.getRouteId() != null) {
            route = routeRepository.findById(dto.getRouteId())
                    .orElseThrow(() -> new RuntimeException("Route not found"));

            // 🔥 Clear safely (requires orphanRemoval = true)
            route.getStops().clear();
        } else {
            route = new Route();
        }

        // ========================
        // BASIC FIELDS
        // ========================
        route.setDriver(
                userRepository.findById(dto.getDriverId()).orElseThrow()
        );

        route.setWarehouse(
                warehouseRepository.findById(dto.getWarehouseId()).orElseThrow()
        );

        route.setPlannedStartTime(dto.getPlannedStartTime());
        route.setPlannedEndTime(dto.getPlannedEndTime());
        route.setRouteStatus(Route.Status.valueOf(dto.getRouteStatus()));
        // ========================
        // STOPS LOOP
        // ========================
        for (RouteStopDTO stopDTO : dto.getStops()) {

            RouteStop stop = new RouteStop();

            stop.setRoute(route);
            stop.setStopSequence(stopDTO.getStopSequence());

            stop.setAddress(
                    addressRepository.findById(stopDTO.getAddressId()).orElseThrow()
            );

            stop.setStopType(
                    RouteStop.StopType.valueOf(stopDTO.getStopType())
            );

            stop.setPlannedTime(stopDTO.getPlannedTime());
            stop.setCompletedTime(stopDTO.getCompletedTime());

            // 🔥 ADD TO ROUTE FIRST (important for JPA)
            route.getStops().add(stop);


            // ========================
            // STOP SHIPMENTS
            // ========================
            List<StopShipment> stopShipments = new ArrayList<>();

            if (stopDTO.getStopShipments() != null) {
                for (StopShipmentDTO ssDTO : stopDTO.getStopShipments()) {

                    Shipment shipment = shipmentRepository.findById(ssDTO.getShipmentId())
                            .orElseThrow(() -> new RuntimeException("Shipment not found"));

                    StopShipment ss = new StopShipment();

                    StopShipment.StopShipmentId id = new StopShipment.StopShipmentId();
                    id.setStopId(stop.getStopId());
                    id.setShipmentId(shipment.getShipmentId());

                    ss.setId(id);
                    ss.setRouteStop(stop);
                    ss.setShipment(shipment);
                    ss.setAction(ssDTO.getAction());

                    stopShipments.add(ss);
                }
            }

            stop.setStopShipments(stopShipments);
        }

        Route saved = routeRepository.save(route);

        return toDTO(saved);
    }

    /*
     ========================================
     GET ROUTE
     ========================================
     */
    public RouteDTO getRoute(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found"));
        return toDTO(route);
    }

    public List<RouteDTO> getAllRoutes() {
        return routeRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }
    public List<RouteDTO> getRoutesByDriver(Long driverId) {
        return routeRepository.findByDriverUserId(driverId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new RuntimeException("Route not found");
        }
        routeRepository.deleteById(id);
    }

    /*
     ========================================
     ENTITY → DTO
     ========================================
     */
    private RouteDTO toDTO(Route route) {
        RouteDTO dto = new RouteDTO();

        dto.setRouteId(route.getRouteId());
        dto.setDriverId(route.getDriver().getUserId());
        dto.setWarehouseId(route.getWarehouse().getWarehouseId());
        dto.setPlannedStartTime(route.getPlannedStartTime());
        dto.setPlannedEndTime(route.getPlannedEndTime());
        route.setRouteStatus(Route.Status.valueOf(dto.getRouteStatus()));
        dto.setStops(
                route.getStops().stream()
                        .map(this::toDTO)
                        .toList()
        );

        return dto;
    }

    private RouteStopDTO toDTO(RouteStop stop) {
        RouteStopDTO dto = new RouteStopDTO();

        dto.setStopId(stop.getStopId());
        dto.setStopSequence(stop.getStopSequence());

        Address addr = stop.getAddress();
        dto.setAddressId(addr.getAddressId());

        // 🔥 ADDRESS STRING FIX
        StringBuilder address = new StringBuilder();

        if (addr.getAptNum() != null && !addr.getAptNum().isEmpty()) {
            address.append(addr.getAptNum()).append("-");
        }

        address.append(addr.getStreetNum()).append(" ")
                .append(addr.getStreetName()).append(" ")
                .append(addr.getStreetType().name().toLowerCase());

        address.append(", ").append(addr.getCity());
        address.append(", ").append(addr.getProvinceState());
        address.append(", ").append(addr.getPostalZip());

        dto.setAddress(address.toString());

        dto.setStopType(stop.getStopType().name());
        dto.setPlannedTime(stop.getPlannedTime());
        dto.setCompletedTime(stop.getCompletedTime());

        dto.setStopShipments(
                stop.getStopShipments().stream().map(ss -> {
                    StopShipmentDTO s = new StopShipmentDTO();
                    s.setShipmentId(ss.getShipment().getShipmentId());
                    s.setTrackingNumber(ss.getShipment().getTrackingNumber());
                    s.setType(ss.getShipment().getType());
                    s.setAction(ss.getAction());
                    return s;
                }).toList()
        );

        return dto;
    }
}
