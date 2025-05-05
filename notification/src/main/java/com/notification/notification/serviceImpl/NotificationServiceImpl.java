package com.notification.notification.serviceImpl;


import com.google.gson.Gson;
import com.notification.notification.entity.Notification;
import com.notification.notification.entity.Orders;
import com.notification.notification.entity.Shipment;
import com.notification.notification.exception.NotificationException;
import com.notification.notification.repository.NotificationRepository;
import com.notification.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    private final Gson gson = new Gson();

    @Autowired
    private JavaMailSender javaMailSender ;
    @KafkaListener(topics = {"order-confirmed","order-cancel"},groupId = "notification-group")
    @Override
    public void sendNotification(String orderJson) throws NotificationException {

        Orders orders = gson.fromJson(orderJson, Orders.class);
        System.out.println("📩 New Order Received in Notification Service: " + orders);


        try{
            SimpleMailMessage sm = new SimpleMailMessage();
            sm.setTo(orders.getEmail());
            if(orders.getStatus().equals("REFUND"))
            {
                sm.setSubject("Order Cancelled");
            }else{
                sm.setSubject("Order Placed");
            }
            sm.setText(orderJson);
            sm.setFrom("virajmalikwade@gmail.com");
            javaMailSender.send(sm);
        }catch (Exception e)
        {
            throw new NotificationException("Email not sent"+e);
        }
    }

    @KafkaListener(topics = "order-delivered",groupId = "notification-group")
    public void sendShipmentUpdate(String shipmentJson) throws NotificationException
    {
        Orders order = gson.fromJson(shipmentJson, Orders.class);
        System.out.println(order);
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setSubject("Delivery Update");
        sm.setFrom("virajmalikwade@gmail.com");
        sm.setText(String.valueOf(order));
        sm.setSentDate(Date.from(Instant.now()));
        sm.setTo(order.getEmail());
        javaMailSender.send(sm);

    }
}
