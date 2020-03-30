package com.example.ustart.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.CartActivity;
import com.example.ustart.Common.Stables;
import com.example.ustart.ProductsForUsers;
import com.example.ustart.R;
import com.example.ustart.SingleProductActivity;
import com.example.ustart.models.CartItem;
import com.example.ustart.models.CategoryItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    CartActivity cartActivity;
    private List<CartItem> cartItems;
    Context mContext;

    public CartItemAdapter(CartActivity cartActivity, List<CartItem> cartItems, Context mContext) {
        this.cartActivity = cartActivity;
        this.cartItems = cartItems;
        this.mContext = mContext;
    }


    public class CartItemViewHolder extends RecyclerView.ViewHolder {
        TextView title,price,qty,total;
        LinearLayout remove;
        ImageView image;
        CardView cardView;
        LinearLayout cart_item_remove_button;
        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.cart_item_tetle);
            price=itemView.findViewById(R.id.cart_item_price);
            qty=itemView.findViewById(R.id.cart_item_qty);
            total=itemView.findViewById(R.id.cart_item_total_price);
            remove=itemView.findViewById(R.id.cart_item_remove_button);
            image=itemView.findViewById(R.id.cart_item_image);
            cardView=itemView.findViewById(R.id.cart_item_card_view);
            cart_item_remove_button=itemView.findViewById(R.id.cart_item_remove_button);

        }
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new CartItemAdapter.CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, final int position) {
        //animation
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        final CartItem cartItem=cartItems.get(position);
        holder.title.setText(cartItem.getTitle());
        holder.price.setText(cartItem.getPrice());
        holder.qty.setText(cartItem.getQty());
        holder.total.setText(cartItem.getTotal());
        Picasso.get().load(Stables.baseUrl+ cartItem.getImage()).into(holder.image);


        holder.cart_item_remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    String url=new Stables().DeleteCartItem(Integer.parseInt(cartItem.getId()));
                    RequestQueue requestQueue= Volley.newRequestQueue(cartActivity);

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    notifyItemRemoved(position);
                                    Toast.makeText(cartActivity, "Your Item has been Deleted", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(cartActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(stringRequest);
                }catch(Exception e){
                    e.printStackTrace();
                }











            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }



}
