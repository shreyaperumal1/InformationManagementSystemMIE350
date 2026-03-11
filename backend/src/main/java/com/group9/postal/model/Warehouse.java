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
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long warehouseId;

    @NotEmpty
    private String name;

    @NotEmpty
    @OneToOne
    @JoinColumn(name = "addressId")
    private Address address;

    private int capacityMaxPackages;

    private String region;

    public Warehouse(String name, Address address, String postalCode, int capacityMaxPackages, String region){
        this.name = name;
        this.address = address;
        this.capacityMaxPackages = capacityMaxPackages;
        this.region = region;
    }
}
