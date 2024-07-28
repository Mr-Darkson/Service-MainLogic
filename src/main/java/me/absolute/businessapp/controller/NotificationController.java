package me.absolute.businessapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.absolute.businessapp.DTO.PersonDTO;
import me.absolute.businessapp.model.Person;
import me.absolute.businessapp.model.Ticket;
import me.absolute.businessapp.service.PersonService;
import me.absolute.businessapp.service.TelegramBotService;
import me.absolute.businessapp.service.TicketService;
import me.absolute.businessapp.util.validation.PersonValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final TicketService ticketService;
    private final PersonService personService;
    private final PersonValidator personValidator;
    private final TelegramBotService telegramBotService;


    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createClient(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            Person person = personService.buildFromDTO(personDTO);

            if (person == null) {
                person = personService.save(
                        Person.builder()
                                .username(personDTO.getUsername())
                                .telephoneNumber(personDTO.getTelephoneNumber())
                                .createdDate(LocalDateTime.now())
                                .tickets(new ArrayList<>())
                                .build()
                );
                log.info(person.toString());
            }

            if (personValidator.hasAccessToCreateTicket(person)) {
                Ticket ticket = ticketService.save(new Ticket(person, LocalDateTime.now()));
                log.info(ticket.toString());

                telegramBotService.sendTicketNotification(ticket);
                responseMap.put("status", "Заявка оформленна");
                return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
            }


            responseMap.put("error",
                    "Заявка уже создана. Следующую заявку возможно создать не ранее чем через 3 часа");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);


        } catch (Exception e) {
            log.error(e.getMessage(), Level.SEVERE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
        }

    }


}
