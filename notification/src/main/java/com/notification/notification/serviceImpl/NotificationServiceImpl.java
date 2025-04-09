package com.notification.notification.serviceImpl;


import com.google.gson.Gson;
import com.notification.notification.entity.Orders;
import com.notification.notification.exception.NotificationException;
import com.notification.notification.repository.NotificationRepository;
import com.notification.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;


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
}
