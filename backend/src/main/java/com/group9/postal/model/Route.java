package com.group9.postal.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    @ManyToOne
    @JoinColumn(name = "driverId")
    private User driver;

    @ManyToOne
    @JoinColumn(name = "warehouseId")
    private Warehouse warehouse;

    @Nullable
    private LocalDateTime plannedStartTime;

    @Nullable
    private LocalDateTime plannedEndTime;

    public enum Status {
        SCHEDULED,
        IN_PROGRESS,
        COMPLETE
    }

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status routeStatus;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RouteStop> stops = new ArrayList<>();

    public Route(User driver, Warehouse warehouse, String routeType,
                 LocalDateTime plannedStartTime, LocalDateTime plannedEndTime,
                 Status routeStatus, List<RouteStop> stops) {
        this.driver = driver;
        this.warehouse = warehouse;
        this.plannedStartTime = plannedStartTime;
        this.plannedEndTime = plannedEndTime;
        this.routeStatus = routeStatus;
        this.stops = stops;
    }

}
