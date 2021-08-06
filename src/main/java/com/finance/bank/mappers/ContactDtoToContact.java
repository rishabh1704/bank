package com.finance.bank.mappers;

import com.finance.bank.dto.ContactDTO;
import com.finance.bank.model.Contact;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContactDtoToContact implements Converter<ContactDTO, Contact> {

    @Override
    public Contact convert(ContactDTO contactDTO) {
        final Contact contact = new Contact();

        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());
//        user to be set manually

        return contact;
    }
}
