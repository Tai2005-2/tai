package com.example.myapplication;

public class Invoice {
    private int id;
    private int customer_id;
    private int product_id;
    private String time;

    public Invoice(int customer_id, int product_id, String time) {
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
