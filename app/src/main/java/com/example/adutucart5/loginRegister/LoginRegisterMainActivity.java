package com.example.adutucart5.loginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.adutucart5.R;

public class LoginRegisterMainActivity extends AppCompatActivity {

    private TextView LoginBtn,RegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_main);

        LoginBtn = findViewById(R.id.login_btn);
        RegisterBtn = findViewById(R.id.register_btn);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginRegisterMainActivity.this,EmailLoginActivity.class);
                startActivity(intent);
            }
        });
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginRegisterMainActivity.this,EmailRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}