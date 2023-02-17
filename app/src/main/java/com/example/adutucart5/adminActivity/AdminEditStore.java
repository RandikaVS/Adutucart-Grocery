package com.example.adutucart5.adminActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.adutucart5.R;

public class AdminEditStore extends AppCompatActivity {


    private CardView SM,VictoriaSupermarket,Robinson,MarketPlace;
    private ImageView DashboardBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_store);
        getSupportActionBar().hide();

        SM = findViewById(R.id.sm_store);
        VictoriaSupermarket = findViewById(R.id.victoria_supermarket);
        Robinson = findViewById(R.id.robinson_store);
        DashboardBackBtn = findViewById(R.id.dashboard_back_btn);
        MarketPlace = findViewById(R.id.marketplace_store);

        SharedPreferences preferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        preferences.edit().remove("store").commit();

        DashboardBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminEditStore.this,AdminDashboard.class);
                startActivity(intent);
            }
        });

        SM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = "SM";
                Intent intent = new Intent(AdminEditStore.this,AdminViewStore.class);
                intent.putExtra("storeName",key);
                startActivity(intent);
            }
        });

        VictoriaSupermarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = "VictoriaSupermarket";
                Intent intent = new Intent(AdminEditStore.this,AdminViewStore.class);
                intent.putExtra("storeName",key);
                startActivity(intent);
            }
        });

        Robinson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = "Robinson";
                Intent intent = new Intent(AdminEditStore.this,AdminViewStore.class);
                intent.putExtra("storeName",key);
                startActivity(intent);
            }
        });

        MarketPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = "MarketPlace";
                Intent intent = new Intent(AdminEditStore.this,AdminViewStore.class);
                intent.putExtra("storeName",key);
                startActivity(intent);
            }
        });
    }
}