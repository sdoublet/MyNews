package com.example.doubl.mynews.Controller.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.doubl.mynews.Controller.Models.MostPopular;
import com.example.doubl.mynews.Controller.Models.ResultMostPopular;
import com.example.doubl.mynews.Controller.Utils.NewYorkTimesStream;
import com.example.doubl.mynews.Controller.Views.RecyclerViews.MostPopularAdapter;
import com.example.doubl.mynews.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

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

     Disposable disposable;
    private List<ResultMostPopular> resultMostPopularList;
    private MostPopularAdapter adapter;

    public MostPopularFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_most_pouplar, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        this.executeHttpRequestWithRetrofit();
        this.configureSwipeRefreshLayout();
        return view;
    }

    public void configureRecyclerView() {
        resultMostPopularList = new ArrayList<>();
        adapter = new MostPopularAdapter(resultMostPopularList, getContext(), Glide.with(this));
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //------------------
    // UPDATE UI
    //------------------
    public void updateUI(List<ResultMostPopular> article) {
        swipeRefreshLayout.setRefreshing(false);
        resultMostPopularList.clear();
        resultMostPopularList.addAll(article);
        adapter.notifyDataSetChanged();
    }

    //----------------------------
    //HTTP REQUEST WITH RETROFIT
    //----------------------------

    private void executeHttpRequestWithRetrofit() {
        this.disposable = NewYorkTimesStream.streamFetchMostPopular(1).subscribeWith(new DisposableObserver<MostPopular>() {
            @Override
            public void onNext(MostPopular mostPopular) {
                updateUI(mostPopular.getResults());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "On Error..."+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "On complete!");
            }
        });
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }


}
