

package com.example.adutucart5.loginRegister;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adutucart5.Database.UserDb;
import com.example.adutucart5.R;
import com.example.adutucart5.model.Admin;
import com.example.adutucart5.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class EmailRegistrationActivity extends AppCompatActivity {

    private Button RegisterAsAdminBtn,UserRegisterBtn;
    private EditText Name,Email,Phone,Password;
    private ImageView idImage,SelectedImage,CancelImage;
    private Uri imageUri = null;
    private ProgressBar ProgressBar;


    private LinearLayout SelectedImageLayout;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_registration);

        RegisterAsAdminBtn = findViewById(R.id.registerOperator);
        Name = findViewById(R.id.user_name);
        Email = findViewById(R.id.email_editText);
        Password = findViewById(R.id.password_editText);
        Phone = findViewById(R.id.cellphone_editText);
        UserRegisterBtn = findViewById(R.id.registerBtn);
        idImage = findViewById(R.id.user_login_profile_image);
        ProgressBar = findViewById(R.id.user_reg_progress);
        SelectedImage = findViewById(R.id.user_selected_image);
        SelectedImageLayout = findViewById(R.id.user_selected_imageLayout);
        CancelImage = findViewById(R.id.user_cancelImage);

        CancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedImage.setImageURI(null);
                SelectedImageLayout.setVisibility(View.GONE);
                idImage.setVisibility(View.VISIBLE);
            }
        });


        idImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        UserDb userDb = new UserDb();
        UserRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLoading(true);

                String email,name,password,phone;

                email = Email.getText().toString().trim();
                name  = Name.getText().toString().trim();
                password = Password.getText().toString().trim();
                phone = Phone.getText().toString().trim();
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
                if(phone.isEmpty()){
                    Phone.setError("Field required!!!");
                    Phone.requestFocus();
                    isSuccess = false;
                }

                if(imageUri == null){
                    Toast.makeText(EmailRegistrationActivity.this,"Please upload image",Toast.LENGTH_SHORT).show();
                    isSuccess = false;
                }

                if(isSuccess){

                    if(imageUri !=null) {

                        StorageReference storageRef = storage.getReference().child("ImageFolder" );

                        StorageReference ImageName = storageRef.child("userImage/"+ UUID.randomUUID().toString());

                        ImageName.putFile(imageUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(EmailRegistrationActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String url = String.valueOf(uri);
                                                User user = new User(name,email,phone,password,url);

                                                userDb.userAuth(user).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if(task.isSuccessful()){
                                                            userDb.addUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    if(task.isSuccessful()) {
                                                                        Toast.makeText(EmailRegistrationActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(EmailRegistrationActivity.this, EmailLoginActivity.class));
                                                                        setLoading(false);
                                                                    }
                                                                    else{
                                                                        setLoading(false);
                                                                        Toast.makeText(EmailRegistrationActivity.this, "Fail to register user", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                }
                                                            });
                                                        }
                                                        else{
                                                            setLoading(false);
                                                            Toast.makeText(EmailRegistrationActivity.this,"Fail to Auth user",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EmailRegistrationActivity.this, "Image Upload failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else{
                        Toast.makeText(EmailRegistrationActivity.this, "Image URI is null", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    setLoading(false);
                }
            }
        });

        RegisterAsAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmailRegistrationActivity.this,RegisterOperatorActivity.class);
                startActivity(intent);
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
            idImage.setVisibility(View.GONE);
            SelectedImageLayout.setVisibility(View.VISIBLE);
            SelectedImage.setImageURI(imageUri);

        }
    }

    private void setLoading(boolean param){

        if(param==true){
            UserRegisterBtn.setVisibility(View.GONE);
            ProgressBar.setVisibility(View.VISIBLE);
        }
        else{
            UserRegisterBtn.setVisibility(View.VISIBLE);
            ProgressBar.setVisibility(View.GONE);
        }

    }
}