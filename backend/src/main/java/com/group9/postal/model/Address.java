package com.group9.postal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    public enum BuildingType {
        HOUSE,
        APARTMENT,
        OFFICE,
        HOTEL,
        WAREHOUSE,
        OTHER
    }

    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    private String aptNum;

    private int streetNum;

    @NotEmpty
    private String streetName;

    public enum StreetType{
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
        LINE,
        HILL
    };

    @NotNull
    @Enumerated(EnumType.STRING)
    private StreetType streetType;

    @NotEmpty
    private String city;

    @NotEmpty
    private String provinceState;

    @NotEmpty
    private String country;

    @NotEmpty
    private String postalZip;

    @NotNull
    private double longitude;

    @NotNull
    private double latitude;

    public Address(Long addressId,
                   BuildingType buildingType,
                   String aptNum,
                   String city,
                   String provinceState,
                   String country,
                   String postalZip,
                   double latitude,
                   double longitude) {
        this.addressId = addressId;
        this.aptNum = aptNum;
        this.city = city;
        this.buildingType = buildingType;
        this.provinceState = provinceState;
        this.country = country;
        this.postalZip = postalZip;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
