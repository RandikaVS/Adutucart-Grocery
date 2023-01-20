package com.example.adutucart5.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.adutucart5.R;


import org.jetbrains.annotations.NonNls;

public class ProductListOperatorFrag extends Fragment {

    EditText id;
    EditText categoryId;
    EditText title;
    EditText description;
    EditText attribute;
    EditText currency;
    EditText price;
    EditText discount;
    EditText image;
    Button add;
    Button delete;
    Button update;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @NonNls
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_product_crud, container, false);
        id = view.findViewById(R.id.product_id);
        categoryId = view.findViewById(R.id.category_id);
        title = view.findViewById(R.id.product_title);
        description = view.findViewById(R.id.product_desc);
        attribute = view.findViewById(R.id.product_attribute);
        price = view.findViewById(R.id.price);
        currency = view.findViewById(R.id.currency);
        discount = view.findViewById(R.id.product_discount_op);
        image = view.findViewById(R.id.product_image_url);
        add = view.findViewById(R.id.add_button);
        delete = view.findViewById(R.id.delete_btn);
        update = view.findViewById(R.id.update_button);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String Id = id.getText().toString();
//                String CategoryId = categoryId.getText().toString();
//                String Title = title.getText().toString();
//                String Description = description.getText().toString();
//                String Attribute = attribute.getText().toString();
//                String Price = price.getText().toString();
//                String Discount = discount.getText().toString();
//                String Currency = currency.getText().toString();
//                String Image = image.getText().toString();

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Products");
    }
}



