package com.example.ustart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ustart.R;
import com.example.ustart.models.ProductItem;

import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder> {
    private List<ProductItem> ProductItemList;

    public ProductItemAdapter(List<ProductItem> productItemList) {
        ProductItemList = productItemList;
    }

    class ProductItemViewHolder extends RecyclerView.ViewHolder{
        TextView title,description,price;
        public ProductItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textViewTitle);
            description=itemView.findViewById(R.id.textViewShortDesc);
            price=itemView.findViewById(R.id.textViewPrice);
        }
    }

    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ProductItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemViewHolder holder, int position) {
        ProductItem productItem=ProductItemList.get(position);
        holder.title.setText(productItem.getTitle());
        holder.description.setText(productItem.getDescription());
        holder.price.setText(productItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return ProductItemList.size();
    }




}

