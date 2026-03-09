package com.group9.postal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "stop_shipment")
public class StopShipment {
    @EmbeddedId
    private StopShipmentId id = new StopShipmentId();

    @ManyToOne
    @MapsId("stopId")
    @JoinColumn(name = "stopId")
    private RouteStop routeStop;

    @ManyToOne
    @MapsId("shipmentId")
    @JoinColumn(name = "shipmentId")
    private Shipment shipment;

    @NotEmpty
    private String action;

    public StopShipment(RouteStop routeStop, Shipment shipment, String action) {
        this.routeStop = routeStop;
        this.shipment = shipment;
        this.action = action;
    }

    @Embeddable
    public static class StopShipmentId implements Serializable {
        private Long stopId;
        private Long shipmentId;

        public Long getStopId() {
            return stopId;
        }

        public void setStopId(Long id) {
            this.stopId = id;
        }

        public Long getShipmentId() {
            return shipmentId;
        }

        public void setShipmentId(Long id) {
            this.shipmentId = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StopShipmentId)) return false;
            StopShipmentId that = (StopShipmentId) o;
            return Objects.equals(stopId, that.stopId) &&
                    Objects.equals(shipmentId, that.shipmentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stopId, shipmentId);
        }
    }
}
