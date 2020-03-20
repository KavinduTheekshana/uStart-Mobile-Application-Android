package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ustart.adapters.ProductAdapter;
import com.example.ustart.models.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductsForUsers extends AppCompatActivity {
    private ImageView BackButton;

    RecyclerView recyclerView;
    ProductAdapter adapter;
    List<Products> productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_for_users);

        BackButton = (ImageView) findViewById(R.id.btnback);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack(null);
            }
        });


        productsList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.allpack);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productsList.add(new Products(
                "sdfsdfsdf",
                "sfsdf"
        ));
    }

    public void goToBack(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
