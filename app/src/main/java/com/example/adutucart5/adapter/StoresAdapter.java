package com.example.adutucart5.adapter;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adutucart5.R;
import com.example.adutucart5.fragment.ConfirmFragment;
import com.example.adutucart5.fragment.ProductListOperatorFrag;
import com.example.adutucart5.fragment.StoresList;
import com.example.adutucart5.model.Stores;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.Viewholder> {

    Context context;
    ArrayList<Stores> storesArrayList;
    StoresList storesList = new StoresList();

    public StoresAdapter(List<Stores> storesArrayList, Context context) {
        this.context = context;
        this.storesArrayList = (ArrayList<Stores>) storesArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_stores, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, @SuppressLint("RecyclerView") int position) {

        Stores stores = storesArrayList.get(position);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (position) {
                    case 0:
                        AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                        FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
                        ft.replace(R.id.content_frame, new ProductListOperatorFrag());
                        ft.commit();
                        break;
                    case 1:{
                        Toast.makeText(context,
                                "Constraint clicked", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        Picasso.get().load(stores.getImage()).error(R.drawable.no_image).into(holder.store_image, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return storesArrayList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        public ImageView store_image;
        public TextView store_name;
        public ProgressBar progressBar;
        ConstraintLayout constraintLayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            store_image = itemView.findViewById(R.id.imageView2);
            progressBar = itemView.findViewById(R.id.progressbar);
            store_name = itemView.findViewById(R.id.textView_storename);
            constraintLayout = itemView.findViewById(R.id.click_layout);
        }
    }
}


//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            imageView = itemView.findViewById(R.id.offer_image);
//            progressBar = itemView.findViewById(R.id.progressbar);
//
//        }    }
//
//
//        public void ViewHolder(@NonNull View itemView) {
//
//            store_name = itemView.findViewById(R.id.textView_storename);
//            store_image = itemView.findViewById(R.id.imageView2);
//            progressBar = itemView.findViewById(R.id.progressbar);
//
//        }
//
//        public Viewholder(@NonNull View itemView) {
//
//            super(itemView);
//
//        }
//
//
//        public static class MyViewHolder extends RecyclerView.ViewHolder {
//            public ImageView imageView;
//            public ProgressBar progressBar;
//
//}
