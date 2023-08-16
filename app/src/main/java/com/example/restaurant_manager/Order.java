package com.example.restaurant_manager;

public class Order {

    private int id;
    private String customerName;
    private String order;
    private double totalAmount;
    private boolean isTakeAway;
    private long orderTime;
    private boolean isDone;

    public Order(int id, String customerName, String order, double totalAmount, boolean isTakeAway, long orderTime, boolean isDone) {
        this.id = id;
        this.customerName = customerName;
        this.order = order;
        this.totalAmount = totalAmount;
        this.isTakeAway = isTakeAway;
        this.orderTime = orderTime;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getOrder() {
        return order;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isTakeAway() {
        return isTakeAway;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
