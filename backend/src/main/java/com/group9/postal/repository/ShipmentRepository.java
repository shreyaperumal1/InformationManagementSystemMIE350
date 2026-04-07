package com.group9.postal.repository;

import com.group9.postal.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByOrderOrderId(Long orderId);
    Optional<Shipment> findByTrackingNumber(String trackingNumber);
    List<Shipment> findByCurrentStatus(String status);
    boolean existsByTrackingNumber(String trackingNumber);
    @Query("""
    SELECT s FROM Shipment s
    WHERE s.currentStatus = :status
    AND (
        s.order.pickupAddress.addressId = :addressId
        OR s.order.dropoffAddress.addressId = :addressId
    )
""")
    List<Shipment> findByStatusAndAddress(
            @Param("status") String status,
            @Param("addressId") Long addressId
    );
}
