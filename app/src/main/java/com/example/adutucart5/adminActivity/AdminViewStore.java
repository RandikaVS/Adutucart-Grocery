package com.example.adutucart5.adminActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adutucart5.R;
import com.example.adutucart5.adapter.AdminProductViewAdapter;
import com.example.adutucart5.model.Product2;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminViewStore extends AppCompatActivity {

    private RecyclerView ProductRecyclerView;

    AdminProductViewAdapter adminProductViewAdapter;
    private DatabaseReference databaseReference;
    private TextView StoreName;
    private ImageView StoreImage;
    private Button AddProductBtn;
    private ImageView EditStoreBackBtn;

    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_store);

        ProductRecyclerView=findViewById(R.id.admin_product_list);
        StoreName = findViewById(R.id.store_name);
        StoreImage = findViewById(R.id.store_image);
        AddProductBtn = findViewById(R.id.add_product_btn);
        EditStoreBackBtn = findViewById(R.id.edit_store_back_btn);

        Bundle extra = getIntent().getExtras();
        if(extra!=null) {
            key = extra.getString("storeName");
        }

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // Storing the key and its value as the data fetched from edittext
                myEdit.putString("store", key);

        myEdit.commit();
        if(key.equals("SM")){
            StoreName.setText("SM");
            StoreImage.setImageResource(R.drawable.sm);
        }
        else if(key.equals("SuperMarket456")){
            StoreName.setText("456 SuperMarket");
            StoreImage.setImageResource(R.drawable.supermarket_456);
        }
        else if(key.equals("Robinson")){
            StoreName.setText("Robinson");
            StoreImage.setImageResource(R.drawable.supermarket_456);
        }

        ProductRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        databaseReference = FirebaseDatabase.getInstance().getReference("Stores").child(key);


        EditStoreBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminViewStore.this,AdminEditStore.class);
                startActivity(intent);
            }
        });
        AddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminViewStore.this,AdminAddProduct.class);
                intent.putExtra("storeName",key);
                startActivity(intent);
            }
        });


        FirebaseRecyclerOptions<Product2> options
                = new FirebaseRecyclerOptions.Builder<Product2>()
                .setQuery(databaseReference, Product2.class)
                .build();

        if(options!=null){
            adminProductViewAdapter = new AdminProductViewAdapter(options);
            // Connecting Adapter class with the Recycler view*/
            ProductRecyclerView.setAdapter(adminProductViewAdapter);
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

    private void setStoreDetails(String key){

    }


    @Override
    protected void onStart() {
        super.onStart();
        adminProductViewAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adminProductViewAdapter.stopListening();
    }
}

