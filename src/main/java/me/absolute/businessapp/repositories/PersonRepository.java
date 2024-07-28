package me.absolute.businessapp.repositories;

import me.absolute.businessapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    public Person findByUsername(String email);

    public Optional<Person> findClientByUsernameAndTelephoneNumber(String username, String telephoneNumber);

}
