package com.example.myapplication;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductAPI {

    @GET("/sanpham")
    Call<List<Product>> getAllProducts();

    @GET("/sanpham/{id}")
    Call<Product> getProductDetail(@Path("id") int id);
    @GET("/hoadon")
    Call<List<Invoice>> getAllInvoices();
}
