package com.example.adutucart5.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adutucart5.R;
import com.example.adutucart5.adapter.CartAdapter;
import com.example.adutucart5.adapter.CartAdapter2;
import com.example.adutucart5.adapter.CustomerProductViewAdapter;
import com.example.adutucart5.adminActivity.WrapContentLinearLayoutManager;
import com.example.adutucart5.model.Cart;
import com.example.adutucart5.model.Cart2;
import com.example.adutucart5.model.Product2;
import com.example.adutucart5.util.localstorage.LocalStorage;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


public class CartActivity extends BaseActivity {
    LocalStorage localStorage;
    List<Cart> cartList = new ArrayList<>();
    Gson gson;
    RecyclerView recyclerView;
    CartAdapter adapter;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    ImageView emptyCart,Star1,Star2,Star3,Star4,Star5;
    LinearLayout checkoutLL,OrderLastAnim;
    TextView totalPrice;
    private DatabaseReference databaseReference;
    CartAdapter2 cartAdapter2;
    private LinearLayout CheckOutBtn,AddressBar;
    private String mState = "SHOW_MENU";
    private EditText Address;
    private RadioGroup RadioGroup;
    private GifImageView GifImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        changeActionBarTitle(getSupportActionBar());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        //upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        localStorage = new LocalStorage(getApplicationContext());
        gson = new Gson();
        emptyCart = findViewById(R.id.empty_cart_img);
        checkoutLL = findViewById(R.id.checkout_LL);
        totalPrice = findViewById(R.id.total_price);
        CheckOutBtn = findViewById(R.id.checkout_btn);
        Address = findViewById(R.id.order_address);
        RadioGroup = findViewById(R.id.group_radio);
        AddressBar = findViewById(R.id.address_bar);
        GifImageView = findViewById(R.id.after_order_animation);
        Star1 = findViewById(R.id.star_1);
        Star2 = findViewById(R.id.star_2);
        Star3 = findViewById(R.id.star_3);
        Star4 = findViewById(R.id.star_4);
        Star5 = findViewById(R.id.star_5);
        OrderLastAnim = findViewById(R.id.order_last_anim);

