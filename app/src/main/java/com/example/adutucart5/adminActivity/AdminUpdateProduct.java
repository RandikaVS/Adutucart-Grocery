package com.example.adutucart5.adminActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adutucart5.Database.ProductDb;
import com.example.adutucart5.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class AdminUpdateProduct extends AppCompatActivity {

    private ImageView ProductImage,ViewStoreBackBtn,Imageremove;
    private TextInputEditText Title,Description,Qty,Price,Discount;
    private Button Update,Delete;

    private String key;
    private String store;
    private DatabaseReference ProductRef;
    private ProgressBar progressBar;
    private Uri imageUri=null;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    private String CurrentUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_product);

        ProductImage = findViewById(R.id.update_product_image);
        Title = findViewById(R.id.update_product_title);
        Description = findViewById(R.id.update_product_description);
        Qty = findViewById(R.id.update_product_qty);
        Price = findViewById(R.id.update_product_price);
        Discount = findViewById(R.id.update_product_discount);
        Update = findViewById(R.id.product_update_btn);
        Delete = findViewById(R.id.product_delete_btn);
        ViewStoreBackBtn = findViewById(R.id.view_store_back_btn);
        progressBar = findViewById(R.id.update_product_progress_bar);
        Imageremove = findViewById(R.id.image_remove);


        Bundle extra = getIntent().getExtras();
        if(extra!=null) {
            key = extra.getString("product_key");
        }

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        // The value will be default as empty string because for
        // the very first time when the app is opened, there is nothing to show
        store = sh.getString("store", "");

        ViewStoreBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminUpdateProduct.this,AdminViewStore.class);
                intent.putExtra("storeName",store);
                startActivity(intent);
            }
        });

        getProductDetails();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateProduct();
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the object of AlertDialog Builder class
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminUpdateProduct.this);

                // Set the message show for the Alert time
                builder.setMessage("Do you want to delete ?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    ProductDb productDb = new ProductDb();
                    productDb.deleteProduct(key,store).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AdminUpdateProduct.this,"Product Deleted",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdminUpdateProduct.this,AdminViewStore.class);
                                intent.putExtra("storeName",store);
                                startActivity(intent);

                            }
                            else{
                                Toast.makeText(AdminUpdateProduct.this,"Fail to delete product",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                });

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();
            }
        });

        Imageremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductImage.setImageResource(R.drawable.add_icon);
            }
        });

        ProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("storeName", store);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
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
            ProductImage.setImageURI(imageUri);

        }
    }

    public void UpdateProduct(){

        progressBar.setVisibility(View.VISIBLE);

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
        if(imageUri == null && CurrentUrl.isEmpty()){
            Toast.makeText(AdminUpdateProduct.this,"Please upload image",Toast.LENGTH_SHORT).show();
            isSuccess = false;
        }


        if(isSuccess){
            StorageReference storageRef = storage.getReference().child("ImageFolder" );

            StorageReference ImageName = storageRef.child("productImage/"+ UUID.randomUUID().toString());

            if(imageUri!=null){
                ImageName.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AdminUpdateProduct.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = String.valueOf(uri);
                                HashMap<String,Object> hashMap = new HashMap<>();
                                hashMap.put("title",title);
                                hashMap.put("description",description);
                                hashMap.put("price",price);
                                hashMap.put("qty",qty);
                                hashMap.put("discount",discount);
                                hashMap.put("image",url);

                                ProductDb productDb = new ProductDb();
                                productDb.updateProduct(store,key,hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AdminUpdateProduct.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AdminUpdateProduct.this,AdminViewStore.class);
                                            intent.putExtra("storeName",store);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(AdminUpdateProduct.this, "Updating Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AdminUpdateProduct.this, "Update failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AdminUpdateProduct.this, "Image Upload failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("title",title);
                hashMap.put("description",description);
                hashMap.put("price",price);
                hashMap.put("qty",qty);
                hashMap.put("discount",discount);
                hashMap.put("image",CurrentUrl);

                ProductDb productDb = new ProductDb();
                productDb.updateProduct(store,key,hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminUpdateProduct.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminUpdateProduct.this,AdminViewStore.class);
                            intent.putExtra("storeName",store);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AdminUpdateProduct.this, "Updating Failed!", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AdminUpdateProduct.this, "Updating Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        else{
            progressBar.setVisibility(View.GONE);
        }

    }
    public void getProductDetails(){

        ProductRef = FirebaseDatabase.getInstance().getReference("Stores").child(store)
                .child(key);

        ProductRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();

                        Title.setText(String.valueOf(dataSnapshot.child("title").getValue()));
                        Description.setText(String.valueOf(dataSnapshot.child("description").getValue()));
                        Qty.setText(String.valueOf(dataSnapshot.child("qty").getValue()));
                        Price.setText(String.valueOf(dataSnapshot.child("price").getValue()));
                        Discount.setText(String.valueOf(dataSnapshot.child("discount").getValue()));
                        Glide.with(AdminUpdateProduct.this).load(String.valueOf(dataSnapshot.child("image").getValue())).into(ProductImage);
                        CurrentUrl = String.valueOf(dataSnapshot.child("image").getValue());

                    }
                    else{
                        Toast.makeText(AdminUpdateProduct.this,"Error fetch data result", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AdminUpdateProduct.this,"Error fetch data unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}