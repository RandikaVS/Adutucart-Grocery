package com.example.adutucart5.Database;

import com.example.adutucart5.model.Admin;
import com.example.adutucart5.model.Product;
import com.example.adutucart5.model.Product2;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProductDb {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public  ProductDb(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Stores");
        mAuth=FirebaseAuth.getInstance();
    }

    public Task<Void> addProduct(Product2 product, String store){

        return databaseReference.child(store).push().setValue(product);

    }


    public Task<AuthResult> adminSignIn(String email, String password){
        return mAuth.signInWithEmailAndPassword(email,password);
    }

    public  Task<Void> updateProduct(String store,String key, HashMap<String,Object> hashMap){
        return databaseReference.child(store).child(key).updateChildren(hashMap);
    }

    public Task<Void> deleteProduct(String key,String store){
        return databaseReference.child(store).child(key).removeValue();
    }
}
