package com.codersnextdoor.bringIt.api.address.create;

import com.codersnextdoor.bringIt.api.address.Address;
import com.codersnextdoor.bringIt.api.address.AddressRepository;
import com.codersnextdoor.bringIt.api.address.AddressResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/address/")
public class CreateAddressController {

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping
    public ResponseEntity<AddressResponseBody> create(@RequestBody CreateAddressDTO createAddressDTO) {
        AddressResponseBody body = new AddressResponseBody();

        if (createAddressDTO == null || StringUtils.isEmpty(createAddressDTO.getPostalCode())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Check if address exists based on relevant fields
        Optional<Address> optionalAddress = addressRepository.findByStreetNumberAndPostalCodeAndCity(
                createAddressDTO.getStreetNumber(),
                createAddressDTO.getPostalCode(),
                createAddressDTO.getCity()

        );

        if (optionalAddress.isPresent()) {
            body.addErrorMessage("Address already exists");
            return new ResponseEntity<>(body, HttpStatus.CONFLICT);
        }

        // Create a new address object
        Address address = new Address(
                createAddressDTO.getStreetNumber(),
                createAddressDTO.getPostalCode(),
                createAddressDTO.getCity()
        );

        // Save the new address to the repository
        try {
            addressRepository.save(address);
        } catch (Throwable t) {
            body.addErrorMessage("An error occurred");
            body.addErrorMessage(t.getMessage());
            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Return created address in response body
        body.setAddress(address);
        return ResponseEntity.ok(body);
    }
}