        Star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Star1.setImageResource(R.drawable.fill_star);
                Toast.makeText(CartActivity.this, "Thank you for rating", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });
        Star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Star1.setImageResource(R.drawable.fill_star);
                Star2.setImageResource(R.drawable.fill_star);
                Toast.makeText(CartActivity.this, "Thank you for rating", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });
        Star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Star1.setImageResource(R.drawable.fill_star);
                Star2.setImageResource(R.drawable.fill_star);
                Star3.setImageResource(R.drawable.fill_star);
                Toast.makeText(CartActivity.this, "Thank you for rating", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });
        Star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Star1.setImageResource(R.drawable.fill_star);
                Star2.setImageResource(R.drawable.fill_star);
                Star3.setImageResource(R.drawable.fill_star);
                Star4.setImageResource(R.drawable.fill_star);
                Toast.makeText(CartActivity.this, "Thank you for rating", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });
        Star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Star1.setImageResource(R.drawable.fill_star);
                Star2.setImageResource(R.drawable.fill_star);
                Star3.setImageResource(R.drawable.fill_star);
                Star4.setImageResource(R.drawable.fill_star);
                Star5.setImageResource(R.drawable.fill_star);
                Toast.makeText(CartActivity.this, "Thank you for rating", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });

        emptyCart.setVisibility(View.VISIBLE);
        checkoutLL.setVisibility(View.GONE);
        AddressBar.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.cart_rv);
        setUpCartRecyclerview(true);

        RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.RadioGroup radioGroup, int i) {
                RadioButton
                        radioButton
                        = (RadioButton)radioGroup
                        .findViewById(i);
            }
        });

        CheckOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = RadioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(CartActivity.this,
                                    "Please select payment type",
                                    Toast.LENGTH_SHORT)
                            .show();
                }
                else {

                    RadioButton radioButton
                            = (RadioButton)RadioGroup
                            .findViewById(selectedId);
                    if(Address.getText().toString().isEmpty()){
                        Toast.makeText(CartActivity.this,
                                        "Please enter address",
                                        Toast.LENGTH_SHORT)
                                .show();
                        Address.setError("Field required");
                        Address.requestFocus();
                    }
                    else {
                        cartAdapter2.CheckOutCart(totalPrice.getText().toString(), Address.getText().toString(), radioButton.getText().toString());
                    }
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        MenuItem item = menu.findItem(R.id.cart_delete);
        if (mState.equalsIgnoreCase("HIDE_MENU")) {
            item.setVisible(false);
        } else {
            item.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart_delete:

                AlertDialog diaBox = showDeleteDialog();
                diaBox.show();

                return true;

            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private AlertDialog showDeleteDialog() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)

                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete All cart")
                .setIcon(R.drawable.ic_delete_black_24dp)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        setUpCartRecyclerview(false);
                        cartCleared();
                        invalidateOptionsMenu();
                        dialog.dismiss();
                    }

                })

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;
    }


    private void changeActionBarTitle(ActionBar actionBar) {
        // Create a LayoutParams for TextView
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        TextView tv = new TextView(getApplicationContext());
        // Apply the layout parameters to TextView widget
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(null, Typeface.BOLD);
        // Set text to display in TextView
        tv.setText("Cart"); // ActionBar title text
        tv.setTextSize(20);

        // Set the text color of TextView to red
        // This line change the ActionBar title text color
        tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        // Set the ActionBar display option
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // Finally, set the newly created TextView as ActionBar custom view
        actionBar.setCustomView(tv);
    }


    private void setUpCartRecyclerview(boolean option) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseRecyclerOptions<Cart2> options = null;
        if(option) {

            recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(mAuth.getCurrentUser().getUid());

            options
                    = new FirebaseRecyclerOptions.Builder<Cart2>()
                    .setQuery(databaseReference, Cart2.class)
                    .build();

            if (options!=null) {
                emptyCart.setVisibility(View.GONE);
                checkoutLL.setVisibility(View.VISIBLE);
                AddressBar.setVisibility(View.VISIBLE);
                recyclerView.setHasFixedSize(true);
                cartAdapter2 = new CartAdapter2(options,this);
                recyclerView.setLayoutManager((new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)));
                recyclerView.setAdapter(cartAdapter2);

            } else {

                mState = "HIDE_MENU";
                invalidateOptionsMenu();
                AddressBar.setVisibility(View.GONE);
                emptyCart.setVisibility(View.VISIBLE);
                checkoutLL.setVisibility(View.GONE);
            }
        }
        else{
            databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(mAuth.getCurrentUser().getUid());
            databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        mState = "HIDE_MENU";
                        invalidateOptionsMenu();
                        emptyCart.setVisibility(View.VISIBLE);
                        checkoutLL.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this,"Cart Deleted",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(CartActivity.this,"Fail to delete cart",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        cartAdapter2.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        cartAdapter2.stopListening();
    }


    public void onCheckoutClicked(View view) {

        startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
    }


    public void cartTotalPrice(int position) {

        totalPrice.setText(String.valueOf(cartAdapter2.getCartTotal(position)));
    }

    public void updateCartTotal(double updatePrice, boolean option){
        double currentTotal = Double.parseDouble(totalPrice.getText().toString());
        double newTotal=0.00;
        if(option) {
            newTotal = currentTotal + updatePrice;
        }
        else{
            newTotal = currentTotal - updatePrice;
        }
        totalPrice.setText( String.valueOf(newTotal));

    }

    public void cartCleared(){
        mState = "HIDE_MENU";
        invalidateOptionsMenu();
        AddressBar.setVisibility(View.GONE);
        emptyCart.setVisibility(View.VISIBLE);
        checkoutLL.setVisibility(View.GONE);
    }
    public void playAnimation(){
        OrderLastAnim.setVisibility(View.VISIBLE);
    }


}
