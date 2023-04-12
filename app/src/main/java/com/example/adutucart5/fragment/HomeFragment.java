package com.example.adutucart5.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.adutucart5.adapter.CategoryAdapter;
import com.example.adutucart5.adapter.CustomerRequestViewAdapter;
import com.example.adutucart5.adapter.HomeProductAdapter;
import com.example.adutucart5.adapter.HomeSliderAdapter;
import com.example.adutucart5.adapter.NewProductAdapter;
import com.example.adutucart5.adapter.PopularProductAdapter;
import com.example.adutucart5.adminActivity.WrapContentLinearLayoutManager;
import com.example.adutucart5.helper.Data;
import com.example.adutucart5.R;
import com.example.adutucart5.activity.MainActivity;
import com.example.adutucart5.model.Category;
import com.example.adutucart5.model.CustomerOrderList;
import com.example.adutucart5.model.Product2;
import com.example.adutucart5.model.Stores;
import com.example.adutucart5.model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
 
/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    Timer timer;
    int page_position = 0;
    Data data;
    private int dotscount;
    private ImageView[] dots;
    private List<Category> categoryList = new ArrayList<>();
    private RecyclerView robinsonRecyclerView, smRecyclerView, victoriaRecyclerView,marketplaceRecyclerView;
    private CategoryAdapter mAdapter;
    private HomeProductAdapter homeProductAdapter,homeProductAdapter2,homeProductAdapter3,homeProductAdapter4;
    private PopularProductAdapter pAdapter;

    private DatabaseReference databaseReference;
    private Integer[] images = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3, R.drawable.slider4, R.drawable.slider5};

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        data = new Data();
        smRecyclerView = view.findViewById(R.id.sm_product_rv);
        robinsonRecyclerView = view.findViewById(R.id.robinson_product_rv);
        victoriaRecyclerView = view.findViewById(R.id.victoria_supermarket_product_rv);
        marketplaceRecyclerView = view.findViewById(R.id.marketplace_product_rv);

        databaseReference = FirebaseDatabase.getInstance().getReference("Stores");

//        mAdapter = new CategoryAdapter(data.getCategoryList(), getContext(), "Home");
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);


        smRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        robinsonRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        victoriaRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        marketplaceRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


        Data data1 = new Data();

        List<Stores> storesList;

        storesList = data1.getStoreList();

//
        Query query = databaseReference.child(storesList.get(0).getStore_name());


        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Product2> options
                = new FirebaseRecyclerOptions.Builder<Product2>()
                .setQuery(query, Product2.class)
                .build();

        // Connecting object of required Adapter class to
        // the Adapter class itself
        homeProductAdapter = new HomeProductAdapter(options,getContext(),"Home",storesList.get(0).getStore_name());
        // Connecting Adapter class with the Recycler view*/
        smRecyclerView.setAdapter(homeProductAdapter);


        Query query2 = databaseReference.child(storesList.get(1).getStore_name());


        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Product2> options2
                = new FirebaseRecyclerOptions.Builder<Product2>()
                .setQuery(query2, Product2.class)
                .build();

        // Connecting object of required Adapter class to
        // the Adapter class itself
        homeProductAdapter2 = new HomeProductAdapter(options2,getContext(),"Home",storesList.get(1).getStore_name());
        // Connecting Adapter class with the Recycler view*/
        robinsonRecyclerView.setAdapter(homeProductAdapter2);

        Query query3 = databaseReference.child(storesList.get(2).getStore_name());


        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Product2> options3
                = new FirebaseRecyclerOptions.Builder<Product2>()
                .setQuery(query3, Product2.class)
                .build();

        // Connecting object of required Adapter class to
        // the Adapter class itself
        homeProductAdapter3 = new HomeProductAdapter(options2,getContext(),"Home",storesList.get(2).getStore_name());
        // Connecting Adapter class with the Recycler view*/
        victoriaRecyclerView.setAdapter(homeProductAdapter3);

        Query query4 = databaseReference.child(storesList.get(3).getStore_name());


        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Product2> options4
                = new FirebaseRecyclerOptions.Builder<Product2>()
                .setQuery(query4, Product2.class)
                .build();

        // Connecting object of required Adapter class to
        // the Adapter class itself
        homeProductAdapter4 = new HomeProductAdapter(options4,getContext(),"Home",storesList.get(3).getStore_name());
        // Connecting Adapter class with the Recycler view*/
        marketplaceRecyclerView.setAdapter(homeProductAdapter4);






        timer = new Timer();
        viewPager = view.findViewById(R.id.viewPager);

        sliderDotspanel = view.findViewById(R.id.SliderDots);

        HomeSliderAdapter viewPagerAdapter = new HomeSliderAdapter(getContext(), images);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        scheduleSlider();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        homeProductAdapter.startListening();
        homeProductAdapter2.startListening();
        homeProductAdapter3.startListening();
        homeProductAdapter4.startListening();
    }





    public void scheduleSlider() {

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == dotscount) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                viewPager.setCurrentItem(page_position, true);
            }
        };

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 4000);
    }

    @Override
    public void onStop() {
        timer.cancel();
        homeProductAdapter.stopListening();
        homeProductAdapter2.stopListening();
        homeProductAdapter3.startListening();
        homeProductAdapter4.startListening();
        super.onStop();
    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }

    public void onLetsClicked(View view) {
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");
    }
}


