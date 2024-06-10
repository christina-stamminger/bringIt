package com.codersnextdoor.bringIt.api.address;

import com.codersnextdoor.bringIt.api.ResponseBody;

public class AddressResponseBody extends ResponseBody {

    private Address address;

    public AddressResponseBody() {

    }

    public AddressResponseBody(Address address) {
        this.address = address;
    }

    // getter and setter

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
