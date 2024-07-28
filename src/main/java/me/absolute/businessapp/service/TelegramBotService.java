package me.absolute.businessapp.service;

import lombok.RequiredArgsConstructor;
import me.absolute.businessapp.model.Notification;
import me.absolute.businessapp.model.Ticket;
import me.absolute.businessapp.producer.NotificationProducer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class TelegramBotService {
    private final NotificationProducer notificationProducer;

    public void sendTicketNotification(Ticket ticket) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String date = dateFormatter.format(ticket.getCreatedDate());
        String time = timeFormatter.format(ticket.getCreatedDate());
        String name = ticket.getOwner().getUsername();
        String number = ticket.getOwner().getTelephoneNumber();

        Notification notification = new Notification(
                """
                Составлена новая заявка (%s):
                Время: %s
                Имя: %s
                Телефон: +7%s
                """
                .formatted(date, time, name, number));

        notificationProducer.sendNotification(notification);
    }
}
