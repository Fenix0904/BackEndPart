package com.site.backend.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.site.backend.domain.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("amazon-storage")
public class SnsNotificationSender {

    private final NotificationMessagingTemplate notificationMessagingTemplate;

    @Autowired
    public SnsNotificationSender(AmazonSNS amazonSNS) {
        this.notificationMessagingTemplate = new NotificationMessagingTemplate(amazonSNS);
    }

    public void send(String subject, Anime message) {
        this.notificationMessagingTemplate.sendNotification("uploads-topic", message, subject);
    }
}