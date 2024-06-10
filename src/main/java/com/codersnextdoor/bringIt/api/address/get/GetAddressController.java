package com.codersnextdoor.bringIt.api.address.get;

import com.codersnextdoor.bringIt.api.address.Address;
import com.codersnextdoor.bringIt.api.address.AddressRepository;
import com.codersnextdoor.bringIt.api.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAddressController {

    @Autowired
    public AddressRepository addressRepository;

    @GetMapping("/api/addresses/")
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return ResponseEntity.ok(addresses);
    }

}
