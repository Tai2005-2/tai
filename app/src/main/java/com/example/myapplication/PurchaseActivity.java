package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PurchaseActivity extends AppCompatActivity {

    private TextView tvTen, tvGia;
    private EditText edtName, edtPhone, edtAddress;
    private Button btnSubmit;
    private Product product;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        tvTen = findViewById(R.id.textViewProductName);
        tvGia = findViewById(R.id.textViewProductPrice);
        edtName = findViewById(R.id.editTextName);
        edtPhone = findViewById(R.id.editTextPhone);
        edtAddress = findViewById(R.id.editTextAddress);
        btnSubmit = findViewById(R.id.buttonSubmit);

        product = (Product) getIntent().getSerializableExtra("product");

        tvTen.setText(product.getTen());
        tvGia.setText(String.valueOf(product.getGia()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiService.class);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();
                String address = edtAddress.getText().toString();

                if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    Toast.makeText(PurchaseActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                CustomerRequest customer = new CustomerRequest(name, phone, address);

                api.createCustomer(customer).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            getLastCustomerId();
                        } else {
                            Toast.makeText(PurchaseActivity.this, "Không thể tạo khách hàng", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(PurchaseActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getLastCustomerId() {
        api.getAllCustomers().enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<Customer> customers = response.body();
                    int lastId = customers.get(customers.size() - 1).getId();

                    String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                    InvoiceRequest invoice = new InvoiceRequest(
                            lastId,
                            product.getId(),
                            now,
                            product.getGia()
                    );

                    api.createInvoice(invoice).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(PurchaseActivity.this, "Mua hàng thành công!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(PurchaseActivity.this, "Lỗi khi tạo hóa đơn", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(PurchaseActivity.this, "Lỗi khi gửi hóa đơn: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(PurchaseActivity.this, "Không thể lấy ID khách hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(PurchaseActivity.this, "Lỗi khi lấy khách hàng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
