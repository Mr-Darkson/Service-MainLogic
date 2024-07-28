package me.absolute.businessapp.service;

import lombok.RequiredArgsConstructor;
import me.absolute.businessapp.model.Ticket;
import me.absolute.businessapp.repositories.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}
