package com.example.adutucart5.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adutucart5.R;
import com.example.adutucart5.activity.SingleProductView;
import com.example.adutucart5.model.Product;
import com.example.adutucart5.model.Product2;
import com.example.adutucart5.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class HomeProductAdapter extends FirebaseRecyclerAdapter<Product2,HomeProductAdapter.MyViewHolder> {
    List<Product2> product2List;
    Context context;
    String Tag;

    String store;


    public HomeProductAdapter(@NonNull FirebaseRecyclerOptions<Product2> options, Context context,String tag,String store) {
        super(options);
        this.context = context;
        this.Tag = tag;
        this.store = store;
    }



    @Override
    protected void onBindViewHolder(@NonNull HomeProductAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Product2 model) {


        holder.progressBar.setVisibility(View.GONE);
        Glide.with(holder.itemView).load(model.getImage()).placeholder(R.drawable.no_image).into(holder.imageView);

        holder.title.setText(model.getTitle());
        holder.price.setText(model.getPrice());

        holder.shopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleProductView.class);
                intent.putExtra("product_key",getRef(position).getKey());
                intent.putExtra("store_key",store);
                intent.putExtra("Home","Home");
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_new_home_products, parent, false);

        return new HomeProductAdapter.MyViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }



    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, attribute, currency, price, shopNow;
        ProgressBar progressBar;
        LinearLayout quantity_ll;
        TextView plus, minus, quantity;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            price = itemView.findViewById(R.id.product_price);
            currency = itemView.findViewById(R.id.product_currency);
            shopNow = itemView.findViewById(R.id.shop_now);
            progressBar = itemView.findViewById(R.id.progressbar);
//            quantity_ll = itemView.findViewById(R.id.quantity_ll);
//            quantity = itemView.findViewById(R.id.quantity);
//            plus = itemView.findViewById(R.id.quantity_plus);
//            minus = itemView.findViewById(R.id.quantity_minus);
            cardView = itemView.findViewById(R.id.card_view);

        }
    }
}
