package synergy.botikspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import synergy.botikspring.control.ContactController;
import synergy.botikspring.dto.ContactDto;
import synergy.botikspring.service.ContactService;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactControllerMvcTest {

    private MockMvc mockMvc;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(contactService).delete(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/contacts/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(contactService).delete(1L);
    }

    @Test
    void testGetAll() throws Exception {
        List<ContactDto> contacts = List.of(
                new ContactDto(1L, "Alice", "Deo", "Olegovna", "79788885454"),
                new ContactDto(2L, "Bob", "Sabvelov", "Denisovich", "79786632121")
        );

        when(contactService.findAll()).thenReturn(contacts);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contacts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Alice"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Bob"));
    }
}