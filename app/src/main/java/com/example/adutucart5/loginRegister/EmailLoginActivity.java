package com.example.adutucart5.loginRegister;

import static com.example.adutucart5.activity.BaseActivity.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adutucart5.Database.UserDb;
import com.example.adutucart5.R;
import com.example.adutucart5.activity.MainActivity;
import com.example.adutucart5.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class EmailLoginActivity extends AppCompatActivity {

    private RelativeLayout LoginAdminBtn;
    private EditText Email,Password;
    private RelativeLayout LoginBtn;
    private TextView LoginText;
    private ProgressBar ProgressBar;
    SharedPreferences sharedpreferences;
    private DatabaseReference databaseReference;
    private ImageView BackBtn;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        getSupportActionBar().hide();

        LoginAdminBtn = findViewById(R.id.loginOperator);
        Email = findViewById(R.id.user_login_email);
        Password = findViewById(R.id.user_login_password);
        LoginBtn = findViewById(R.id.loginBtn);
        LoginText = findViewById(R.id.user_login_text);
        ProgressBar = findViewById(R.id.user_login_progress);
        BackBtn = findViewById(R.id.back_arrow);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmailLoginActivity.this,LoginRegisterMainActivity.class));
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();


                    }
                });


        UserDb userDb = new UserDb();
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLoading(true);
                String email,password;

                email = Email.getText().toString().trim();
                password = Password.getText().toString().trim();
                boolean isSuccess = true;

                if(email.isEmpty()){
                    Email.setError("Field required!!!");
                    Email.requestFocus();
                    isSuccess = false;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Please enter valid email!!!");
                    Email.requestFocus();
                    isSuccess = false;
                }
                if(password.isEmpty()){
                    Password.setError("Field required!!!");
                    Password.requestFocus();
                    isSuccess = false;
                }

                if(isSuccess){

                    userDb.userSignIn(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                //validate user status
                                databaseReference = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName()).child(currentFirebaseUser.getUid());

                                databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if(task.isSuccessful()){
                                            if(task.getResult().exists()){
                                                DataSnapshot dataSnapshot = task.getResult();
                                                String status = String.valueOf(dataSnapshot.child("status").getValue());
                                                String profile_image = String.valueOf(dataSnapshot.child("idImage").getValue());
                                                String userName = String.valueOf(dataSnapshot.child("name").getValue());

                                                if(status.equals("0")){
                                                    setLoading(false);
                                                    Toast.makeText(EmailLoginActivity.this, "Your account is under verification", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    sharedpreferences = getSharedPreferences("user_shared_prefs", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedpreferences.edit();

                                                    editor.putString("emailKey", email);
                                                    editor.putString("passwordKey", password);
                                                    editor.putString("profile_image", profile_image);
                                                    editor.putString("user_name", userName);
                                                    editor.putString("token", token);

                                                    editor.apply();
                                                    Toast.makeText(EmailLoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                                    setLoading(false);
                                                    Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        }
                                        else{
                                            setLoading(false);
                                            Toast.makeText(EmailLoginActivity.this,"Error fetch user status is null",Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        setLoading(false);
                                        Toast.makeText(EmailLoginActivity.this,"Error fetch user status",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            else{
                                setLoading(false);
                                Toast.makeText(EmailLoginActivity.this, "Fail to login", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else{
                    setLoading(false);
                }
            }
        });

        LoginAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmailLoginActivity.this,LoginOperatorActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setLoading(boolean param){

        if(param==true){
            LoginText.setVisibility(View.GONE);
            ProgressBar.setVisibility(View.VISIBLE);
        }
        else{
            LoginText.setVisibility(View.VISIBLE);
            ProgressBar.setVisibility(View.GONE);
        }

    }
}