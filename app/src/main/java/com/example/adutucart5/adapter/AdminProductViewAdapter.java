package com.example.adutucart5.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adutucart5.R;
import com.example.adutucart5.adminActivity.AdminUpdateProduct;
import com.example.adutucart5.model.Product2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminProductViewAdapter extends FirebaseRecyclerAdapter<Product2, AdminProductViewAdapter.taskViewHolder> {

    public AdminProductViewAdapter(@NonNull FirebaseRecyclerOptions<Product2> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull taskViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Product2 model) {

        holder.Name.setText(model.getTitle());
        holder.ProductQty.setText(String.valueOf(model.getQty()));
        holder.ProductPrice.setText(String.valueOf(model.getPrice()));
        Glide.with(holder.itemView).load(model.getImage()).into(holder.ProductImage);

        holder.ProductCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference parent = getRef(position).getParent();
//                if (parent != null)
//                    intent.putExtra("ParentKey", parent.getKey());
//                context.startActivity(intent);

                String key =  getRef(position).getKey();

                Context context = view.getContext();
                Intent intent = new Intent(context, AdminUpdateProduct.class);
                intent.putExtra("product_key",key);
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_product_card, parent, false);

        return new AdminProductViewAdapter.taskViewHolder(view);
    }


    class taskViewHolder extends RecyclerView.ViewHolder {

        TextView Name,ProductQty,ProductPrice;

        CircleImageView ProductImage;

        CardView ProductCard;

        public taskViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.product_name);
            ProductQty = itemView.findViewById(R.id.admin_product_qty);
            ProductImage = itemView.findViewById(R.id.admin_product_image);
            ProductPrice = itemView.findViewById(R.id.admin_product_price);
            ProductCard = itemView.findViewById(R.id.product_card_view);
        }
    }
}
