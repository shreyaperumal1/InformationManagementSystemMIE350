package com.group9.postal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tracking_event")
public class TrackingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    private LocalDateTime timestamp = LocalDateTime.now();

    @NotEmpty
    private String status;

    @Nullable
    private String locationText;

    @Nullable
    private String note;

    public TrackingEvent(Shipment shipment, String status,
                         String locationText, String note) {
        this.shipment = shipment;
        this.status = status;
        this.locationText = locationText;
        this.note = note;
    }


}
