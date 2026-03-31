package com.group9.postal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    public enum BuildingType {
        HOUSE,
        APARTMENT,
        OFFICE,
        HOTEL,
        WAREHOUSE,
        OTHER
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    private String aptNum;

    private int streetNum;

    public enum StreetType {
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
    }

    @NotBlank
    private String streetName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StreetType streetType;

    @NotBlank
    private String city;

    @NotBlank
    private String provinceState;

    @NotBlank
    private String country;

    @NotBlank
    private String postalZip;

    @NotNull
    private double longitude;

    @NotNull
    private double latitude;

    public Address(Long addressId,
                   BuildingType buildingType,
                   String aptNum,
                   int streetNum,
                   String streetName,
                   StreetType streetType,
                   String city,
                   String provinceState,
                   String country,
                   String postalZip,
                   double latitude,
                   double longitude) {

        this.addressId = addressId;
        this.buildingType = buildingType;
        this.aptNum = aptNum;
        this.streetNum = streetNum;
        this.streetName = streetName;
        this.streetType = streetType;
        this.city = city;
        this.provinceState = provinceState;
        this.country = country;
        this.postalZip = postalZip;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}