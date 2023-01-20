package com.example.adutucart5.loginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.adutucart5.R;

public class EmailLoginActivity extends AppCompatActivity {

    private RelativeLayout LoginAdminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        LoginAdminBtn = findViewById(R.id.loginOperator);

        LoginAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmailLoginActivity.this,LoginOperatorActivity.class);
                startActivity(intent);
            }
        });
    }
}