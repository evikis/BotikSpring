package synergy.botikspring.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import synergy.botikspring.dto.ContactDto;
import synergy.botikspring.myEntity.Contact;
import synergy.botikspring.repository.ContactRepository;
import synergy.botikspring.service.ContactService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactsService;
    private final ContactRepository contactRepository;

    @GetMapping
    public ResponseEntity<List<ContactDto>> getAll() {
        List<ContactDto> contacts = contactsService.findAll();
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> getById(@PathVariable Long id) {
        try {
            ContactDto contact = contactsService.findById(id);
            return ResponseEntity.ok(contact);
        } catch (Exception e) {
            log.error("Error finding contact with id: {}", id, e);
            return ResponseEntity.notFound().build();  // Возвращаем 404 вместо исключения
        }
    }

    @PostMapping
    public ResponseEntity<ContactDto> create(@RequestBody ContactDto dto) {
        try {
            ContactDto saved = contactsService.create(dto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(saved);
        } catch (Exception e){
            log.error("Error creating contact", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDto> update(
            @PathVariable Long id, @RequestBody ContactDto dto) {

        try {
            dto.setId(id);  // Убедимся, что ID установлен
            ContactDto updated = contactsService.update(dto, id);
            return updated != null
                    ? ResponseEntity.ok(updated)
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating contact with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            contactsService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting contact with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/test-transaction")
    @Transactional
    public ResponseEntity<String> testTransaction(@RequestParam boolean shouldFail) {
        // Создаем первый контакт
        Contact contact1 = new Contact();
        contact1.setFirstName("Transaction");
        contact1.setLastName("Test1");
        contact1.setPhone("7999" + System.currentTimeMillis()); // Уникальный телефон
        contactRepository.save(contact1);

        Contact contact2 = new Contact();
        contact2.setFirstName("Transaction");
        contact2.setLastName("Test2");
        contact2.setPhone("7999" + (System.currentTimeMillis() + 1));
        contactRepository.save(contact2);

        if (shouldFail) {
            throw new RuntimeException("Forced transaction rollback for testing");
        }

        return ResponseEntity.ok("Transaction committed. Created IDs: " +
                contact1.getId() + ", " + contact2.getId());
    }
}