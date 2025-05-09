package com.example.myapplication;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String ten;
    private String hinhanh;
    private int gia;
    private String mota;

    // Constructor
    public Product(int id, String ten, String hinhanh, int gia, String mota) {
        this.id = id;
        this.ten = ten;
        this.hinhanh = hinhanh;
        this.gia = gia;
        this.mota = mota;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
