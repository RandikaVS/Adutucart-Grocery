package com.example.adutucart5.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adutucart5.activity.BaseActivity;
import com.example.adutucart5.adapter.AdminProductViewAdapter;
import com.example.adutucart5.adapter.CustomerOrderListAdapter;
import com.example.adutucart5.adapter.OrderAdapter;
import com.example.adutucart5.R;
import com.example.adutucart5.adminActivity.WrapContentLinearLayoutManager;
import com.example.adutucart5.model.CustomerOrderItemList;
import com.example.adutucart5.model.CustomerOrderList;
import com.example.adutucart5.model.Order;
import com.example.adutucart5.model.Product2;
import com.example.adutucart5.util.localstorage.LocalStorage;
import com.example.adutucart5.viewModel.FirebaseViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
 
/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {

    private RecyclerView OrderRecyclerView;
    private FirebaseViewModel firebaseViewModel;

    private CustomerOrderListAdapter customerOrderListAdapter;
    private LinearLayout NoOrder;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("MyOrder");

        NoOrder = view.findViewById(R.id.no_order_ll);
        OrderRecyclerView = view.findViewById(R.id.order_rv);

        OrderRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        OrderRecyclerView.setLayoutManager(mLayoutManager);
        customerOrderListAdapter = new CustomerOrderListAdapter();
        OrderRecyclerView.setAdapter(customerOrderListAdapter);

        firebaseViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);

        firebaseViewModel.getAllData();
        firebaseViewModel.getOrderMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<CustomerOrderList>>() {
            @Override
            public void onChanged(List<CustomerOrderList> customerOrderLists) {
                customerOrderListAdapter.setOrderList(customerOrderLists);
                customerOrderListAdapter.notifyDataSetChanged();
            }
        });

        firebaseViewModel.getDatabaseErrorMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DatabaseError>() {
            @Override
            public void onChanged(DatabaseError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
