package com.example.adutucart5.adminActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adutucart5.R;
import com.example.adutucart5.adapter.AdminViewOrdersAdapter;
import com.example.adutucart5.adapter.CustomerOrderListAdapter;
import com.example.adutucart5.model.AdminOrderItemList;
import com.example.adutucart5.model.CustomerOrderList;
import com.example.adutucart5.viewModel.FirebaseViewModel;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class AdminViewOrders extends AppCompatActivity {

    private RecyclerView AdminOrderRecyclerView;
    private FirebaseViewModel firebaseViewModel;

    private AdminViewOrdersAdapter adminViewOrdersAdapter;

    private ImageView BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_orders);
        getSupportActionBar().hide();

        AdminOrderRecyclerView = findViewById(R.id.admin_orders_recycler_view);
        BackBtn = findViewById(R.id.admin_view_orders_back_btn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminViewOrders.this,AdminDashboard.class));
            }
        });

        AdminOrderRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        AdminOrderRecyclerView.setLayoutManager(mLayoutManager);
        adminViewOrdersAdapter = new AdminViewOrdersAdapter(this);
        AdminOrderRecyclerView.setAdapter(adminViewOrdersAdapter);

        firebaseViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);

        firebaseViewModel.getAllDataAdmin();

        firebaseViewModel.getOrderMutableLiveDataAdmin().observe(this, new Observer<List<CustomerOrderList>>() {
            @Override
            public void onChanged(List<CustomerOrderList> customerOrderLists) {
                adminViewOrdersAdapter.setAdminOrderList(customerOrderLists);
                System.out.println(customerOrderLists);
                adminViewOrdersAdapter.notifyDataSetChanged();
            }
        });


        firebaseViewModel.getDatabaseErrorMutableLiveData().observe(this, new Observer<DatabaseError>() {
            @Override
            public void onChanged(DatabaseError error) {
                Toast.makeText(AdminViewOrders.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}