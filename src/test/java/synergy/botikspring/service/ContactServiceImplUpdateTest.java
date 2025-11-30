package synergy.botikspring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import synergy.botikspring.dto.ContactDto;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис для обновления контактов")
class ContactServiceImplUpdateTest {

    @InjectMocks
    private ContactServiceImpl contact;

    void setUp() throws Exception {
        contact.create(new ContactDto(1L, "Alice", "Deo", "Olegovna", "79788885454"));
        contact.create(new ContactDto(2L, "Bob", "Sabvelov", "Denisovich", "79786632121"));
    }

    @Test
    @DisplayName("обновляет контакты")
    void update() throws Exception {
        ContactDto contactDto = new ContactDto(
                1L,
                "Alice",
                "Zurina",
                "Olegovna",
                "79784562321"
        );

        ContactDto result = contact.update(contactDto, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLastName()).isEqualTo("Zurina");
        assertThat(result.getPhone()).isEqualTo("79784562321");

        ContactDto found = contact.findById(1L);
        assertThat(found).isEqualTo(result);

    }
}