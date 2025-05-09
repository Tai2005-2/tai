package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerAPI {

    // Lấy tất cả khách hàng (nếu cần)
    @GET("/customers")
    Call<List<Customer>> getAllCustomers();

    // Lấy khách hàng theo ID
    @GET("/customers/{id}")
    Call<Customer> getCustomer(@Path("id") int id);

    // Thêm khách hàng mới
    @POST("/customers")
    Call<Customer> createCustomer(@Body Customer customer);

    // Cập nhật thông tin khách hàng
    @PUT("/customers/{id}")
    Call<Void> updateCustomer(@Path("id") int id, @Body Customer customer);

    // (Tùy chọn) Xóa khách hàng
    // @DELETE("/customers/{id}")
    // Call<Void> deleteCustomer(@Path("id") int id);
}
