package me.absolute.businessapp.producer;


import lombok.RequiredArgsConstructor;
import me.absolute.businessapp.model.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationProducer {
    @Value("${topic.name.notification}")
    private String notificationTopic;

    private final KafkaTemplate<String, Notification> kafkaTemplate;

    public void sendNotification(Notification notification) {
         kafkaTemplate.send(notificationTopic, notification);
    }

}
