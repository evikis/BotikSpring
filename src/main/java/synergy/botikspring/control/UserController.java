package synergy.botikspring.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synergy.botikspring.dto.UserDto;

import java.util.Random;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final Random random = new Random();
    private final String[] names = {"Alice", "Peter", "Asya", "Lera"};
    private final String[] surnames = {"Smith", "John", "Engelgartova", "Cruel"};

    @GetMapping("/random")
    public UserDto getRandomUser() {
        String name = names[random.nextInt(names.length)];
        String surname = surnames[random.nextInt(surnames.length)];
        int age = random.nextInt(30) + 18;
        return new UserDto(name, surname, age);
    }
}
