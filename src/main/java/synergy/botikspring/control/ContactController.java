package synergy.botikspring.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergy.botikspring.dto.ContactDto;
import synergy.botikspring.service.ContactService;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Qualifier("contactServiceImpl")
    @Autowired
    private ContactService contactsService;

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
    public ResponseEntity<ContactDto> create(@RequestBody ContactDto dto) {
        try {
            ContactDto saved = contactsService.create(dto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    //.created(URI.create("/api/contacts/" + saved.getId()))
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
            ContactDto updated = contactsService.update(dto, id);
            return updated != null
                    ? ResponseEntity.ok(updated)
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

