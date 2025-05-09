package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.productList = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product p = productList.get(position);
        holder.txtTen.setText(p.getTen());
        holder.txtGia.setText("Giá: " + p.getGia() + " đ");

        // Load ảnh từ drawable theo tên trả về
        int resId = context.getResources().getIdentifier(
                p.getHinhanh().replace(".jpg", "").replace(".png", ""),
                "drawable",
                context.getPackageName()
        );
        holder.imgProduct.setImageResource(resId);

        // Nút Chi tiết
        holder.btnChiTiet.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product", p); // Truyền đối tượng Product
            context.startActivity(intent);
        });

        // Nút Mua
        holder.btnMua.setOnClickListener(v -> {
            Intent intent = new Intent(context, PurchaseActivity.class);
            intent.putExtra("product", p); // Truyền đối tượng Product
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtGia;
        ImageView imgProduct;
        Button btnChiTiet, btnMua;

        public ProductViewHolder(View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtGia = itemView.findViewById(R.id.txtGia);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnChiTiet = itemView.findViewById(R.id.btnChiTiet);
            btnMua = itemView.findViewById(R.id.btnMua);
        }
    }
}
