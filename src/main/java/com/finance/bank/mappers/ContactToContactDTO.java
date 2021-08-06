package com.finance.bank.mappers;

import com.finance.bank.dto.ContactDTO;
import com.finance.bank.model.Contact;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ContactToContactDTO implements Converter<Contact, ContactDTO> {
    @Synchronized
    @Nullable
    @Override
    public ContactDTO convert(Contact contact) {
        final ContactDTO contactDTO = new ContactDTO();

        contactDTO.setEmail(contact.getEmail());
        contactDTO.setPhone(contact.getPhone());
        return contactDTO;
    }
}
