package com.group9.postal.model;

import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.group9.postal.model.Address;

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
    @JoinColumn(name = "routeId")
    @JsonBackReference
    private Route route;

    private int stopSequence;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stopAddress")
    private Address address;

    private enum StopType {
        PICKUP,
        DELIVERY
    };

    @Enumerated(EnumType.STRING)
    private StopType stopType;

    @Nullable
    private LocalDateTime plannedTime;

    @Nullable
    private LocalDateTime completedTime;

    @OneToMany(mappedBy = "routeStop")
    @JsonManagedReference
    private List<StopShipment> stopShipments = new ArrayList<>();

    public RouteStop(Route route, int stopSequence, Address address,
                     StopType stopType, LocalDateTime plannedTime) {
        this.route = route;
        this.stopSequence = stopSequence;
        this.address = address;
        this.stopType = stopType;
        this.plannedTime = plannedTime;
    }
}
