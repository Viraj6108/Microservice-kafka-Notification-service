package com.notification.notification.service;

import com.notification.notification.entity.Orders;
import com.notification.notification.exception.NotificationException;

public interface NotificationService {

    public void sendNotification(String orders) throws NotificationException;
}
