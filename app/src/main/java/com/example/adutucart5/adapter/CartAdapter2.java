package com.example.adutucart5.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adutucart5.Database.AdminDb;
import com.example.adutucart5.Database.CartDb;
import com.example.adutucart5.R;
import com.example.adutucart5.activity.BaseActivity;
import com.example.adutucart5.activity.CartActivity;
import com.example.adutucart5.model.Cart2;
import com.example.adutucart5.model.CustomerOrderList;
import com.example.adutucart5.model.Order2;
import com.example.adutucart5.model.Product2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartAdapter2 extends FirebaseRecyclerAdapter<Cart2, CartAdapter2.taskViewHolder> {

    public FirebaseRecyclerOptions<Cart2> product2;
    private double total=0.00;
    Context context;
    private DatabaseReference databaseReference,databaseReference2;
    private FirebaseAuth mAuth;

    public CartAdapter2(@NonNull FirebaseRecyclerOptions<Cart2> options,Context context) {

        super(options);
        this.product2 = options;
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull CartAdapter2.taskViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Cart2 model) {

        Glide.with(holder.itemView).load(model.getImage()).placeholder(R.drawable.no_image).into(holder.imageView);
        holder.quantity.setText(String.valueOf(model.getQuantity()));
        holder.price.setText(String.valueOf(model.getUnitPrice()));
        holder.subTotal.setText(String.valueOf(model.getSubTotal()));
        holder.title.setText(String.valueOf(model.getTitle()));
        ((CartActivity) context).cartTotalPrice(position);
        checkCartEmpty();
        if(model.getImage()==null){
            holder.progressBar.setVisibility(View.VISIBLE);
        }
        else{
            holder.progressBar.setVisibility(View.GONE);
        }


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int newQty = Integer.parseInt(model.getQuantity())+1;
                model.setQuantity(String.valueOf(newQty));
                holder.quantity.setText(String.valueOf(newQty));

                int newTot = Integer.parseInt(model.getUnitPrice())+Integer.parseInt(model.getSubTotal());
                model.setSubTotal(String.valueOf(newTot));
                holder.subTotal.setText(String.valueOf(newTot));

                ((CartActivity) context).updateCartTotal(Double.parseDouble(model.getUnitPrice()),true);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Integer.parseInt(model.getQuantity())<=1){
                    Toast.makeText(view.getContext(), "Quantity must greater than 1", Toast.LENGTH_SHORT).show();
                }
                else{
                    int newQty = Integer.parseInt(model.getQuantity())-1;
                    model.setQuantity(String.valueOf(newQty));
                    holder.quantity.setText(String.valueOf(newQty));

                    int newTot = Integer.parseInt(model.getSubTotal())-Integer.parseInt(model.getUnitPrice());
                    model.setSubTotal(String.valueOf(newTot));
                    holder.subTotal.setText(String.valueOf(newTot));

                    ((CartActivity) context).updateCartTotal(Double.parseDouble(model.getUnitPrice()),false);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key =  getRef(position).getKey();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                // Set the message show for the Alert time
                builder.setMessage("Do you want to remove item ?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    CartDb cartDb = new CartDb();
                    cartDb.deleteItem(key).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(view.getContext(),"Item Removed",Toast.LENGTH_SHORT).show();
                                total=0.00;
                                notifyDataSetChanged();
                            }
                            else{
                                Toast.makeText(view.getContext(),"Fail to remove item",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                });

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();
            }
        });


    }

    @NonNull
    @Override
    public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart, parent, false);
        return new CartAdapter2.taskViewHolder(view);
    }

    class taskViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        ProgressBar progressBar;
        CardView cardView;
        TextView offer, currency, price, quantity, attribute, addToCart, subTotal;
        Button plus, minus, delete;

        public taskViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            progressBar = itemView.findViewById(R.id.progressbar);
            quantity = itemView.findViewById(R.id.quantity);
            currency = itemView.findViewById(R.id.product_currency);
            attribute = itemView.findViewById(R.id.product_attribute);
            plus = itemView.findViewById(R.id.quantity_plus);
            minus = itemView.findViewById(R.id.quantity_minus);
            delete = itemView.findViewById(R.id.cart_delete);
            subTotal = itemView.findViewById(R.id.sub_total);
            price = itemView.findViewById(R.id.product_price);
        }
    }

    @Override
    public int getItemCount() {
        Log.d("NB", "getItemCount: " + super.getItemCount());
        return super.getItemCount();
    }

    public double getCartTotal(int position) {

        total = total+Double.parseDouble(super.getItem(position).getSubTotal());
        return total;
    }

    public void CheckOutCart(String total,String address,String paymentType){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        databaseReference = db.getReference("Orders").child(mAuth.getCurrentUser().getUid()).push();
        databaseReference2 = databaseReference.child("items");

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("subTotal",total);
        hashMap.put("address",address);
        hashMap.put("paymentType",paymentType);
        hashMap.put("status","Pending");
        Task task = null;

        List<Order2> order2ArrayList = new ArrayList<>();
        if(super.getItemCount()>0) {
            for (int i = 0; i < super.getItemCount(); i++) {
                Order2 order2 = new Order2(getRef(i).getKey(),super.getItem(i).getTitle(), super.getItem(i).getImage(), super.getItem(i).getQuantity()
                        , super.getItem(i).getUnitPrice(), super.getItem(i).getSubTotal());

                order2ArrayList.add(order2);
            }
                databaseReference2.setValue(order2ArrayList).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Order completed", Toast.LENGTH_SHORT);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        db.getReference("Order").child(mAuth.getCurrentUser().getUid()).removeValue();
                        Toast.makeText(context, "Fail to placing order", Toast.LENGTH_SHORT).show();
                    }
                });


            databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        databaseReference = db.getReference("Cart").child(mAuth.getCurrentUser().getUid());
                        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                ((CartActivity) context).cartCleared();
                                ((CartActivity) context).playAnimation();
                                Toast.makeText(context, "Cart cleared", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Fail to clear cart", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
                }
            });
        }
        else{
            Toast.makeText(context, "There are no items in cart", Toast.LENGTH_SHORT).show();
        }

    }

    public void checkCartEmpty(){

        if(getItemCount()<=0){

            ((CartActivity) context).cartCleared();
        }
    }

}
