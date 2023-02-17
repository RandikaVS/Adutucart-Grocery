package com.example.adutucart5.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adutucart5.Database.CartDb;
import com.example.adutucart5.R;
import com.example.adutucart5.adapter.PopularProductAdapter;
import com.example.adutucart5.helper.Data;
import com.example.adutucart5.model.Cart2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingleProductView extends AppCompatActivity {

    private String storeKey,productKey,ImageUrl,SubTotal;
    private DatabaseReference ProductRef;

    private ImageView ProductImage,Add,Remove,BackBtn;
    private TextView Title,Qty,Description,Price;
    private ProgressBar ProgressBar;
    private Button AddCartBtn;
    Data data;
    private RecyclerView recyclerView;
    private PopularProductAdapter pAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_view);
        getSupportActionBar().hide();

        ProductImage = findViewById(R.id.product_image);
        Add = findViewById(R.id.product_add_btn);
        Remove = findViewById(R.id.product_remove_btn);
        Title = findViewById(R.id.product_title);
        Qty = findViewById(R.id.product_qty_count);
        Description = findViewById(R.id.product_description);
        AddCartBtn = findViewById(R.id.product_add_cart_btn);
        Price  = findViewById(R.id.product_price);
        ProgressBar = findViewById(R.id.add_cart_progress);
        recyclerView = findViewById(R.id.related_product_recyclerview);
        BackBtn = findViewById(R.id.single_product_back_btn);

//        pAdapter = new PopularProductAdapter(data.getPopularList(), this, "Home");
//        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(pLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(pAdapter);

        Bundle extra = getIntent().getExtras();

        if(extra!=null) {
            productKey = extra.getString("product_key");
            storeKey = extra.getString("store_key");
            getProductDetails(productKey,storeKey);
        }
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(extra.getString("Home")!=null){
                    Intent intent = new Intent(SingleProductView.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SingleProductView.this, StoreProducts.class);
                    intent.putExtra("store_name", storeKey);
                    startActivity(intent);
                }
            }
        });

        AddCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLoading(true);
                if(Qty.getText().toString().isEmpty()){
                    setLoading(false);
                    Toast.makeText(SingleProductView.this,"Quantity must greater than 0",Toast.LENGTH_SHORT).show();
                }else {
                    SubTotal = String.valueOf(Integer.parseInt(Qty.getText().toString()) * Integer.parseInt(Price.getText().toString()));
                    addCart();
                }
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Qty.getText().toString().isEmpty()){
                    Qty.setText("1");
                }
                else{
                    String present_value_string = Qty.getText().toString();
                    int present_value_int = Integer.parseInt(present_value_string);
                    present_value_int++;

                    Qty.setText(String.valueOf(present_value_int));
                }

            }
        });
        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Qty.getText().toString().isEmpty()){
                    Qty.setText("0");
                }
                else{
                    String present_value_string = Qty.getText().toString();
                    int present_value_int = Integer.parseInt(present_value_string);
                    if(present_value_int==0){
                        Toast.makeText(view.getContext(), "Minimum value",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        present_value_int--;
                        Qty.setText(String.valueOf(present_value_int));
                    }

                }
            }
        });


    }


    private void addCart(){
        CartDb cartDb = new CartDb();
        Cart2 cart2 = new Cart2(ImageUrl,Title.getText().toString(),Qty.getText().toString(),SubTotal,productKey,Price.getText().toString());
        cartDb.addCart(cart2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    setLoading(false);
                    Toast.makeText(SingleProductView.this,"Product added to cart",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                setLoading(false);
                Toast.makeText(SingleProductView.this,"Fail to add cart",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getProductDetails(String productKey, String storeKey){
        if(productKey.isEmpty()||storeKey.isEmpty()){
            Toast.makeText(this,"Product key is not available",Toast.LENGTH_SHORT).show();
        }
        else{
            ProductRef = FirebaseDatabase.getInstance().getReference("Stores").child(storeKey)
                    .child(productKey);

            ProductRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    if(task.isSuccessful()){
                        if(task.getResult().exists()){
                            DataSnapshot dataSnapshot = task.getResult();
                            Title.setText(String.valueOf(dataSnapshot.child("title").getValue()));
                            Description.setText(String.valueOf(dataSnapshot.child("description").getValue()));
                            Price.setText(String.valueOf(dataSnapshot.child("price").getValue()));
                            ImageUrl = String.valueOf(dataSnapshot.child("image").getValue());
                            Glide.with(SingleProductView.this).load(String.valueOf(dataSnapshot.child("image").getValue())).placeholder(R.drawable.no_image).into(ProductImage);


                        }
                        else{
                            Toast.makeText(SingleProductView.this,"Error fetch data result", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SingleProductView.this,"Error fetch data unsuccessful",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("store_name", storeKey);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    protected void setLoading(boolean status){
        if(status){
            AddCartBtn.setVisibility(View.GONE);
            ProgressBar.setVisibility(View.VISIBLE);
        }
        else{
            AddCartBtn.setVisibility(View.VISIBLE);
            ProgressBar.setVisibility(View.GONE);
        }
    }
}