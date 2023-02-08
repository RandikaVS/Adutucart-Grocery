package com.example.adutucart5.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adutucart5.Database.AdminDb;
import com.example.adutucart5.R;
import com.example.adutucart5.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;


public class CustomerRequestViewAdapter extends FirebaseRecyclerAdapter<User, CustomerRequestViewAdapter.taskViewHolder> {

    private Context context;
    public CustomerRequestViewAdapter(@NonNull FirebaseRecyclerOptions<User> options,Context context) {
        super(options);
        this.context = context;
    }

    LayoutInflater inflater;

    protected void onBindViewHolder(@NonNull CustomerRequestViewAdapter.taskViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull User model) {

        holder.Email.setText(model.getEmail());

        Glide.with(holder.itemView).load(model.getIdImage()).into(holder.CustomerIdImage);

        holder.Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key =  getRef(position).getKey();
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("status","1");

                AdminDb adminDb = new AdminDb();
                adminDb.ApproveUser(key,hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(view.getContext(), "User Approved!!!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(view.getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key =  getRef(position).getKey();
                // Create the object of AlertDialog Builder class
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                // Set the message show for the Alert time
                builder.setMessage("Do you want to remove user ?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    AdminDb adminDb = new AdminDb();
                    adminDb.deleteUser(key).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(view.getContext(),"User Deleted",Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(view.getContext(),"Fail to delete user",Toast.LENGTH_SHORT).show();
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

        holder.CustomerIdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key =  getRef(position).getKey();
                Context context = view.getContext();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final AlertDialog dialog = builder.create();
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogLayout = inflater.inflate(R.layout.id_popup_layout, null);
                dialog.setView(dialogLayout);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                ImageView image = (ImageView) dialogLayout.findViewById(R.id.id_image);
                image.setImageResource(R.drawable.store_icon);
                Glide.with(view.getContext()).load(model.getIdImage()).into(image);

                dialog.show();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog.findViewById(R.id.id_image);
                        image.setImageResource(R.drawable.store_icon);
//                        Glide.with(view.getContext()).load("http://i.imgur.com/DvpvklR.png").into(image);

                        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.store_icon);
                        float imageWidthInPX = (float)image.getWidth();

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                                Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                        image.setLayoutParams(layoutParams);


                    }
                });

            }
        });

    }


    @NonNull
    @Override
    public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_request_item, parent, false);
        return new CustomerRequestViewAdapter.taskViewHolder(view);
    }




    class taskViewHolder extends RecyclerView.ViewHolder {

        TextView Email;
        ImageView CustomerIdImage;
        Button Approve,Reject;
        TableLayout CustomerCard;

        public taskViewHolder(@NonNull View itemView) {
            super(itemView);

            Email = itemView.findViewById(R.id.customer_email);
            CustomerIdImage = itemView.findViewById(R.id.customer_id_image);
            Approve = itemView.findViewById(R.id.approve_btn);
            Reject = itemView.findViewById(R.id.reject_btn);
            CustomerCard = itemView.findViewById(R.id.customer_request_item);
        }
    }
}
