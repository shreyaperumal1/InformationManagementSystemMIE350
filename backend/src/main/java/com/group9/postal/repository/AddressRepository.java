package com.group9.postal.repository;

import com.group9.postal.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCity(String city);
    List<Address> findByPostalZip(String postalZip);
    List<Address> findByProvinceState(String provinceState);
}
