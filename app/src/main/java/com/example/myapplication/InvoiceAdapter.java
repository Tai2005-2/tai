package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
    private List<Invoice> invoiceList;

    public InvoiceAdapter(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    @Override
    public InvoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false);
        return new InvoiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InvoiceViewHolder holder, int position) {
        Invoice invoice = invoiceList.get(position);
        holder.tvCustomerId.setText(String.valueOf(invoice.getCustomer_id()));
        holder.tvProductId.setText(String.valueOf(invoice.getProduct_id()));
        holder.tvTime.setText(invoice.getTime());
    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }

    public static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomerId, tvProductId, tvTime;

        public InvoiceViewHolder(View itemView) {
            super(itemView);
            tvCustomerId = itemView.findViewById(R.id.tvCustomerId);
            tvProductId = itemView.findViewById(R.id.tvProductId);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
