package synergy.botikspring.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergy.botikspring.dto.ContactDto;
import synergy.botikspring.service.ContactServise;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Qualifier("contactServiseImpl")
    @Autowired
    private ContactServise contactsService;

    public List<ContactDto> findAll() {
        return new ArrayList<>(contactsService.findAll()); // копируем, чтобы избежать ConcurrentModificationException
    }

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
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<ContactDto> create(@PathVariable @RequestBody ContactDto dto) {
        try {
            ContactDto saved = contactsService.create(dto);
            return ResponseEntity
                    .created(URI.create("/api/contacts/" + saved.getId()))
                    .body(saved);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDto> update(
            @PathVariable Long id, @RequestBody ContactDto dto) {

        dto.setId(id);
        try {
            ContactDto updated = contactsService.update(dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ContactDto> delete(@PathVariable Long id) {
        contactsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

