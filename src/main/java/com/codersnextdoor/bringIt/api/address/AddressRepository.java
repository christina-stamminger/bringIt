package com.codersnextdoor.bringIt.api.address;

import com.codersnextdoor.bringIt.api.user.User;
import jakarta.transaction.Transactional;
import org.hibernate.mapping.Set;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    //List<Address> findAddressesBy(Long Id);

    Optional<Address> findByStreetNumberAndPostalCodeAndCity(String streetNumber, String postalCode, String city);

    Optional<Address> findById(Long addressId);


    //Delete Addresses that are not associated with any user:
    @Modifying
    @Transactional
    @Query("DELETE FROM Address a WHERE a.addressId NOT IN (SELECT u.address.addressId FROM User u)")
    void deleteOrphanedAddresses();

}
