package com.example.adutucart5.Database;

import com.example.adutucart5.model.Admin;
import com.example.adutucart5.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserDb {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public  UserDb(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());
        mAuth=FirebaseAuth.getInstance();
    }

    public Task<Void> addUser(User user){

        return databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(user);

    }

    public Task<AuthResult> userAuth(User user){

        return mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword());

    }

    public Task<AuthResult> userSignIn(String email, String password){
        return mAuth.signInWithEmailAndPassword(email,password);
    }

    public  Task<Void> userUpdate(HashMap<String,Object> hashMap){
        return databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(hashMap);
    }

    public Task<Void> userDelete(){
        try {
            String uid = mAuth.getCurrentUser().getUid();
            mAuth.getCurrentUser().delete();
            return databaseReference.child(uid).removeValue();
        }
        catch (Exception e){
            return null;
        }


    }

}
