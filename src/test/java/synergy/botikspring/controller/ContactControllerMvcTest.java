package synergy.botikspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import synergy.botikspring.control.ContactController;
import synergy.botikspring.dto.ContactDto;
import synergy.botikspring.myEntity.Contact;
import synergy.botikspring.repository.ContactRepository;
import synergy.botikspring.service.ContactService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ContactControllerMvcTest {

    private MockMvc mockMvc;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @Mock
    private ContactRepository contactRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(contactService).delete(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/contacts/1"))
                .andExpect(status().isNoContent());

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
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Alice"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Bob"));
    }

    @Test
    void testTransactionEndpointExists() throws Exception {
        mockMvc.perform(post("/api/contacts/test-transaction")
                        .param("shouldFail", "false"))
                .andExpect(status().isOk());
    }
}