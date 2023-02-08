package com.example.adutucart5.Database;

import com.example.adutucart5.model.Cart;
import com.example.adutucart5.model.Cart2;
import com.example.adutucart5.model.Product2;
import com.example.adutucart5.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CartDb {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public  CartDb(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Cart");
        mAuth=FirebaseAuth.getInstance();
    }

    public Task<Void> addCart(Cart2 cart2){
//        Query check = databaseReference.child(mAuth.getCurrentUser().getUid()).orderByChild("productId").equalTo(cart2.getProductId());

        return databaseReference.child(mAuth.getCurrentUser().getUid()).child(cart2.getProductId()).setValue(cart2);

    }

    public Task<Void> deleteItem(String key){
        FirebaseDatabase cart =  FirebaseDatabase.getInstance();
        databaseReference = cart.getReference(Cart.class.getSimpleName());
        return databaseReference.child(mAuth.getCurrentUser().getUid()).child(key).removeValue();
    }


}
