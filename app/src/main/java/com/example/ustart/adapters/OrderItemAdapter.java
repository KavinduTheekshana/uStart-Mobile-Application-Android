package com.example.ustart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ustart.CartActivity;
import com.example.ustart.Common.Stables;
import com.example.ustart.CustomerOrdersActivity;
import com.example.ustart.R;
import com.example.ustart.models.CartItem;
import com.example.ustart.models.OrderItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {
    CustomerOrdersActivity customerOrdersActivity;
    private List<OrderItem> orderItems;
    Context mContext;

    public OrderItemAdapter(CustomerOrdersActivity customerOrdersActivity, List<OrderItem> orderItems, Context mContext) {
        this.customerOrdersActivity = customerOrdersActivity;
        this.orderItems = orderItems;
        this.mContext = mContext;
    }


    public class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView order_item_title,order_item_price,order_item_qty;
        ImageView order_item_image;
        CardView order_item_card_view;
        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            order_item_title=itemView.findViewById(R.id.order_item_title);
            order_item_price=itemView.findViewById(R.id.order_item_price);
            order_item_qty=itemView.findViewById(R.id.order_item_qty);
            order_item_image=itemView.findViewById(R.id.order_item_image);
            order_item_card_view=itemView.findViewById(R.id.order_item_card_view);

        }
    }



    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new OrderItemAdapter.OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        //animation
        holder.order_item_card_view.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        OrderItem cartItem=orderItems.get(position);
        holder.order_item_title.setText(cartItem.getProductName());
        holder.order_item_price.setText("LKR "+cartItem.getPrice()+".00");
        holder.order_item_qty.setText(cartItem.getQty());
        Picasso.get().load(Stables.baseUrl+ cartItem.getImage()).into(holder.order_item_image);

    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }


}
