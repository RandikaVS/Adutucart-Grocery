package com.example.adutucart5.fragment;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adutucart5.adapter.OfferAdapter;
import com.example.adutucart5.helper.Data;
import com.example.adutucart5.R;
import com.example.adutucart5.model.Offer;

import java.util.ArrayList;
import java.util.List;
 
/**
 * A simple {@link Fragment} subclass.
 */
public class OffrersFragment extends Fragment {

    Data data;
    private List<Offer> offerList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OfferAdapter mAdapter;

    public OffrersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer, container, false);
        recyclerView = view.findViewById(R.id.offer_rv);
        data = new Data();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAdapter = new OfferAdapter(data.getOfferList(), getContext());
        }
        RecyclerView.LayoutManager mLayoutManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mLayoutManager = new LinearLayoutManager(getContext());
        }
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Offer");
    }
}
