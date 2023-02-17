package com.example.adutucart5.loginRegister;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adutucart5.Database.AdminDb;
import com.example.adutucart5.R;
import com.example.adutucart5.model.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class RegisterOperatorActivity extends AppCompatActivity {

    private EditText Email,Password,Name,Tel;
    private RelativeLayout RegisterBtn;

    private ImageView ProfileImage;

    private ImageView SelectedImage;

    private LinearLayout SelectedImageLayout;

    private ImageView CancelImage,Back;

    private ProgressBar RegProgressBar;

    private TextView RegisterText;
    Uri imageUri = null;
    FirebaseStorage storage = FirebaseStorage.getInstance();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_operator);
        getSupportActionBar().hide();

        Email = findViewById(R.id.email_editText);
        Password = findViewById(R.id.password_editText);
        Name = findViewById(R.id.name_editText);
        Tel = findViewById(R.id.cellphone_editText);
        ProfileImage = findViewById(R.id.profileImage);
        RegisterBtn = findViewById(R.id.registerBtn);
        SelectedImage = findViewById(R.id.selectedImage);
        SelectedImageLayout = findViewById(R.id.selectedImageLayout);
        CancelImage = findViewById(R.id.cancelImage);
        RegProgressBar = findViewById(R.id.reg_progress_bar);
        RegisterText = findViewById(R.id.register_text);
        Back = findViewById(R.id.back_arrow_admin_register);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterOperatorActivity.this,EmailRegistrationActivity.class));
            }
        });

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        CancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedImage.setImageURI(null);
                SelectedImageLayout.setVisibility(View.GONE);
                ProfileImage.setVisibility(View.VISIBLE);
            }
        });

        AdminDb adminDb = new AdminDb();

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setLoading(true);

                String email,name,password,tel;

                email = Email.getText().toString().trim();
                name  = Name.getText().toString().trim();
                password = Password.getText().toString().trim();
                tel = Tel.getText().toString().trim();
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
                if(name.isEmpty()){
                    Name.setError("Field required!!!");
                    Name.requestFocus();
                    isSuccess = false;
                }
                if(password.isEmpty()){
                    Password.setError("Field required!!!");
                    Password.requestFocus();
                    isSuccess = false;
                }
                if(tel.isEmpty()){
                    Tel.setError("Field required!!!");
                    Tel.requestFocus();
                    isSuccess = false;
                }

                if(imageUri == null){
                    Toast.makeText(RegisterOperatorActivity.this,"Please upload image",Toast.LENGTH_SHORT).show();
                    isSuccess = false;
                }

                if(isSuccess){

                    if(imageUri !=null) {

                        StorageReference storageRef = storage.getReference().child("ImageFolder" );

                        StorageReference ImageName = storageRef.child("adminImage/"+ UUID.randomUUID().toString());

                        ImageName.putFile(imageUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(RegisterOperatorActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String url = String.valueOf(uri);
                                                Admin admin = new Admin(name,email,tel,password,url);

                                                adminDb.adminAuth(admin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if(task.isSuccessful()){
                                                            adminDb.addAdmin(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    if(task.isSuccessful()) {
                                                                        Toast.makeText(RegisterOperatorActivity.this, "Admin Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(RegisterOperatorActivity.this, LoginOperatorActivity.class));
                                                                        setLoading(false);
                                                                    }
                                                                    else{
                                                                        setLoading(false);
                                                                        Toast.makeText(RegisterOperatorActivity.this, "Fail to register admin", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                }
                                                            });
                                                        }
                                                        else{
                                                            setLoading(false);
                                                            Toast.makeText(RegisterOperatorActivity.this,"Fail to Auth admin",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterOperatorActivity.this, "Image Upload failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else{
                        Toast.makeText(RegisterOperatorActivity.this, "Image URI is null", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    setLoading(false);
                }



            }
        });
    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            ProfileImage.setVisibility(View.GONE);
            SelectedImageLayout.setVisibility(View.VISIBLE);
            SelectedImage.setImageURI(imageUri);

        }
    }
//    private boolean uploadImage(){
//
//
//        if(imageUri !=null) {
//
//            StorageReference storageRef = storage.getReferenceFromUrl("images/" + UUID.randomUUID().toString());
//
//            storageRef.putFile(imageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(RegisterOperatorActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(RegisterOperatorActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
//        else{
//            Toast.makeText(RegisterOperatorActivity.this, "Image URI is null", Toast.LENGTH_SHORT).show();
//        }
//
//            return true;
//
//    }

    private void setLoading(boolean param){

        if(param==true){
            RegisterText.setVisibility(View.GONE);
            RegProgressBar.setVisibility(View.VISIBLE);
        }
        else{
            RegisterText.setVisibility(View.VISIBLE);
            RegProgressBar.setVisibility(View.GONE);
        }

    }
}