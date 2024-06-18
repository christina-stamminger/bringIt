package com.codersnextdoor.bringIt.api.address;

import com.codersnextdoor.bringIt.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    //List<Address> findAddressesBy(Long Id);

    Optional<Address> findByStreetNumberAndPostalCodeAndCity(String streetNumber, String postalCode, String city);

    Optional<Address> findById(Long addressId);

}
