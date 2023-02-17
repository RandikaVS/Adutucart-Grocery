package com.example.adutucart5.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adutucart5.Database.CartDb;
import com.example.adutucart5.R;
import com.example.adutucart5.activity.SingleProductView;
import com.example.adutucart5.model.Cart2;
import com.example.adutucart5.model.Product2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerProductViewAdapter extends FirebaseRecyclerAdapter<Product2, CustomerProductViewAdapter.taskViewHolder> {

    private String key,ImageUrl;
    private Button AddCart;
    private String productKey;
    public CustomerProductViewAdapter(@NonNull FirebaseRecyclerOptions<Product2> options,String key) {
        super(options);
        this.key = key;
    }


    @Override
    protected void onBindViewHolder(@NonNull CustomerProductViewAdapter.taskViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Product2 model) {
        holder.Name.setText(model.getTitle());
        holder.ProductPrice.setText(String.valueOf(model.getPrice()));
        Glide.with(holder.itemView).load(model.getImage()).into(holder.ProductImage);
        AddCart = holder.AddToCart;


        holder.ProductCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productKey =  getRef(position).getKey();

                Context context = view.getContext();
                Intent intent = new Intent(context, SingleProductView.class);
                intent.putExtra("store_key",key).putExtra("product_key",productKey);
                context.startActivity(intent);
            }
        });

        holder.Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.ProductQty.getText().toString().isEmpty()){
                    holder.ProductQty.setText("1");
                }
                else{
                    String present_value_string = holder.ProductQty.getText().toString();
                    int present_value_int = Integer.parseInt(present_value_string);
                    present_value_int++;

                    holder.ProductQty.setText(String.valueOf(present_value_int));
                }

            }
        });
        holder.Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.ProductQty.getText().toString().isEmpty()){
                    holder.ProductQty.setText("0");
                }
               else{
                    String present_value_string = holder.ProductQty.getText().toString();
                    int present_value_int = Integer.parseInt(present_value_string);
                    if(present_value_int==0){
                        Toast.makeText(view.getContext(), "Minimum value",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        present_value_int--;
                        holder.ProductQty.setText(String.valueOf(present_value_int));
                    }

                }
            }
        });

        holder.AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qty="0",SubTotal="0";
                if(holder.ProductQty.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(),"Quantity must greater than 0",Toast.LENGTH_SHORT).show();
                }
                else{
                    qty = holder.ProductQty.getText().toString();
                    SubTotal = String.valueOf(Integer.parseInt(holder.ProductQty.getText().toString()) * Integer.parseInt(holder.ProductPrice.getText().toString()));
                    addCart(holder.Name.getText().toString(),qty,holder.ProductPrice.getText().toString(),SubTotal,getRef(position).getKey(),view.getContext(),getItem(position).getImage());
                }



            }
        });
    }

    @NonNull
    @Override
    public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_product_card, parent, false);

        return new CustomerProductViewAdapter.taskViewHolder(view);
    }

    class taskViewHolder extends RecyclerView.ViewHolder {

        TextView Name,ProductQty,ProductPrice;

        CircleImageView ProductImage;

        CardView ProductCard;
        ImageView Add,Remove;
        Button AddToCart;

        public taskViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.user_product_name);
            ProductQty = itemView.findViewById(R.id.user_qty_count);
            ProductImage = itemView.findViewById(R.id.user_product_image);
            ProductPrice = itemView.findViewById(R.id.user_product_price);
            ProductCard = itemView.findViewById(R.id.user_product_card_view);
            Add = itemView.findViewById(R.id.add_btn);
            Remove = itemView.findViewById(R.id.remove_btn);
            AddToCart = itemView.findViewById(R.id.add_cart_btn);
        }
    }


    private void addCart(String title,String qty,String price,String sub,String pid,Context context,String image){
        CartDb cartDb = new CartDb();
        Cart2 cart2 = new Cart2(image,title,qty,sub,pid,price);
        cartDb.addCart(cart2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context,"Product added to cart",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Fail to add cart",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
