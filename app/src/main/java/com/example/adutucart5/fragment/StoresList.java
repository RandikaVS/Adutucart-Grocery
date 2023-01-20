package com.example.adutucart5.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.ProgressBar;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.DefaultItemAnimator;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.adutucart5.R;
        import com.example.adutucart5.adapter.OfferAdapter;
        import com.example.adutucart5.adapter.StoresAdapter;
        import com.example.adutucart5.helper.Data;
        import com.example.adutucart5.model.Offer;
        import com.example.adutucart5.model.Stores;
        import com.squareup.picasso.Callback;
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;
        import java.util.List;

public class StoresList extends Fragment {

    Data data;
    private ArrayList<Stores> storesArrayList = new ArrayList<>();

    RecyclerView recyclerView;

    StoresAdapter mAdapter;



    public StoresList() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.stores_list, container, false);
        recyclerView = view.findViewById(R.id.RecylerList);
        data = new Data();
        mAdapter = new StoresAdapter(data.getStoreList(),getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Stores");
    }
}


