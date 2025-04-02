package com.notification.notification.controller;

import com.notification.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;



}
