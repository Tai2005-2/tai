package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView productName, productDescription, productPrice;
    private ImageView productImage;
    private Button btnBuyNow;

    private Product product; // Khai báo toàn cục để dùng trong onClick

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ view
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_description);
        productPrice = findViewById(R.id.product_price);
        productImage = findViewById(R.id.product_image);
        btnBuyNow = findViewById(R.id.buy_button);

        // Nhận đối tượng Product từ Intent
        product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            // Gán dữ liệu vào giao diện
            productName.setText(product.getTen());
            productDescription.setText(product.getMota());
            productPrice.setText(String.format("%d VND", product.getGia()));

            // Hiển thị ảnh từ drawable
            int imageResId = getResources().getIdentifier(
                    product.getHinhanh().replace(".jpg", "").replace(".png", ""),
                    "drawable",
                    getPackageName()
            );
            productImage.setImageResource(imageResId);
        } else {
            Toast.makeText(this, "Không có thông tin sản phẩm", Toast.LENGTH_SHORT).show();
        }

        // Xử lý nút Mua Ngay
        btnBuyNow.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailActivity.this, PurchaseActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });
    }
}
