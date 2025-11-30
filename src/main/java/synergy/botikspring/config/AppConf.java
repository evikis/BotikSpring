package synergy.botikspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import synergy.botikspring.service.ContactService;
import synergy.botikspring.service.ContactServiceImpl;

@Configuration
public class AppConf {

    @Bean
    public ContactService contactServise() {
        return new ContactServiceImpl();
    }
}
