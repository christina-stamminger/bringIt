package com.codersnextdoor.bringIt.api.address.update;

import com.codersnextdoor.bringIt.api.address.Address;
import com.codersnextdoor.bringIt.api.address.AddressRepository;
import com.codersnextdoor.bringIt.api.address.AddressResponseBody;
import com.codersnextdoor.bringIt.api.user.User;
import com.codersnextdoor.bringIt.api.user.UserRepository;
import com.codersnextdoor.bringIt.api.user.UserResponseBody;
import com.codersnextdoor.bringIt.api.user.update.UpdateUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/address")
public class UpdateAddressController {

    @Autowired
    private AddressRepository addressRepository;

    @PutMapping
    public ResponseEntity<AddressResponseBody> update(@RequestBody UpdateAddressDTO updateAddressDTO) {
        if (updateAddressDTO == null || updateAddressDTO.getAddressId() < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Address> optionalAddress = addressRepository.findById(updateAddressDTO.getAddressId());

        if (optionalAddress.isEmpty()) {
            AddressResponseBody response = new AddressResponseBody();
            response.addErrorMessage("Could not find address by ID: " + updateAddressDTO.getAddressId());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Address address = optionalAddress.get();

        if (!StringUtils.isEmpty(updateAddressDTO.getStreetNumber())) {
            address.setStreetNumber(updateAddressDTO.getStreetNumber());
        }

        if (!StringUtils.isEmpty(updateAddressDTO.getPostalCode())) {
            address.setPostalCode(updateAddressDTO.getPostalCode());
        }

        if (!StringUtils.isEmpty(updateAddressDTO.getCity())) {
            address.setCity(updateAddressDTO.getCity());
        }

        addressRepository.save(address);

        AddressResponseBody response = new AddressResponseBody();
        response.setAddress(address);

        return ResponseEntity.ok(response);
    }
}

