package com.finance.bank.mappers;

import com.finance.bank.dto.AddressDTO;
import com.finance.bank.model.Address;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AddressToAddressDTO implements Converter<Address, AddressDTO> {
    @Synchronized
    @Nullable
    @Override
    public AddressDTO convert(Address address) {
        final AddressDTO addressDTO = new AddressDTO();

        addressDTO.setCity(address.getCity());
        addressDTO.setLine1(address.getLine1());
        addressDTO.setLine2(address.getLine2());
        addressDTO.setState(address.getState());
        addressDTO.setPinCode(address.getPinCode());

        return addressDTO;
    }
}
