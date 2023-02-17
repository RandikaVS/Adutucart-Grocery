package com.example.adutucart5.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adutucart5.R;
import com.example.adutucart5.adminActivity.WrapContentLinearLayoutManager;
import com.example.adutucart5.model.CustomerOrderItemList;
import com.example.adutucart5.model.CustomerOrderList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;


public class CustomerOrderListAdapter extends RecyclerView.Adapter<CustomerOrderListAdapter.taskViewHolder> {

    private List<CustomerOrderList> customerOrderLists;

    public void setOrderList(List<CustomerOrderList> customerOrderItemLists){
        this.customerOrderLists = customerOrderItemLists;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CustomerOrderListAdapter.taskViewHolder holder, int position) {

        CustomerOrderList customerOrderList = customerOrderLists.get(position);
        holder.OrderId.setText(customerOrderList.getKey());

        holder.OrderAddress.setText(customerOrderList.getAddress());
        holder.OrderPaymentType.setText(customerOrderList.getPaymentType());
        holder.SubTotal.setText(customerOrderList.getSubTotal());

        if(customerOrderList.getStatus().equals("ToShip")){
            holder.ToShip.setBackgroundResource(R.drawable.status_circle_view);
            holder.ToShipBar.setBackgroundResource(R.color.white);
        } else if (customerOrderList.getStatus().equals("ToReceive")) {
            holder.ToShip.setBackgroundResource(R.drawable.status_circle_view);
            holder.ToShipBar.setBackgroundResource(R.color.white);
            holder.ToReceive.setBackgroundResource(R.drawable.status_circle_view);
            holder.ToReceiveBar.setBackgroundResource(R.color.white);
        }
        else if(customerOrderList.getStatus().equals("Completed")){
            holder.ToShip.setBackgroundResource(R.drawable.status_circle_view);
            holder.ToShipBar.setBackgroundResource(R.color.white);
            holder.ToReceive.setBackgroundResource(R.drawable.status_circle_view);
            holder.ToReceiveBar.setBackgroundResource(R.color.white);
            holder.CompletedBar.setBackgroundResource(R.color.white);
            holder.Completed.setBackgroundResource(R.drawable.status_circle_view);
            holder.AfterCompleted.setBackgroundResource(R.drawable.status_circle_view);
        }

        holder.OrderRv.setHasFixedSize(true);
        holder.OrderRv.setLayoutManager(new LinearLayoutManager(holder.OrderRv.getContext(),LinearLayoutManager.VERTICAL,false));
        CustomerOrderItemListAdapter customerOrderItemListAdapter = new CustomerOrderItemListAdapter();
        customerOrderItemListAdapter.setChildItemList(customerOrderList.getCustomerOrderItemList());
        holder.OrderRv.setAdapter(customerOrderItemListAdapter);
        customerOrderItemListAdapter.notifyDataSetChanged();




    }

    @NonNull
    @Override
    public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_order_list, parent, false);

        return new taskViewHolder(view);
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

        TextView OrderId,ToShip,ToReceive,Completed,AfterCompleted,OrderAddress,OrderPaymentType,SubTotal;
        View ToShipBar,ToReceiveBar,CompletedBar;

        RecyclerView OrderRv;

        CardView OrderCard;

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

        }
    }
}
