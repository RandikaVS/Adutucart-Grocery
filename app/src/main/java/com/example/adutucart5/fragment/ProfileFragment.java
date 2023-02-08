package com.example.adutucart5.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.adutucart5.Database.UserDb;
import com.example.adutucart5.R;
import com.example.adutucart5.activity.SplashScreen;
import com.example.adutucart5.customfonts.MyTextViewInterRegular;
import com.example.adutucart5.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        return view;
    }

    private MyTextViewInterRegular Email;
    private EditText Name,Address,Mobile;
    private Button Update;
    private TextView DeleteAccount,ChangeImage;
    private DatabaseReference userRef;
    private CircleImageView UserImage;
    private Uri imageUri;
    private ProgressBar ProgressBar;
    private String CurrentUrl;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");

        Name = view.findViewById(R.id.user_profile_name);
        Email = view.findViewById(R.id.user_profile_email);
        Address = view.findViewById(R.id.user_profile_address);
        Mobile = view.findViewById(R.id.user_profile_mobile);
        Update = view.findViewById(R.id.update_account_btn);
        DeleteAccount = view.findViewById(R.id.delete_account);
        UserImage = view.findViewById(R.id.user_image);
        ChangeImage = view.findViewById(R.id.image_change_btn);
        ProgressBar = view.findViewById(R.id.user_profile_progress);

        getUseDetails();

        ChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });

        DeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });



    }

    private void updateUser(){
        setLoading(true);
        String name,mobile,address;

        name = Name.getText().toString().trim();
        mobile = Mobile.getText().toString().trim();
        address = Address.getText().toString().trim();
        boolean isSuccess = true;

        if(name.isEmpty()){
            Name.setError("Field required");
            Name.requestFocus();
            isSuccess=false;
        }
        if(mobile.isEmpty()){
            Mobile.setError("Field required");
            Mobile.requestFocus();
            isSuccess=false;
        }

        if(address.isEmpty()){
            Address.setError("Field required");
            Address.requestFocus();
            isSuccess=false;
        }
        if(imageUri == null && CurrentUrl.isEmpty()){
            Toast.makeText(getContext(),"Please upload image",Toast.LENGTH_SHORT).show();
            isSuccess = false;
        }

        if(isSuccess){
            StorageReference storageRef = storage.getReference().child("ImageFolder" );

            StorageReference ImageName = storageRef.child("userImage/"+ UUID.randomUUID().toString());

            if(imageUri!=null){
                ImageName.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();

                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = String.valueOf(uri);
                                HashMap<String,Object> hashMap = new HashMap<>();
                                hashMap.put("name",name);
                                hashMap.put("mobile",mobile);
                                hashMap.put("address",address);
                                hashMap.put("idImage",url);

                                UserDb userDb = new UserDb();
                                userDb.userUpdate(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(getContext(), "Updating Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                        setLoading(false);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        setLoading(false);
                                        Toast.makeText(getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        setLoading(false);
                        Toast.makeText(getContext(), "Image Upload failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("name",name);
                hashMap.put("mobile",mobile);
                hashMap.put("address",address);


                UserDb userDb = new UserDb();
                userDb.userUpdate(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Updating Failed!", Toast.LENGTH_SHORT).show();
                        }
                        setLoading(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        setLoading(false);
                        Toast.makeText(getContext(), "Updating Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        else{
            setLoading(false);
        }
    }

    private void deleteUser(){
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Set the message show for the Alert time
        builder.setMessage("Do you want to delete account ?");
        builder.setIcon(R.drawable.warning);

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            UserDb userDb = new UserDb();
            userDb.userDelete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(),"User Deleted",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), SplashScreen.class);
                        startActivity(intent);
                        getActivity().finish();

                    }
                    else{
                        Toast.makeText(getContext(),"Fail to delete user",Toast.LENGTH_SHORT).show();
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
    private void setLoading(boolean status){
        if(status) {
            ProgressBar.setVisibility(View.VISIBLE);
        }
        else{
            ProgressBar.setVisibility(View.GONE);
        }
    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            if(imageUri==null){
                UserImage.setImageResource(R.drawable.no_image);
            }
            else {
                UserImage.setImageURI(imageUri);
            }

        }
    }

    public void getUseDetails(){

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userRef = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName()).child(currentFirebaseUser.getUid());

        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();

                        Name.setText(String.valueOf(dataSnapshot.child("name").getValue()));
                        Email.setText(String.valueOf(dataSnapshot.child("email").getValue()));
                        Mobile.setText(String.valueOf(dataSnapshot.child("mobile").getValue()));
                        CurrentUrl = String.valueOf(dataSnapshot.child("idImage").getValue());
                        Glide.with(ProfileFragment.this)
                                .load(String.valueOf(dataSnapshot.child("idImage").getValue()))
                                .placeholder(R.drawable.no_image).into(UserImage);

                        if(String.valueOf(dataSnapshot.child("address").getValue())==null){
                            Address.setText("No Address");
                        }
                        else{
                            Address.setText(String.valueOf(dataSnapshot.child("address").getValue()));
                        }


                    }
                    else{
                        Toast.makeText(getActivity(),"Error fetch data result", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Error fetch data unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
