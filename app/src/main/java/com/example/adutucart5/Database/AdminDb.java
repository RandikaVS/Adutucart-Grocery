package com.example.adutucart5.Database;

import com.example.adutucart5.model.Admin;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminDb {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public  AdminDb(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Admin.class.getSimpleName());
        mAuth=FirebaseAuth.getInstance();
    }

    public Task<Void> addAdmin(Admin adm){

        return databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(adm);

    }

    public Task<AuthResult> adminAuth(Admin adm){

        return mAuth.createUserWithEmailAndPassword(adm.getEmail(), adm.getPassword());

    }

    public Task<AuthResult> adminSignIn(String email, String password){
        return mAuth.signInWithEmailAndPassword(email,password);
    }

    public  Task<Void> adminUpdate(String key, HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> adminDelete(String key){
        return databaseReference.child(key).removeValue();
    }
}
