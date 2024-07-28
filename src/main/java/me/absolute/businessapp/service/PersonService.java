package me.absolute.businessapp.service;

import lombok.RequiredArgsConstructor;
import me.absolute.businessapp.DTO.PersonDTO;
import me.absolute.businessapp.model.Person;
import me.absolute.businessapp.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public boolean hasClientWithNameAndTelephoneNumber(Person person) {
        Optional<Person> clientOptional = personRepository.
                findClientByUsernameAndTelephoneNumber(person.getUsername(), person.getTelephoneNumber());
        return clientOptional.isPresent();
    }

    @Transactional
    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Person buildFromDTO(PersonDTO personDTO) {
        Optional<Person> clientOptional = personRepository.
                findClientByUsernameAndTelephoneNumber(personDTO.getUsername(), personDTO.getTelephoneNumber());
        return clientOptional.orElse(null);
    }

}
