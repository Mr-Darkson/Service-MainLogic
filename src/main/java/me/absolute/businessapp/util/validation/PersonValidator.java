package me.absolute.businessapp.util.validation;

import lombok.AllArgsConstructor;
import lombok.Setter;
import me.absolute.businessapp.model.Person;
import me.absolute.businessapp.model.Ticket;
import me.absolute.businessapp.service.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Setter
@AllArgsConstructor
@Component

public class PersonValidator implements Validator {


    private final PersonService personService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {}

    public boolean hasAccessToCreateTicket(Person person) {
        List<Ticket> clientTickets = person.getTickets()
                .stream().sorted(Comparator.comparing(Ticket::getCreatedDate))
                .toList();
        return !(!clientTickets.isEmpty() && ChronoUnit.HOURS.between(clientTickets.getFirst().getCreatedDate(), LocalDateTime.now()) < 3);

    }
}
