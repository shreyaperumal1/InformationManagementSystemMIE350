package com.group9.postal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="shipment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @NotEmpty
    @Column(unique = true)
    private String trackingNumber;

    @NotEmpty
    private String type;

    private float weight;
    private float volume;
    private boolean fragileFlag;

    @NotEmpty
    private String currentStatus;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "currentwarehouseId")
    private Warehouse currentWarehouse;

    public Shipment(Order order, String trackingNumber, String type,
                    float weight, float volume, boolean fragileFlag,
                    String currentStatus, Warehouse currentWarehouse) {
        this.order = order;
        this.trackingNumber = trackingNumber;
        this.type = type;
        this.weight = weight;
        this.volume = volume;
        this.fragileFlag = fragileFlag;
        this.currentStatus = currentStatus;
        this.currentWarehouse = currentWarehouse;
    }

}
