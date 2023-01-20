

package com.example.adutucart5.loginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adutucart5.R;

public class EmailRegistrationActivity extends AppCompatActivity {

    private Button RegisterAsAdminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_registration);

        RegisterAsAdminBtn = findViewById(R.id.registerOperator);

        RegisterAsAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmailRegistrationActivity.this,RegisterOperatorActivity.class);
                startActivity(intent);
            }
        });
    }
}