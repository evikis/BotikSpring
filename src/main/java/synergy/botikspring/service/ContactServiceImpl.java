package synergy.botikspring.service;

import myEntity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ContactRepository;
import synergy.botikspring.dto.ContactDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ContactDto> findAll() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto findById(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isPresent()) {
            return convertToDto(contact.get());
        } else {
            throw new RuntimeException("Contact not found with id: " + id);
        }
    }

    @Override
    public ContactDto create(ContactDto contactDto) {
        // Проверка на существующий телефон
        if (contactRepository.existsByPhone(contactDto.getPhone())) {
            throw new RuntimeException("Contact with phone " + contactDto.getPhone() + " already exists");
        }

        Contact contact = convertToEntity(contactDto);
        Contact savedContact = contactRepository.save(contact);
        return convertToDto(savedContact);
    }

    @Override
    public ContactDto update(ContactDto contactDto, Long id) {
        // Проверяем существование контакта
        if (!contactRepository.existsById(id)) {
            throw new RuntimeException("Contact not found with id: " + id);
        }

        Contact contact = convertToEntity(contactDto);
        contact.setId(id);
        Contact updatedContact = contactRepository.save(contact);
        return convertToDto(updatedContact);
    }

    @Override
    public void delete(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new RuntimeException("Contact not found with id: " + id);
        }
        contactRepository.deleteById(id);
    }

    private ContactDto convertToDto(Contact contact) {
        ContactDto dto = new ContactDto();
        dto.setId(contact.getId());
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setMiddleName(contact.getMiddleName());
        dto.setPhone(contact.getPhone());
        return dto;
    }

    private Contact convertToEntity(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setId(contactDto.getId());
        contact.setFirstName(contactDto.getFirstName());
        contact.setLastName(contactDto.getLastName());
        contact.setMiddleName(contactDto.getMiddleName());
        contact.setPhone(contactDto.getPhone());
        return contact;
    }
}