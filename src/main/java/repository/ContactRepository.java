package repository;

import myEntity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByPhone(String phone);

    List<Contact> findByLastName(String lastName);

    List<Contact> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT c FROM Contact c WHERE c.firstName LIKE %:name% OR c.lastName LIKE %:name%")
    List<Contact> findByNameContaining(@Param("name") String name);

    boolean existsByPhone(String phone);
}