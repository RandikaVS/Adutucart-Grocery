package com.example.adutucart5.adminActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adutucart5.Database.ProductDb;
import com.example.adutucart5.R;
import com.example.adutucart5.model.Product2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AdminAddProduct extends AppCompatActivity {

    private String key;
    private ImageView BackBtn,AddImage;
    private TextInputEditText Title,Description,Qty,Price,Currency,Discount;
    private Button AddBtn;
    private Uri imageUri = null;

    private ProgressBar progressBar;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        BackBtn = findViewById(R.id.back_btn);
        AddImage = findViewById(R.id.add_image_btn);
        AddBtn = findViewById(R.id.add_btn);
        Title = findViewById(R.id.product_title);
        Description = findViewById(R.id.product_description);
        Qty = findViewById(R.id.product_qty);
        Price = findViewById(R.id.product_price);
        Discount = findViewById(R.id.product_discount);
        progressBar = findViewById(R.id.add_product_progress_bar);

        Bundle extra = getIntent().getExtras();
        key = extra.getString("storeName");


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAddProduct.this,AdminViewStore.class);
                intent.putExtra("storeName",key);
                startActivity(intent);
            }
        });

        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        ProductDb productDb = new ProductDb();

        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setLoading(true);

                String title,description,qty,discount,price;

                title = Title.getText().toString().trim();
                description = Description.getText().toString().trim();
                qty = Qty.getText().toString().trim();
                price = Price.getText().toString().trim();
                discount = Discount.getText().toString().trim();

                boolean isSuccess = true;

                if(title.isEmpty()){
                    Title.setError("Field required");
                    Title.requestFocus();
                    isSuccess=false;
                }
                if(description.isEmpty()){
                    Description.setError("Field required");
                    Description.requestFocus();
                    isSuccess=false;
                }

                if(qty.isEmpty()){
                    Qty.setError("Field required");
                    Qty.requestFocus();
                    isSuccess=false;
                }
                if(price.isEmpty()){
                    Price.setError("Field required");
                    Price.requestFocus();
                    isSuccess=false;
                }
                if(discount.isEmpty()){
                    Discount.setError("Field required");
                    Discount.requestFocus();
                    isSuccess=false;
                }
                if(imageUri == null){
                    Toast.makeText(AdminAddProduct.this,"Please upload image",Toast.LENGTH_SHORT).show();
                    isSuccess = false;
                }

                if(isSuccess){
                    StorageReference storageRef = storage.getReference().child("ImageFolder" );

                    StorageReference ImageName = storageRef.child("productImage/"+ UUID.randomUUID().toString());

                    ImageName.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(AdminAddProduct.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                            ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = String.valueOf(uri);
                                    Product2 product = new Product2(title,description,qty,price,discount,url);
                                    productDb.addProduct(product,key).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(AdminAddProduct.this, "Product published", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(AdminAddProduct.this, AdminViewStore.class).putExtra("storeName",key));
                                                setLoading(false);
                                            }
                                            else{
                                                setLoading(false);
                                                Toast.makeText(AdminAddProduct.this, "Fail to add product", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            setLoading(false);
                            Toast.makeText(AdminAddProduct.this, "Image Upload failed", Toast.LENGTH_SHORT).show();
                        }
                    });
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
            AddImage.setImageURI(imageUri);

        }
    }
    private void setLoading(boolean param){

        if(param==true){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.GONE);
        }

    }
}