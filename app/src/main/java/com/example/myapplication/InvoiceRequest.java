package com.example.myapplication;

public class InvoiceRequest {
    private int id_khachhang;
    private int id_sanpham;
    private String thoigian;
    private int tongtien;

    public InvoiceRequest(int id_khachhang, int id_sanpham, String thoigian, int tongtien) {
        this.id_khachhang = id_khachhang;
        this.id_sanpham = id_sanpham;
        this.thoigian = thoigian;
        this.tongtien = tongtien;
    }

    // getters
}
