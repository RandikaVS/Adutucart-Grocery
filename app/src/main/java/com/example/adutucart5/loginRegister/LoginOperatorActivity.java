package com.example.adutucart5.loginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adutucart5.Database.AdminDb;
import com.example.adutucart5.R;
import com.example.adutucart5.adminActivity.AdminDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginOperatorActivity extends AppCompatActivity {

    private ConstraintLayout LoginBtn;
    private ProgressBar progressBar;
    private EditText Email,Password;
    private TextView LoginText;
    private ImageView Back;

    SharedPreferences sharedpreferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_operator);
        getSupportActionBar().hide();

        LoginBtn = findViewById(R.id.loginOperator);
        progressBar = findViewById(R.id.progressBar);
        Email = findViewById(R.id.email_editText);
        Password = findViewById(R.id.password_editText);
        LoginText = findViewById(R.id.textView2);
        Back = findViewById(R.id.back_arrow_admin_login);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginOperatorActivity.this,EmailLoginActivity.class));
            }
        });

        AdminDb adminDb = new AdminDb();

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

                    adminDb.adminSignIn(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.putString("emailKey", email);
                                editor.putString("passwordKey", password);

                                editor.apply();
                                Toast.makeText(LoginOperatorActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                setLoading(false);
                                Intent intent = new Intent(LoginOperatorActivity.this, AdminDashboard.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                setLoading(false);
                                Toast.makeText(LoginOperatorActivity.this, "Fail to login", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else{
                    setLoading(false);
                }
            }
        });


    }

    private void setLoading(boolean param){

        if(param==true){
            LoginText.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            LoginText.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

    }

}