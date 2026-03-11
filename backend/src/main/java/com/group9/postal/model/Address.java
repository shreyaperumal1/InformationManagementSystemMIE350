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
@Table(name = "address")

public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long addressId;

    @NotEmpty
    private enum BuildingType {
        HOUSE,
        APARTMENT,
        OFFICE,
        HOTEL,
        WAREHOUSE,
        OTHER
    };

    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    private String aptNum;

    @NotEmpty
    private int streetNum;

    @NotEmpty
    private String streetName;

    @NotEmpty
    private enum StreetType{
        DRIVE,
        AVENUE,
        BOULEVARD,
        STREET,
        PLACE,
        CRESCENT,
        COURT,
        LANE,
        WALK,
        WAY,
        ROAD,
        LINE
    };

    @Enumerated(EnumType.STRING)
    private StreetType streetType;

    @NotEmpty
    private String city;

    @NotEmpty
    private String provinceOrState;

    @NotEmpty
    private String country;

    @NotEmpty
    private String postalOrZip;

    public Address(Long addressId,
                   BuildingType buildingType,
                   String aptNum,
                   int streetNum,
                   String streetName,
                   StreetType streetType,
                   String city,
                   String provinceOrState,
                   String country,
                   String postalOrZip) {

        this.addressId = addressId;
        this.buildingType = buildingType;
        this.aptNum = aptNum;
        this.streetNum = streetNum;
        this.streetName = streetName;
        this.streetType = streetType;
        this.city = city;
        this.provinceOrState = provinceOrState;
        this.country = country;
        this.postalOrZip = postalOrZip;
    }
}
