package com.group9.postal.controller;

import com.group9.postal.controller.exceptions.AddressNotFoundException;
import com.group9.postal.model.Address;
import com.group9.postal.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
public class AddressController {

    @Autowired
    private final AddressRepository addressRepository;

    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @GetMapping("/addresses")
    List<Address> retrieveAllAddresses() {
        return addressRepository.findAll();
    }

    @GetMapping("/addresses/{id}")
    Address retrieveAddress(@PathVariable("id") Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
    }

    @GetMapping("/addresses/city/{city}")
    List<Address> retrieveAddressesByCity(@PathVariable("city") String city) {
        return addressRepository.findByCity(city);
    }

    @GetMapping("/addresses/postal/{postalZip}")
    List<Address> retrieveAddressesByPostal(@PathVariable("postalZip") String postalZip) {
        return addressRepository.findByPostalZip(postalZip);
    }

    @PostMapping("/addresses")
    Address createAddress(@RequestBody Address newAddress) {
        return addressRepository.save(newAddress);
    }

    @PutMapping("/addresses/{id}")
    Address updateAddress(@RequestBody Address newAddress, @PathVariable("id") Long addressId) {
        return addressRepository.findById(addressId)
                .map(address -> {
                    address.setBuildingType(newAddress.getBuildingType());
                    address.setAptNum(newAddress.getAptNum());
                    address.setStreetNum(newAddress.getStreetNum());
                    address.setStreetName(newAddress.getStreetName());
                    address.setStreetType(newAddress.getStreetType());
                    address.setCity(newAddress.getCity());
                    address.setProvinceState(newAddress.getProvinceState());
                    address.setCountry(newAddress.getCountry());
                    address.setPostalZip(newAddress.getPostalZip());
                    return addressRepository.save(address);
                })
                .orElseGet(() -> {
                    newAddress.setAddressId(addressId);
                    return addressRepository.save(newAddress);
                });
    }

    @DeleteMapping("/addresses/{id}")
    void deleteAddress(@PathVariable("id") Long addressId) {
        addressRepository.deleteById(addressId);
    }
}