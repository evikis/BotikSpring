package synergy.botikspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import synergy.botikspring.service.ContactServise;
import synergy.botikspring.service.ContactServiseImpl;

@Configuration
public class AppConf {

    @Bean
    public ContactServise contactServise() {
        return new ContactServiseImpl();
    }
}
