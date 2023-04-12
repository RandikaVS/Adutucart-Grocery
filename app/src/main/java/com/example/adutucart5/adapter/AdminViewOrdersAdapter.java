package com.example.adutucart5.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adutucart5.R;
import com.example.adutucart5.fragment.ProfileFragment;
import com.example.adutucart5.model.AdminOrderItemList;
import com.example.adutucart5.model.CustomerOrderList;
import com.example.adutucart5.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminViewOrdersAdapter extends RecyclerView.Adapter<AdminViewOrdersAdapter.taskViewHolder>{

    private List<CustomerOrderList> customerOrderLists;
    private final Context context;

    private DatabaseReference userRef,databaseReference;


    public AdminViewOrdersAdapter(Context context){
        this.context = context;
    }


    public void setAdminOrderList(List<CustomerOrderList> customerOrderLists){
        this.customerOrderLists = customerOrderLists;
    }


    @NonNull
    @Override
    public AdminViewOrdersAdapter.taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_view_order_list, parent, false);


        return new AdminViewOrdersAdapter.taskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewOrdersAdapter.taskViewHolder holder, int position) {
        CustomerOrderList customerOrderList = customerOrderLists.get(position);
        List<String> list = new ArrayList();
        list.add("Pending");
        list.add("ToShip");
        list.add("ToReceive");
        list.add("Completed");

        holder.OrderStatus.setAdapter(
                new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, list));

        holder.OrderStatus.setSelection(Adapter.NO_SELECTION,true);

        holder.ChangeStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedItem = holder.OrderStatus.getSelectedItem().toString();

                if(selectedItem.equals("ToShip") || selectedItem.equals("ToReceive") || selectedItem.equals("Completed") || selectedItem.equals("Pending")) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("status", selectedItem);


                    databaseReference = FirebaseDatabase.getInstance().getReference("Orders").child(customerOrderList.getParentKey()).child(customerOrderList.getKey());

                    databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            notifyDataSetChanged();
                            Toast.makeText(context, "Order status changed to " + selectedItem, Toast.LENGTH_SHORT).show();
                            if(selectedItem.equals("Completed")){
                                updateRider(context,customerOrderList.getRiderId());
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


//        holder.txt_item.setText(mItemList.get(position).getItem_name());


        holder.OrderId.setText(customerOrderList.getKey());

        if(!customerOrderList.getEvidence().equals("null")){
            Glide.with(holder.itemView).load(customerOrderList.getEvidence()).placeholder(R.drawable.no_image).into(holder.Evidence);
        }


        holder.OrderAddress.setText(customerOrderList.getAddress());
        holder.OrderPaymentType.setText(customerOrderList.getPaymentType());
        holder.SubTotal.setText(customerOrderList.getSubTotal());

        if(!customerOrderList.getRiderName().equals("null")){
            holder.RiderName.setText(customerOrderList.getRiderName());
        }

        if(customerOrderList.getStatus().equals("ToShip")){
            holder.OrderStatus.setSelection(1);
            holder.ToShip.setBackgroundResource(R.drawable.status_circle_view);
            holder.ToShipBar.setBackgroundResource(R.color.white);
        } else if (customerOrderList.getStatus().equals("ToReceive")) {
            holder.OrderStatus.setSelection(2);
            holder.ToShip.setBackgroundResource(R.drawable.status_circle_view);
            holder.ToShipBar.setBackgroundResource(R.color.white);
            holder.ToReceive.setBackgroundResource(R.drawable.status_circle_view);
            holder.ToReceiveBar.setBackgroundResource(R.color.white);
        }
        else if(customerOrderList.getStatus().equals("Completed")){
            holder.OrderStatus.setSelection(3);
            holder.ToShip.setBackgroundResource(R.drawable.status_circle_view);
            holder.ToShipBar.setBackgroundResource(R.color.white);
            holder.ToReceive.setBackgroundResource(R.drawable.status_circle_view);
            holder.ToReceiveBar.setBackgroundResource(R.color.white);
            holder.CompletedBar.setBackgroundResource(R.color.white);
            holder.Completed.setBackgroundResource(R.drawable.status_circle_view);
            holder.AfterCompleted.setBackgroundResource(R.drawable.status_circle_view);
        } else if (customerOrderList.getStatus().equals("Pending")) {
            holder.OrderStatus.setSelection(0);
        }


        userRef = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName()).child(customerOrderList.getParentKey());

        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();

                        holder.CustomerName.setText(String.valueOf(dataSnapshot.child("name").getValue()));
                        holder.CustomerEmail.setText(String.valueOf(dataSnapshot.child("email").getValue()));
                        holder.CustomerMobile.setText(String.valueOf(dataSnapshot.child("mobile").getValue()));
                    }
                    else{
                        Toast.makeText(context,"Error fetch data result of user", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(context,"Error fetch data unsuccessful user",Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.OrderRv.setHasFixedSize(true);
        holder.OrderRv.setLayoutManager(new LinearLayoutManager(holder.OrderRv.getContext(),LinearLayoutManager.VERTICAL,false));
        CustomerOrderItemListAdapter customerOrderItemListAdapter = new CustomerOrderItemListAdapter();
        customerOrderItemListAdapter.setChildItemList(customerOrderList.getCustomerOrderItemList());
        holder.OrderRv.setAdapter(customerOrderItemListAdapter);
        customerOrderItemListAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        if(customerOrderLists!=null){
            return customerOrderLists.size();
        }
        else{
            return 0;
        }
    }

    class taskViewHolder extends RecyclerView.ViewHolder {

        TextView OrderId,ToShip,ToReceive,Completed,AfterCompleted,OrderAddress,
                OrderPaymentType,SubTotal,CustomerName,CustomerEmail,CustomerMobile,RiderName;
        View ToShipBar,ToReceiveBar,CompletedBar;

        RecyclerView OrderRv;

        ImageView Evidence;

        CardView OrderCard;

        Spinner OrderStatus;
        Button ChangeStatusBtn;


        public taskViewHolder(@NonNull View itemView) {
            super(itemView);


            ToShip = itemView.findViewById(R.id.to_ship);
            ToShipBar = itemView.findViewById(R.id.to_ship_bar);
            ToReceiveBar = itemView.findViewById(R.id.to_receive_bar);
            ToReceive = itemView.findViewById(R.id.to_receive);
            Completed = itemView.findViewById(R.id.completed);
            CompletedBar = itemView.findViewById(R.id.completed_bar);
            AfterCompleted = itemView.findViewById(R.id.completed_last);
            OrderCard = itemView.findViewById(R.id.order_card);
            OrderRv = itemView.findViewById(R.id.order_list_rv);
            OrderId = itemView.findViewById(R.id.order_id);
            OrderAddress = itemView.findViewById(R.id.order_address);
            OrderPaymentType = itemView.findViewById(R.id.order_payment_type);
            SubTotal = itemView.findViewById(R.id.order_subtotal);
            OrderStatus = itemView.findViewById(R.id.order_status_spinner);
            CustomerName = itemView.findViewById(R.id.customer_name);
            CustomerEmail = itemView.findViewById(R.id.customer_email);
            CustomerMobile = itemView.findViewById(R.id.customer_mobile);
            ChangeStatusBtn = itemView.findViewById(R.id.change_status_btn);
            Evidence = itemView.findViewById(R.id.evidence);
            RiderName = itemView.findViewById(R.id.rider_name);

        }
    }

    private void updateRider(Context context,String riderId){

        if(!riderId.isEmpty()) {

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("pending","0");

            databaseReference = FirebaseDatabase.getInstance().getReference("Rider");

            databaseReference.child(riderId).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(context, "Rider released", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context, "Failed to release rider", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(context, "Rider id is empty!!!", Toast.LENGTH_SHORT).show();
        }
    }

}
