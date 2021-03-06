package com.example.doubl.mynews.MyNews.Fragments;


import android.content.Intent;
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
import com.example.doubl.mynews.MyNews.Activities.WebViewActivity;
import com.example.doubl.mynews.MyNews.Models.MostPopular;
import com.example.doubl.mynews.MyNews.Models.ResultMostPopular;
import com.example.doubl.mynews.MyNews.Utils.ItemClickSupport;
import com.example.doubl.mynews.MyNews.Utils.NewYorkTimesStream;
import com.example.doubl.mynews.MyNews.Views.RecyclerViews.Divider;
import com.example.doubl.mynews.MyNews.Views.RecyclerViews.MostPopularAdapter;
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

public static final String BUNDLE_URL= "BUNDLE_URL";
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
        this.configureOnClickRecyclerView();
        return view;
    }

    public void configureRecyclerView() {
        resultMostPopularList = new ArrayList<>();
        adapter = new MostPopularAdapter(resultMostPopularList, getContext(), Glide.with(this) );
        recyclerView.addItemDecoration(new Divider(getContext(), LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //-----------------
    // ACTION
    //-----------------

    /**
     * Display article selected in the webView
     */
    // Configure item click on RecyclerView
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.all_fragment_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // Get ResultMostPopular from adapter
                        String articleMostPopular;
                        articleMostPopular = adapter.getUrl(position);
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra(BUNDLE_URL, articleMostPopular);
                        startActivity(intent);


                    }
                });
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

    /**
     * Execute http request
     * Use Retrofit to convert json format and execute asyncTask in the main thread
     */
    public void executeHttpRequestWithRetrofit() {
        this.disposable = NewYorkTimesStream.streamFetchMostPopular(7).subscribeWith(new DisposableObserver<MostPopular>() {
            @Override
            public void onNext(MostPopular mostPopular) {
                updateUI(mostPopular.getResults());
                System.out.println(mostPopular.getResults().size());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "On Error..." + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "On complete!");
            }
        });
    }

    /**
     * Refresh current page
     */
    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }


}
