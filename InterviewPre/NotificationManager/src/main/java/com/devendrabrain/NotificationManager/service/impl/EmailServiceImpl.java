package com.devendrabrain.NotificationManager.service.impl;

import com.devendrabrain.NotificationManager.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendEmail(NotificationDTO notificationDTO) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("devenpatil062.com");
        message.setTo(notificationDTO.getEmail());
        message.setSubject("Invoice for Order Id : "+notificationDTO.getOrderId());

        String text= "Invoice for the Item for Total bill of "+notificationDTO.getTotalPrice()+". Items in Invoice are  ";

        text = text.concat(notificationDTO.getInventoryDetails().entrySet().stream().map(longStringEntry -> longStringEntry.getKey()+"-"+longStringEntry.getValue()).collect(Collectors.joining(",")));

        message.setText(text);
        javaMailSender.send(message);
    }
}
