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
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private User driver;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @NotEmpty
    private String routeType;

    @Nullable
    private LocalDateTime plannedStartTime;

    @Nullable
    private LocalDateTime plannedEndTime;

    @NotEmpty
    private String routeStatus;

    @OneToMany(mappedBy = "route")
    @Nullable
    private List<RouteStop> stops = new ArrayList<>();

    public Route(User driver, Warehouse warehouse, String routeType,
                 LocalDateTime plannedStartTime, LocalDateTime plannedEndTime,
                 String routeStatus) {
        this.driver = driver;
        this.warehouse = warehouse;
        this.routeType = routeType;
        this.plannedStartTime = plannedStartTime;
        this.plannedEndTime = plannedEndTime;
        this.routeStatus = routeStatus;
    }
}
