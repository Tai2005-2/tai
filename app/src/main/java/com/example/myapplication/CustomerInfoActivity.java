package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerInfoActivity extends AppCompatActivity {

    private EditText etName, etPhone, etAddress;
    private Button btnSubmit;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info); // Đảm bảo file XML tên đúng

        // Khởi tạo các view theo đúng ID trong XML
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btnSubmit = findViewById(R.id.btnSubmit);

        if (getIntent().hasExtra("customer_id")) {
            int customerId = getIntent().getIntExtra("customer_id", -1);
            loadCustomerInfo(customerId);
        }

        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();
            String address = etAddress.getText().toString();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(CustomerInfoActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                saveCustomerInfo(name, phone, address);
            }
        });
    }

    private void loadCustomerInfo(int customerId) {
        CustomerAPI api = ApiClient.getClient().create(CustomerAPI.class);
        Call<Customer> call = api.getCustomer(customerId);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    customer = response.body();
                    etName.setText(customer.getTen());
                    etPhone.setText(customer.getSdt());
                    etAddress.setText(customer.getDiachi());
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(CustomerInfoActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveCustomerInfo(String name, String phone, String address) {
        if (customer != null) {
            customer.setTen(name);
            customer.setSdt(phone);
            customer.setDiachi(address);

            CustomerAPI api = ApiClient.getClient().create(CustomerAPI.class);
            Call<Void> call = api.updateCustomer(customer.getId(), customer);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CustomerInfoActivity.this, "Thông tin đã được lưu!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CustomerInfoActivity.this, "Lỗi lưu dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CustomerInfoActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
