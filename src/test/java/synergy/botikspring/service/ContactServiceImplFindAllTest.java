package synergy.botikspring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import synergy.botikspring.dto.ContactDto;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@DisplayName("Сервис обрабатывающий контакты")
class ContactServiceImplFindAllTest {

    @InjectMocks
    private ContactServiceImpl contact;

    @Test
    @DisplayName("ищет все контакты")
    void findAll() {
        List<ContactDto> list = contact.findAll();
        Assertions.assertThat(list).isNotEmpty();
    }
}