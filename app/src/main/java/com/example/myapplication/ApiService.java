package com.example.myapplication;



import com.example.myapplication.Customer;
import com.example.myapplication.Product;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface ApiService {

    // -------------------- SẢN PHẨM --------------------
    @GET("sanpham")
    Call<List<com.example.myapplication.Product>> getAllProducts();

    @GET("sanpham/{id}")
    Call<com.example.myapplication.Product> getProduct(@Path("id") int id);

    @POST("sanpham")
    Call<Void> createProduct(@Body Product product);

    @PUT("sanpham/{id}")
    Call<Void> updateProduct(@Path("id") int id, @Body Product product);

    @DELETE("sanpham/{id}")
    Call<Void> deleteProduct(@Path("id") int id);

    // -------------------- KHÁCH HÀNG --------------------
    @GET("khachhang")
    Call<List<Customer>> getAllCustomers(); // Nếu cần lấy danh sách

    @GET("khachhang/{id}")
    Call<Customer> getCustomer(@Path("id") int id);

    @POST("khachhang")
    Call<Void> createCustomer(@Body CustomerRequest customer);

    @PUT("khachhang/{id}")
    Call<Void> updateCustomer(@Path("id") int id, @Body Customer customer);

    @DELETE("khachhang/{id}")
    Call<Void> deleteCustomer(@Path("id") int id);

    // -------------------- HÓA ĐƠN --------------------
    @GET("hoadon")
    Call<List<JsonObject>> getAllInvoices();

    @GET("hoadon/{id}")
    Call<JsonObject> getInvoice(@Path("id") int id);

    @POST("hoadon")
    Call<Void> createInvoice(@Body InvoiceRequest invoice);


    @PUT("hoadon/{id}")
    Call<Void> updateInvoice(@Path("id") int id, @Body JsonObject invoice);

    @DELETE("hoadon/{id}")
    Call<Void> deleteInvoice(@Path("id") int id);
}
