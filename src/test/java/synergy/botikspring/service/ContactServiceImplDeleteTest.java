package synergy.botikspring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import synergy.botikspring.dto.ContactDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис удаляющий контакт")
class ContactServiceImplDeleteTest {

    @InjectMocks
    private ContactServiceImpl contact;

    @Test
    @DisplayName("удаляет контакт")
    void delete() throws Exception {
        contact.delete(1L);

        ContactDto found = contact.findById(1L);
        assertThat(found).isNull();

        List<ContactDto> allContacts = contact.findAll();
        assertThat(allContacts).hasSize(1);
        assertThat(allContacts.get(0).getId()).isEqualTo(2L);
    }
}