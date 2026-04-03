package com.group9.postal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
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

    @Setter
    @Getter
    @Embeddable
    public static class StopShipmentId implements Serializable {
        private Long stopId;
        private Long shipmentId;

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
