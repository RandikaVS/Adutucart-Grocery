package com.example.adutucart5.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.adutucart5.R;
import com.example.adutucart5.adapter.CustomerProductViewAdapter;
import com.example.adutucart5.adminActivity.WrapContentLinearLayoutManager;
import com.example.adutucart5.model.Product2;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoreProducts extends AppCompatActivity {

    private String key;
    private RecyclerView ProductRecyclerView;
    private TextView StoreName;
    private CircleImageView StoreImage;

    CustomerProductViewAdapter customerProductViewAdapter;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_products);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            key = extra.getString("store_name");//next activity on back press need to initialize bundle extra
        }

        ProductRecyclerView=findViewById(R.id.user_store_product_list);
        StoreName = findViewById(R.id.user_store_name);
        StoreImage = findViewById(R.id.user_store_image);

        setStoreDetails(key);

        ProductRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        databaseReference = FirebaseDatabase.getInstance().getReference("Stores").child(key);

        FirebaseRecyclerOptions<Product2> options
                = new FirebaseRecyclerOptions.Builder<Product2>()
                .setQuery(databaseReference, Product2.class)
                .build();

        if(options!=null){
            customerProductViewAdapter = new CustomerProductViewAdapter(options,key);
            // Connecting Adapter class with the Recycler view*/
            ProductRecyclerView.setAdapter(customerProductViewAdapter);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 100) {
                key = data.getStringExtra("storeName");

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        customerProductViewAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        customerProductViewAdapter.stopListening();
    }

    private void setStoreDetails(String key){
        if(key.equals("SM")){
            StoreName.setText("SM Store");
            StoreImage.setImageResource(R.drawable.sm);
        }
        if(key.equals("SuperMarket456")){
            StoreName.setText("Super Market 456");
            StoreImage.setImageResource(R.drawable.supermarket_456);
        }
        else if(key.equals("Robinson")){
            StoreName.setText("Robinson Store");
            StoreImage.setImageResource(R.drawable.robinson);
        }
    }
}