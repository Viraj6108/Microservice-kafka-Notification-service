package com.notification.notification.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Shipment {
    private Integer shipmentId;

    public enum STATUS{
        SHIPPED, DELIVERED, INTRANSIT,CANCELLED
    }
    @Enumerated(EnumType.STRING)
    public STATUS status;

    public String deliveryDate;
    private Integer orderId;

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
