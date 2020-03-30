package com.example.ustart.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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
import java.util.ArrayList;
import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder> {

    ProductsForUsers productsForUsers;
    Context mContext;
    private List<CategoryItem> categoryItems;

    public CategoryItemAdapter(List<CategoryItem> categoryItems, ProductsForUsers productsForUsers,Context mContext) {
        this.categoryItems = categoryItems;
        this.productsForUsers=productsForUsers;
        this.mContext = mContext;
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
        final CategoryItem categoryItem=categoryItems.get(position);

        //animation
        holder.category.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));


        holder.category.setText(categoryItem.getCategory());

        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=position;
                notifyDataSetChanged();
            }
        });

        if(position==0){
            holder.category.setBackgroundColor(0xFF097dd3);
            holder.category.setTextColor(Color.WHITE);
        }

        if(fir!=0){
            if(index==position){
                holder.category.setBackgroundColor(0xFF097dd3);
                holder.category.setTextColor(Color.WHITE);

                productsForUsers.productItem=new ArrayList<>();
                productsForUsers.filter=categoryItem.getId();
                productsForUsers.loadItems();
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
