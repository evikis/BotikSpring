package synergy.botikspring.service;

import synergy.botikspring.myEntity.Contact;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synergy.botikspring.repository.ContactRepository;
import synergy.botikspring.dto.ContactDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис для обновления контактов")
class ContactServiceImplUpdateTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

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

        when(contactRepository.existsById(1L)).thenReturn(true);
        when(contactRepository.save(any())).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return (Contact) args[0];
        });

        ContactDto result = contactService.update(contactDto, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLastName()).isEqualTo("Zurina");
        assertThat(result.getPhone()).isEqualTo("79784562321");
    }
}