package com.codersnextdoor.bringIt.api.address;

import com.codersnextdoor.bringIt.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByAddressId(Long Id);

}
