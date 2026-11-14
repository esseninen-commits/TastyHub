
package com.myproject.tastyhub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private int orderId;
    private Customer customer;
    private List<OrderItem> items;
    private String status;
    private String deliveryAddress;

    public Order(int orderId, Customer customer, String deliveryAddress) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        if (deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Delivery address is required.");
        }

        this.orderId = orderId;
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
        this.items = new ArrayList<>();
        this.status = "Pending";
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public String getStatus() {
        return status;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Order item cannot be null.");
        }
        items.add(item);
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Order status is required.");
        }
        this.status = status;
    }

    public double getTotal() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.getSubtotal();
        }

        return total;
    }
}
