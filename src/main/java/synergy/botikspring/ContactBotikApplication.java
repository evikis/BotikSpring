package synergy.botikspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"myEntity", "synergy.botikspring"})
@EnableJpaRepositories("repository")
@ComponentScan(basePackages = {"synergy.botikspring", "repository"})
public class ContactBotikApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactBotikApplication.class, args);
    }
}
