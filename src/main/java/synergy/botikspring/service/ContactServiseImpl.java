package synergy.botikspring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import synergy.botikspring.dto.ContactDto;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ContactServiseImpl implements ContactServise {

    private final List<ContactDto> contacts = new ArrayList<>();

    public ContactServiseImpl() {
        contacts.add(new ContactDto(1L, "Alice", "Deo", "Olegovna", "79788885454"));
        contacts.add(new ContactDto(2L, "Bob", "Sabvelov", "Denisovich", "79786632121"));
    }

    @Override
    public List<ContactDto> findAll() {
        return contacts; // Возвращает все контакты из памяти

    }

    @Override
    public ContactDto findById(Long id) throws Exception {
        return contacts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public ContactDto create(ContactDto dto) throws Exception {
        contacts.add(dto);
        return dto;
    }

    @Override
    public ContactDto update(ContactDto dto, Long id) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId().equals(id)) {
                contacts.set(i, dto);
            }
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        contacts.removeIf(c -> c.getId().equals(id));
    }

    @Override
    public ContactDto update(ContactDto dto) {
        return null;
    }
}
