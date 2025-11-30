package synergy.botikspring.service;

import myEntity.Contact;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ContactRepository;
import synergy.botikspring.dto.ContactDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис для создания контактов")
class ContactServiceImplCreateTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    @Test
    @DisplayName("создаёт контакт")
    void create() throws Exception {
        ContactDto contactDto = new ContactDto(
                null,
                "Avrora",
                "Volkova",
                "Andreevna",
                "79786753434"
        );

        when(contactRepository.existsByPhone("79786753434")).thenReturn(false);
        when(contactRepository.save(any())).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            var contact = (Contact) args[0];
            contact.setId(99L);
            return contact;
        });

        ContactDto result = contactService.create(contactDto);

        assertThat(result.getId()).isEqualTo(99L);
        assertThat(result.getFirstName()).isEqualTo("Avrora");
        assertThat(result.getLastName()).isEqualTo("Volkova");
        assertThat(result.getMiddleName()).isEqualTo("Andreevna");
        assertThat(result.getPhone()).isEqualTo("79786753434");
    }
}