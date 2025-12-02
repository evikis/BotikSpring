package synergy.botikspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = {"synergy.botikspring.myEntity", "synergy.botikspring"})
@EnableJpaRepositories("synergy.botikspring.repository")
@ComponentScan(basePackages = {"synergy.botikspring", "synergy.botikspring.repository"})
@EnableTransactionManagement
public class ContactBotikApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactBotikApplication.class, args);
    }
}
