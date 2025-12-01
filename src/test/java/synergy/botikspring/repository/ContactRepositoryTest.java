package synergy.botikspring.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import synergy.botikspring.myEntity.Contact;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Тесты для ContactRepository")
class ContactRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactRepository contactRepository;

    private Contact contact1;
    private Contact contact2;
    private Contact contact3;

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();

        contact1 = new Contact();
        contact1.setFirstName("Иван");
        contact1.setLastName("Иванов");
        contact1.setMiddleName("Иванович");
        contact1.setPhone("79990001111");

        contact2 = new Contact();
        contact2.setFirstName("Петр");
        contact2.setLastName("Петров");
        contact2.setMiddleName("Петрович");
        contact2.setPhone("79990002222");

        contact3 = new Contact();
        contact3.setFirstName("Мария");
        contact3.setLastName("Иванова");
        contact3.setMiddleName("Сергеевна");
        contact3.setPhone("79990003333");

        entityManager.persist(contact1);
        entityManager.persist(contact2);
        entityManager.persist(contact3);
        entityManager.flush();
    }

    @Test
    @DisplayName("должен найти контакт по ID")
    void shouldFindContactById() {
        Optional<Contact> found = contactRepository.findById(contact1.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Иван");
        assertThat(found.get().getLastName()).isEqualTo("Иванов");
        assertThat(found.get().getPhone()).isEqualTo("79990001111");
    }

    @Test
    @DisplayName("должен найти все контакты")
    void shouldFindAllContacts() {
        List<Contact> contacts = contactRepository.findAll();

        assertThat(contacts).hasSize(3);
        assertThat(contacts).extracting(Contact::getFirstName)
                .containsExactlyInAnyOrder("Иван", "Петр", "Мария");
    }

    @Test
    @DisplayName("должен сохранить новый контакт")
    void shouldSaveNewContact() {
        Contact newContact = new Contact();
        newContact.setFirstName("Анна");
        newContact.setLastName("Сидорова");
        newContact.setMiddleName("Алексеевна");
        newContact.setPhone("79990004444");

        Contact saved = contactRepository.save(newContact);
        List<Contact> allContacts = contactRepository.findAll();

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getFirstName()).isEqualTo("Анна");
        assertThat(allContacts).hasSize(4);
    }

    @Test
    @DisplayName("должен обновить существующий контакт")
    void shouldUpdateContact() {
        Contact contact = contactRepository.findById(contact1.getId()).get();
        contact.setFirstName("Обновленный");
        contact.setPhone("79995556677");

        Contact updated = contactRepository.save(contact);
        Contact found = contactRepository.findById(contact1.getId()).get();

        assertThat(updated.getFirstName()).isEqualTo("Обновленный");
        assertThat(updated.getPhone()).isEqualTo("79995556677");
        assertThat(found.getFirstName()).isEqualTo("Обновленный");
    }

    @Test
    @DisplayName("должен удалить контакт по ID")
    void shouldDeleteContactById() {
        contactRepository.deleteById(contact1.getId());
        List<Contact> contacts = contactRepository.findAll();

        assertThat(contacts).hasSize(2);
        assertThat(contactRepository.findById(contact1.getId())).isEmpty();
    }

    @Test
    @DisplayName("должен найти контакт по номеру телефона")
    void shouldFindByPhone() {
        Optional<Contact> found = contactRepository.findByPhone("79990002222");

        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Петр");
        assertThat(found.get().getLastName()).isEqualTo("Петров");
    }

    @Test
    @DisplayName("должен вернуть пустой результат при поиске несуществующего телефона")
    void shouldReturnEmptyWhenPhoneNotFound() {
        Optional<Contact> found = contactRepository.findByPhone("00000000000");

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("должен найти контакты по фамилии")
    void shouldFindByLastName() {
        List<Contact> found = contactRepository.findByLastName("Иванов");

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getFirstName()).isEqualTo("Иван");
    }

    @Test
    @DisplayName("должен найти контакты по имени и фамилии")
    void shouldFindByFirstNameAndLastName() {
        List<Contact> found = contactRepository.findByFirstNameAndLastName("Иван", "Иванов");

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getPhone()).isEqualTo("79990001111");
    }

    @Test
    @DisplayName("должен найти контакты по части имени или фамилии")
    void shouldFindByNameContaining() {
        List<Contact> found = contactRepository.findByNameContaining("Иван");

        assertThat(found).hasSize(2); // Иван Иванов и Мария Иванова
        assertThat(found).extracting(Contact::getFirstName)
                .containsExactlyInAnyOrder("Иван", "Мария");
    }

    @Test
    @DisplayName("должен проверить существование телефона")
    void shouldCheckIfPhoneExists() {
        boolean exists = contactRepository.existsByPhone("79990001111");
        boolean notExists = contactRepository.existsByPhone("00000000000");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("должен проверить существование по ID")
    void shouldCheckIfExistsById() {
        boolean exists = contactRepository.existsById(contact1.getId());
        boolean notExists = contactRepository.existsById(999L);

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("должен найти контакты с одинаковой фамилией")
    void shouldFindMultipleContactsWithSameLastName() {
        Contact anotherIvanov = new Contact();
        anotherIvanov.setFirstName("Сергей");
        anotherIvanov.setLastName("Иванов");
        anotherIvanov.setPhone("79990004444");
        entityManager.persist(anotherIvanov);
        entityManager.flush();

        List<Contact> ivanovs = contactRepository.findByLastName("Иванов");

        assertThat(ivanovs).hasSize(2);
        assertThat(ivanovs).extracting(Contact::getFirstName)
                .containsExactlyInAnyOrder("Иван", "Сергей");
    }

    @Test
    @DisplayName("должен вернуть пустой список при поиске несуществующей фамилии")
    void shouldReturnEmptyListForNonExistentLastName() {
        List<Contact> found = contactRepository.findByLastName("Несуществующая");

        assertThat(found).isEmpty();
    }
}