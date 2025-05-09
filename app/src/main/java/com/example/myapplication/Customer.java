package com.example.myapplication;

public class Customer {
    private int id;
    private String ten;
    private String sdt;
    private String diachi;

    // Constructor
    public Customer(String ten, String sdt, String diachi) {
        this.ten = ten;
        this.sdt = sdt;
        this.diachi = diachi;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for ten
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    // Getter and Setter for sdt
    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    // Getter and Setter for diachi
    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
