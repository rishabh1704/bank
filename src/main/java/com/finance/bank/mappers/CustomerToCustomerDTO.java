package com.finance.bank.mappers;

import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.model.Customer;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CustomerToCustomerDTO implements Converter<Customer, CustomerDTO> {

    private final AddressToAddressDTO addressToAddressDTO;
    private final ContactToContactDTO contactToContactDTO;

    public CustomerToCustomerDTO(AddressToAddressDTO addressToAddressDTO, ContactToContactDTO contactToContactDTO) {
        this.addressToAddressDTO = addressToAddressDTO;
        this.contactToContactDTO = contactToContactDTO;
    }

    @Synchronized
    @Nullable
    @Override
    public CustomerDTO convert(Customer customer) {
        final CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setCustomerType(customer.getCustomerType().name());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setIdentificationNumber(customer.getIdentificationNumber());

        customerDTO.setAddress(addressToAddressDTO.convert(customer.getAddress()));
        customerDTO.setContact(contactToContactDTO.convert(customer.getContact()));

        return customerDTO;
    }
}
