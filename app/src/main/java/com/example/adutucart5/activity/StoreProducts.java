package com.example.adutucart5.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adutucart5.R;
import com.example.adutucart5.adapter.CustomerProductViewAdapter;
import com.example.adutucart5.adminActivity.WrapContentLinearLayoutManager;
import com.example.adutucart5.fragment.StoresList;
import com.example.adutucart5.model.Product2;
import com.example.adutucart5.model.Stores;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoreProducts extends AppCompatActivity {

    private String key;
    private RecyclerView ProductRecyclerView;
    private TextView StoreName;
    private CircleImageView StoreImage;
    private ImageView BackBtn;

    CustomerProductViewAdapter customerProductViewAdapter;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_products);
        getSupportActionBar().hide();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            key = extra.getString("store_name");//next activity on back press need to initialize bundle extra
        }

        ProductRecyclerView=findViewById(R.id.user_store_product_list);
        StoreName = findViewById(R.id.user_store_name);
        StoreImage = findViewById(R.id.user_store_image);
        BackBtn = findViewById(R.id.edit_store_back_btn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction()
//                        .add(android.R.id.content, new StoresList()).commit();
                startActivity(new Intent(StoreProducts.this,MainActivity.class));


            }
        });

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
        if(key.equals("HardwareSection")){
            StoreName.setText("Hardware Section");
            StoreImage.setImageResource(R.drawable.hardware_section);
        }
        if(key.equals("MeatSection")){
            StoreName.setText("Meat Section");
            StoreImage.setImageResource(R.drawable.meat_section);
        }
        else if(key.equals("ProductSection")){
            StoreName.setText("Product Section");
            StoreImage.setImageResource(R.drawable.product_section);
        }
        else if(key.equals("VegetableSection")){
            StoreName.setText("Vegetable Section");
            StoreImage.setImageResource(R.drawable.vegetable_section);
        }
    }
}