package synergy.botikspring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import synergy.botikspring.dto.ContactDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService {

    private final List<ContactDto> contacts = new ArrayList<>();

    public ContactServiceImpl() {
        log.info("Initializing ContactServiceImpl with test data");
        contacts.add(new ContactDto(1L, "Alice", "Deo", "Olegovna", "79788885454"));
        contacts.add(new ContactDto(2L, "Bob", "Sabvelov", "Denisovich", "79786632121"));
    }

    @Override
    public List<ContactDto> findAll() {
        return contacts;

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
        if (dto.getId() != null) {
            if (contacts.stream().anyMatch(c -> Objects.equals(c.getId(), dto.getId()))) {
                throw new IllegalArgumentException("Contact with ID " + dto.getId() + " already exists");
            }
        } else {
            long newId = contacts.stream()
                    .mapToLong(ContactDto::getId)
                    .max()
                    .orElse(0L) + 1;
            dto.setId(newId);
        }

        contacts.add(dto);
        return dto;
    }

    @Override
    public ContactDto update(ContactDto dto, Long id) throws Exception {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId().equals(id)) {
                contacts.set(i, dto);
                return dto;
            }
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        contacts.removeIf(c -> c.getId() != null && c.getId().equals(id));
    }
}
