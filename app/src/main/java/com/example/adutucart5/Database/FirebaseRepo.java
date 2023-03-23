package com.example.adutucart5.Database;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.adutucart5.adapter.CustomerOrderListAdapter;
import com.example.adutucart5.adminActivity.WrapContentLinearLayoutManager;
import com.example.adutucart5.model.AdminOrderItemList;
import com.example.adutucart5.model.CustomerOrderItemList;
import com.example.adutucart5.model.CustomerOrderList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRepo {

    private DatabaseReference databaseReference;
    private OnRealTimeDbTaskComplete onRealTimeDbTaskComplete;

    public FirebaseRepo(OnRealTimeDbTaskComplete onRealTimeDbTaskComplete){
        this.onRealTimeDbTaskComplete = onRealTimeDbTaskComplete;
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
    }

    public void getAllOrderData(){
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CustomerOrderList> customerOrderLists = new ArrayList<>();

                for(DataSnapshot ds :snapshot.getChildren()){
                    CustomerOrderList customerOrderList = new CustomerOrderList();
                    customerOrderList.setAddress(ds.child("address").getValue(String.class));
//
                    GenericTypeIndicator<ArrayList<CustomerOrderItemList>> genericTypeIndicator =
                            new GenericTypeIndicator<ArrayList<CustomerOrderItemList>>() {};
                    customerOrderList.setCustomerOrderItemList(ds.child("items").getValue(genericTypeIndicator));

                    customerOrderList.setPaymentType(ds.child("paymentType").getValue(String.class));
                    customerOrderList.setStatus(ds.child("status").getValue(String.class));
                    customerOrderList.setSubTotal(ds.child("subTotal").getValue(String.class));
                    customerOrderList.setRiderName(ds.child("riderName").getValue(String.class));
                    customerOrderList.setWaitingTime(ds.child("waitingTime").getValue(String.class));
                    customerOrderList.setKey((ds.getKey()));


                    customerOrderLists.add(customerOrderList);
                }

                onRealTimeDbTaskComplete.onSuccess(customerOrderLists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onRealTimeDbTaskComplete.onFailure(error);
                throw  error.toException();
            }
        });
    }

    public void getAllOrderDataAdmin(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CustomerOrderList> customerOrderLists = new ArrayList<>();

                for(DataSnapshot ds :snapshot.getChildren()){

                    databaseReference.child(ds.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            for(DataSnapshot ds2 :snapshot.getChildren()){
                                CustomerOrderList customerOrderList = new CustomerOrderList();
                                customerOrderList.setAddress(ds2.child("address").getValue(String.class));

                                GenericTypeIndicator<ArrayList<CustomerOrderItemList>> genericTypeIndicator =
                                        new GenericTypeIndicator<ArrayList<CustomerOrderItemList>>() {};
                                customerOrderList.setCustomerOrderItemList(ds2.child("items").getValue(genericTypeIndicator));

                                customerOrderList.setPaymentType(ds2.child("paymentType").getValue(String.class));
                                customerOrderList.setStatus(ds2.child("status").getValue(String.class));
                                customerOrderList.setSubTotal(ds2.child("subTotal").getValue(String.class));
                                customerOrderList.setKey((ds2.getKey()));
                                customerOrderList.setParentKey(ds.getKey());


                                customerOrderLists.add(customerOrderList);
                                System.out.println("Order ===== "+customerOrderList);
                            }
                            System.out.println("order list ==="+customerOrderLists.size());
                            onRealTimeDbTaskComplete.onSuccessAdmin(customerOrderLists);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            onRealTimeDbTaskComplete.onFailure(error);
                            throw  error.toException();
                        }
                    });

                }

                System.out.println("order list new1==="+customerOrderLists.size());


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onRealTimeDbTaskComplete.onFailure(error);
                throw  error.toException();
            }
        });

    }

    public interface OnRealTimeDbTaskComplete{

        void onSuccess(List<CustomerOrderList> customerOrderLists);
        void onSuccessAdmin(List<CustomerOrderList> customerOrderLists);
        void onFailure(DatabaseError error);

    }
}
