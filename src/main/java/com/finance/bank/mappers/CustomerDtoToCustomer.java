package com.finance.bank.mappers;

import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.model.Address;
import com.finance.bank.model.Customer;
import com.finance.bank.model.CustomerType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoToCustomer implements Converter<CustomerDTO, Customer> {

    private final AddressDtoToAddress addressDtoToAddress;
    private final ContactDtoToContact contactDtoToContact;

    public CustomerDtoToCustomer(AddressDtoToAddress addressDtoToAddress, ContactDtoToContact contactDtoToContact) {
        this.addressDtoToAddress = addressDtoToAddress;
        this.contactDtoToContact = contactDtoToContact;
    }

    @Override
    public Customer convert(CustomerDTO customerDTO) {
        final Customer customer = new Customer();

        customer.setCustomerType(CustomerType.valueOf(customerDTO.getCustomerType()));
        customer.setIdentificationNumber(customerDTO.getIdentificationNumber());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());

        customer.setAddress(this.addressDtoToAddress.convert(customerDTO.getAddress()));
        customer.setContact(this.contactDtoToContact.convert(customerDTO.getContact()));
//        account is not created yet here. too much responsibility for this dto.

        return customer;
    }
}
