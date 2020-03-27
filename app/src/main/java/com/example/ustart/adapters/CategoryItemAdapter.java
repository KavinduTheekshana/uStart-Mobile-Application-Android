package com.example.ustart.adapters;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ustart.ProductsForUsers;
import com.example.ustart.R;
import com.example.ustart.models.CategoryItem;
import com.example.ustart.models.ProductItem;

import java.sql.SQLOutput;
import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder> {
    private List<CategoryItem> categoryItems;

    public CategoryItemAdapter(List<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
    }

    public static class CategoryItemViewHolder extends RecyclerView.ViewHolder{
        public Button category;
        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            category=itemView.findViewById(R.id.category_button);

        }
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_button,parent,false);
        return new CategoryItemAdapter.CategoryItemViewHolder(view);
    }



    int index;
    int fir=0;

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder,final int position) {
        CategoryItem categoryItem=categoryItems.get(position);
        holder.category.setText(categoryItem.getCategory());

        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=position;
                notifyDataSetChanged();
            }
        });

        if(fir!=0){
            if(index==position){
                holder.category.setBackgroundColor(0xFF097dd3);
                holder.category.setTextColor(Color.WHITE);
            }else{
                holder.category.setBackgroundColor(Color.WHITE);
                holder.category.setTextColor(0xFF097dd3);
            }
        }else{
            fir++;
        }

    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }





}
