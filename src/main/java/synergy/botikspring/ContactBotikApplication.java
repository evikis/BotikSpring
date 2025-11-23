package synergy.botikspring;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import synergy.botikspring.control.ContactController;
import synergy.botikspring.dto.ContactDto;
import synergy.botikspring.service.ContactServise;
import synergy.botikspring.service.ContactServiseImpl;

import java.util.Arrays;

@EnableCaching
@SpringBootApplication
public class ContactBotikApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactBotikApplication.class, args);
    }
}
