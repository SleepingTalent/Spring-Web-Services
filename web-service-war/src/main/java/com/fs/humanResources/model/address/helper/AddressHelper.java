package com.fs.humanResources.model.address.helper;

import com.fs.humanResources.model.address.entities.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressHelper {

    public AddressHelper() {
    }

    public Address findPrimaryAddress(List<Address> addressList) {
        Address primaryAddress = null;

        for (Address address : addressList) {
           if(address.isPrimaryAddress()) {
               primaryAddress = address;
           }
        }
       return primaryAddress;
    }

    public List<Address> convertToList(Address... addresses) {
        List<Address> addressList = new ArrayList();

        for(Address address : addresses ) {
            addressList.add(address);
        }

        return addressList;
    }
}
