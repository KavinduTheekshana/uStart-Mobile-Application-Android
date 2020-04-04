package com.example.ustart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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
        CardView order_item_card_view,mark_as_complete_button;
        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            order_item_title=itemView.findViewById(R.id.order_item_title);
            order_item_price=itemView.findViewById(R.id.order_item_price);
            order_item_qty=itemView.findViewById(R.id.order_item_qty);
            order_item_image=itemView.findViewById(R.id.order_item_image);
            order_item_card_view=itemView.findViewById(R.id.order_item_card_view);
            mark_as_complete_button=itemView.findViewById(R.id.mark_as_complete_button);

        }
    }



    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new OrderItemAdapter.OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder,final int position) {
        //animation
        holder.order_item_card_view.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        final OrderItem cartItem=orderItems.get(position);
        holder.order_item_title.setText(cartItem.getProductName());
        holder.order_item_price.setText("LKR "+cartItem.getPrice()+".00");
        holder.order_item_qty.setText(cartItem.getQty());
        Picasso.get().load(Stables.baseUrl+ cartItem.getImage()).into(holder.order_item_image);



        holder.mark_as_complete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url=new Stables().MarkOrderItemIsComplete(Integer.parseInt(cartItem.getId()));
                    RequestQueue requestQueue= Volley.newRequestQueue(customerOrdersActivity);

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    orderItems.remove(position);
                                    notifyDataSetChanged();
                                    notifyItemRemoved(position);
                                    Toast.makeText(customerOrdersActivity, "Your Item has Marked as Complete", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(customerOrdersActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        return orderItems.size();
    }


}
