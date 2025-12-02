package synergy.botikspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergy.botikspring.myEntity.Contact;
import synergy.botikspring.repository.ContactRepository;
import synergy.botikspring.dto.ContactDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private ContactDto toDto(Contact contact) {
        return ContactDto.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .middleName(contact.getMiddleName())
                .phone(contact.getPhone())
                .build();
    }

    private Contact toEntity(ContactDto dto) {
        Contact contact = new Contact();
        contact.setId(dto.getId());
        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
        contact.setMiddleName(dto.getMiddleName());
        contact.setPhone(dto.getPhone());
        return contact;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDto> findAll() {
        return contactRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ContactDto findById(Long id) {
        return contactRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    @Override
    @Transactional
    public ContactDto create(ContactDto dto) {
        if (contactRepository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Phone already exists");
        }
        Contact contact = toEntity(dto);
        contact.setId(null); // ID будет сгенерирован БД
        Contact saved = contactRepository.save(contact);
        return toDto(saved);
    }

    @Override
    @Transactional
    public ContactDto update(ContactDto dto, Long id) {
        if (!contactRepository.existsById(id)) {
            return null;
        }
        Contact contact = toEntity(dto);
        contact.setId(id);
        Contact updated = contactRepository.save(contact);
        return toDto(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            log.info("Контакт с id {} удален", id);
        } else {
            throw new RuntimeException("Контакт не найден");
        }
    }
}