package com.devendrabrain.NotificationManagerAWSSQS.service;

import com.devendrabrain.NotificationManagerAWSSQS.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private MailSender mailSender;

    public void sendMailMessage(NotificationDTO notificationDTO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("devenpatil062@gmail.com");
        simpleMailMessage.setTo("devenpatil062@gmail.com");
        simpleMailMessage.setSubject("AWS SES Invoice for Order Id : "+notificationDTO.getOrderId());

        String text= "Invoice for the Item for Total bill of "+notificationDTO.getTotalPrice()+". Items in Invoice are  ";

        text = text.concat(notificationDTO.getInventoryDetails().entrySet().stream().map(longStringEntry -> longStringEntry.getKey()+"-"+longStringEntry.getValue()).collect(Collectors.joining(",")));

        simpleMailMessage.setText(text);
        mailSender.send(simpleMailMessage);
    }
}
