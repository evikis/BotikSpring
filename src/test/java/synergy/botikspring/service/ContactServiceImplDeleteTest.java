package synergy.botikspring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ContactRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис удаляющий контакт")
class ContactServiceImplDeleteTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    @Test
    @DisplayName("удаляет контакт")
    void delete() throws Exception {
        when(contactRepository.existsById(1L)).thenReturn(true);
        doNothing().when(contactRepository).deleteById(1L);

        contactService.delete(1L);

        verify(contactRepository).existsById(1L);
        verify(contactRepository).deleteById(1L);
    }
}