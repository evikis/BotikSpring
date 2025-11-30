package synergy.botikspring.service;

import myEntity.Contact;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ContactRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис обрабатывающий контакты")
class ContactServiceImplFindAllTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    @Test
    @DisplayName("ищет все контакты")
    void findAll() {
        Contact contact1 = new Contact("Alice", "Smith", "Marie", "1234567890");
        contact1.setId(1L);
        Contact contact2 = new Contact("Bob", "Johnson", "Lee", "0987654321");
        contact2.setId(2L);

        when(contactRepository.findAll()).thenReturn(List.of(contact1, contact2));

        var list = contactService.findAll();
        assertThat(list).isNotEmpty();
        assertThat(list).hasSize(2);
    }
}