package com.finance.bank.mappers;

import com.finance.bank.dto.AddressDTO;
import com.finance.bank.model.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressDtoToAddress implements Converter<AddressDTO, Address> {

    @Override
    public Address convert(AddressDTO addressDTO) {
        final Address address = new Address();

        address.setState(addressDTO.getState());
        address.setCity(addressDTO.getCity());
        address.setLine1(addressDTO.getLine1());
        address.setLine2(addressDTO.getLine2());
        address.setPinCode(addressDTO.getPinCode());

        return address;
    }
}
