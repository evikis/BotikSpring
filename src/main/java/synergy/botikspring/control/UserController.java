package synergy.botikspring.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("http://localhost:9090/api/user")
public class UserController {

    private final Random random = new Random();
    private final String[] names = {"Alice", "Peter", "Asya", "Lera"};
    private final String[] surnames = {"Smith", "John", "Engelgartova", "Cruel"};

    @GetMapping("/random")
    public String getRandomUser() {
        String name = names[random.nextInt(names.length)];
        String surname = surnames[random.nextInt(surnames.length)];
        int age = random.nextInt(30) + 18;
        return String.format("%s %s, age: %d", name, surname, age);
    }
}
