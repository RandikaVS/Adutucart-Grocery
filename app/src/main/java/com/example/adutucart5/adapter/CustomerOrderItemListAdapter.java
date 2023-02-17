package com.example.adutucart5.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adutucart5.R;
import com.example.adutucart5.model.CustomerOrderItemList;


import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerOrderItemListAdapter extends RecyclerView.Adapter<CustomerOrderItemListAdapter.ChildViewHolder> {

    private List<CustomerOrderItemList> customerOrderItemLists;


    public void setChildItemList(List<CustomerOrderItemList> customerOrderLists) {
        this.customerOrderItemLists = customerOrderLists;

        this.customerOrderItemLists.removeAll(Collections.singleton(null));
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_order_item_list, viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position) {

        CustomerOrderItemList customerOrderItemList = customerOrderItemLists.get(position);

        childViewHolder.Title.setText(customerOrderItemList.getProductName());
        Glide.with(childViewHolder.itemView).load(customerOrderItemList.getProductImage()).placeholder(R.drawable.no_image).into(childViewHolder.ItemImage);
        childViewHolder.Qty.setText(customerOrderItemList.getQty());
        childViewHolder.UnitPrice.setText(customerOrderItemList.getUnitPrice());
        childViewHolder.Total.setText(customerOrderItemList.getTotal());
    }

    @Override
    public int getItemCount()
    {
        if(customerOrderItemLists!=null){
            return customerOrderItemLists.size();
        }
        else{
            return 0;
        }
    }


    class ChildViewHolder
            extends RecyclerView.ViewHolder {

        CircleImageView ItemImage;
        TextView Title,Qty,UnitPrice,Total;

        ChildViewHolder(View itemView)
        {
            super(itemView);

            ItemImage = itemView.findViewById(R.id.item_image);
            Title = itemView.findViewById(R.id.item_name);
            Qty = itemView.findViewById(R.id.item_qty);
            UnitPrice = itemView.findViewById(R.id.unit_price);
            Total = itemView.findViewById(R.id.item_total);


        }
    }
}
