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


    private CardView HardwareSection,MeatSection,ProductSection,VegetableSection;
    private ImageView DashboardBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_store);
        getSupportActionBar().hide();

        HardwareSection = findViewById(R.id.hardware_section);
        MeatSection = findViewById(R.id.meat_section);
        ProductSection = findViewById(R.id.product_section);
        DashboardBackBtn = findViewById(R.id.dashboard_back_btn);
        VegetableSection = findViewById(R.id.vegetable_section);

        SharedPreferences preferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        preferences.edit().remove("store").commit();

        DashboardBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminEditStore.this,AdminDashboard.class);
                startActivity(intent);
            }
        });

        HardwareSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = "HardwareSection";
                Intent intent = new Intent(AdminEditStore.this,AdminViewStore.class);
                intent.putExtra("storeName",key);
                startActivity(intent);
            }
        });

        MeatSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = "MeatSection";
                Intent intent = new Intent(AdminEditStore.this,AdminViewStore.class);
                intent.putExtra("storeName",key);
                startActivity(intent);
            }
        });

        ProductSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = "ProductSection";
                Intent intent = new Intent(AdminEditStore.this,AdminViewStore.class);
                intent.putExtra("storeName",key);
                startActivity(intent);
            }
        });

        VegetableSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = "VegetableSection";
                Intent intent = new Intent(AdminEditStore.this,AdminViewStore.class);
                intent.putExtra("storeName",key);
                startActivity(intent);
            }
        });
    }
}