package synergy.botikspring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import synergy.botikspring.dto.ContactDto;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис для создания контактов")
class ContactServiceImplCreateTest {

    @InjectMocks
    private ContactServiceImpl contactService;


    @Test
    @DisplayName("создаёт контакт")
    void create() throws Exception {
        ContactDto contactDto = new ContactDto(
                99L,
                "Avrora",
                "Volkova",
                "Andreevna",
                "79786753434"
        );

        ContactDto result = contactService.create(contactDto);

        assertThat(result.getId()).isEqualTo(99L);
        assertThat(result.getFirstName()).isEqualTo("Avrora");
        assertThat(result.getLastName()).isEqualTo("Volkova");
        assertThat(result.getMiddleName()).isEqualTo("Andreevna");
        assertThat(result.getPhone()).isEqualTo("79786753434");

        ContactDto found = contactService.findById(99L);
        assertThat(found).isNotNull();
        assertThat(found.getLastName()).isEqualTo("Volkova");
    }
}
