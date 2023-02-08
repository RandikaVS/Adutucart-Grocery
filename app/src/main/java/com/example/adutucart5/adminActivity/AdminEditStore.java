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


    private CardView SM,SuperMarket456,Robinson;
    private ImageView DashboardBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_store);

        SM = findViewById(R.id.sm_store);
        SuperMarket456 = findViewById(R.id.super_market_456_store);
        Robinson = findViewById(R.id.robinson_store);
        DashboardBackBtn = findViewById(R.id.dashboard_back_btn);

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

        SuperMarket456.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = "SuperMarket456";
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
    }
}