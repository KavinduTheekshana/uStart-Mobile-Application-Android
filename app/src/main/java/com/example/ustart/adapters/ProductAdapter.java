package com.example.ustart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ustart.R;
import com.example.ustart.models.Products;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Products> productsList;

    public ProductAdapter(Context mCtx, List<Products> productsList) {
        this.mCtx = mCtx;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Products products = productsList.get(position);

        holder.textViewProductName.setText(products.getPriductName());
        holder.textViewProductPrice.setText(products.getProductPrice());

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewProductName,textViewProductPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_item_product_image);
            textViewProductName = itemView.findViewById(R.id.product_item_product_name);
            textViewProductPrice = itemView.findViewById(R.id.product_item_product_price);
        }
    }
}
