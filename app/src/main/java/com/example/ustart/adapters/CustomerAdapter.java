package com.example.ustart.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ustart.Common.Stables;
import com.example.ustart.CustomerDetails;
import com.example.ustart.ListOfCustomersActivity;
import com.example.ustart.R;
import com.example.ustart.SingleProductActivity;
import com.example.ustart.models.CategoryItem;
import com.example.ustart.models.Customers;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>{

    ListOfCustomersActivity listOfCustomersActivity;
    Context mContext;
    private List<Customers> customers;

    public CustomerAdapter(ListOfCustomersActivity listOfCustomersActivity, Context mContext, List<Customers> customers) {
        this.listOfCustomersActivity = listOfCustomersActivity;
        this.mContext = mContext;
        this.customers = customers;
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder{
        ImageView single_customer_image;
        TextView single_customer_shop_name,single_customer_address;
        MaterialCardView single_customer_card_view;
        LinearLayout single_customer_details_button;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            single_customer_image =itemView.findViewById(R.id.single_customer_image);
            single_customer_shop_name =itemView.findViewById(R.id.single_customer_shop_name);
            single_customer_address =itemView.findViewById(R.id.single_customer_address);
            single_customer_card_view =itemView.findViewById(R.id.single_customer_card_view);
            single_customer_details_button =itemView.findViewById(R.id.single_customer_details_button);
        }
    }



    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_coustomer,parent,false);
        return new CustomerAdapter.CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final Customers customer = customers.get(position);

        holder.single_customer_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CustomerDetails.class)
                        .putExtra("id",customer.getId())
                        .putExtra("name",customer.getName())
                        .putExtra("shopname",customer.getShopname())
                        .putExtra("address",customer.getAddress())
                        .putExtra("email",customer.getEmail())
                        .putExtra("telephone",customer.getTelephone())
                        .putExtra("joined_date",customer.getJoined_date())
                        .putExtra("city",customer.getCity())
                        .putExtra("image",customer.getProfile_pic())
                );
            }
        });

        //animation
        holder.single_customer_card_view.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        Picasso.get().load(Stables.baseUrl+ customer.getProfile_pic()).into(holder.single_customer_image);
        holder.single_customer_shop_name.setText(customer.getShopname());
        holder.single_customer_address.setText(customer.getAddress());

    }

    @Override
    public int getItemCount() {
        return customers.size();
    }



}
