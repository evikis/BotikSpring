package synergy.botikspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import synergy.botikspring.service.ContactServise;

@SpringBootApplication
public class ContactBotikApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactBotikApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ContactServise contactServise) {
        return args -> {
            System.out.println("Список контактов:");
            contactServise.findAll().forEach(System.out::println);
        };
    }
}
