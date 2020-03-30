package com.example.ustart.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ustart.Common.Stables;
import com.example.ustart.ProductsForUsers;
import com.example.ustart.R;
import com.example.ustart.SingleProductActivity;
import com.example.ustart.models.ProductItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder> {
    private List<ProductItem> ProductItemList;
    Context mContext;

    public ProductItemAdapter(Context mContext,List<ProductItem> productItemList) {
        ProductItemList = productItemList;
        this.mContext = mContext;
    }

    public static class ProductItemViewHolder extends RecyclerView.ViewHolder{
        TextView title,description,price;
        CardView cardviewnewnew;
        ImageView imageView;

        public ProductItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textViewTitle);
            description=itemView.findViewById(R.id.textViewShortDesc);
            price=itemView.findViewById(R.id.textViewPrice);
            cardviewnewnew=itemView.findViewById(R.id.cardviewnewnew);
            imageView=itemView.findViewById(R.id.imageView);

        }
    }

    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ProductItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ProductItemViewHolder holder, final int position) {
        final ProductItem productItem=ProductItemList.get(position);

        holder.cardviewnewnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, SingleProductActivity.class)
                    .putExtra("title",productItem.getTitle())
                    .putExtra("description",productItem.getDescription())
                    .putExtra("category",productItem.getCategory())
                    .putExtra("price",productItem.getPrice())
                    .putExtra("image",productItem.getImage())
                    .putExtra("id",productItem.getId())
                );
            }
        });

        //image set loading
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//               try {
//                   Picasso.get().load(Stables.baseUrl+ productItem.getImage()).into(holder.imageView);
//               }catch(Exception e){
//                   System.out.println(e.getMessage());
//               }
//            }
//        }).start();

        //animation
        holder.cardviewnewnew.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));



        holder.title.setText(productItem.getTitle());
        holder.description.setText(productItem.getDescription());
        holder.price.setText(productItem.getPrice());
        Picasso.get().load(Stables.baseUrl+ productItem.getImage()).into(holder.imageView);




//        Picasso.get().load(Stables.baseUrl+ userObj.getString("profile_pic")).into(profile_profile_image);

    }

    @Override
    public int getItemCount() {
        return ProductItemList.size();
    }




}

