package com.group9.postal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @NotEmpty
    @Column(unique=true)
    private String plate;

    @NotEmpty
    private String type;

    private float capacityWeight;
    private float capacityVolume;

    public Vehicle(String plate, String type, float capacityWeight, float capacityVolume) {
        this.plate = plate;
        this.type = type;
        this.capacityWeight = capacityWeight;
        this.capacityVolume = capacityVolume;
    }

}
