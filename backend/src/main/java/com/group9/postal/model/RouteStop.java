package com.group9.postal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "route_stop")
public class RouteStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stopId;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    private int stopSequence;

    @NotEmpty
    private String stopAddress;

    @NotEmpty
    private String stopType;

    @Nullable
    private LocalDateTime plannedTime;

    @Nullable
    private LocalDateTime completedTime;

    @OneToMany(mappedBy = "routeStop")
    @Nullable
    private List<StopShipment> stopShipments = new ArrayList<>();

    public RouteStop(Route route, int stopSequence, String stopAddress,
                     String stopType, LocalDateTime plannedTime) {
        this.route = route;
        this.stopSequence = stopSequence;
        this.stopAddress = stopAddress;
        this.stopType = stopType;
        this.plannedTime = plannedTime;
    }
}
