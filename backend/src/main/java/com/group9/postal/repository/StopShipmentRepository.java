package com.group9.postal.repository;

import com.group9.postal.model.StopShipment;
import com.group9.postal.model.StopShipment.StopShipmentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StopShipmentRepository extends JpaRepository<StopShipment, StopShipmentId> {

    List<StopShipment> findByRouteStop_StopId(Long stopId);

    List<StopShipment> findByShipment_ShipmentId(Long shipmentId);
}