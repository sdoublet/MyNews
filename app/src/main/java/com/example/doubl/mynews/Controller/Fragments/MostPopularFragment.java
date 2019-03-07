package com.example.doubl.mynews.Controller.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.doubl.mynews.Controller.Models.ResultMostPopular;
import com.example.doubl.mynews.Controller.Views.RecyclerViews.MostPopularAdapter;
import com.example.doubl.mynews.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostPopularFragment extends Fragment {


    //----------------
    // FOR DESIGN
    //----------------
    @BindView(R.id.recycler_view_most_popular_fragment)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_most_popular)
    SwipeRefreshLayout swipeRefreshLayout;

    //----------------
    // FOR DATA
    //----------------

    private Disposable disposable;
    private List<ResultMostPopular> resultMostPopularList;
    private MostPopularAdapter adapter;

    public MostPopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view= inflater.inflate(R.layout.fragment_most_pouplar, container, false);
        ButterKnife.bind(view);
        this.configureRecyclerView();
        return view;
    }

    private void configureRecyclerView(){
        resultMostPopularList = new ArrayList<>();
        adapter = new MostPopularAdapter(resultMostPopularList, getContext(),Glide.with(this));
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
