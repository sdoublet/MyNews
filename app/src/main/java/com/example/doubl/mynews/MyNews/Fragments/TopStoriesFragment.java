package com.example.doubl.mynews.MyNews.Fragments;


import android.content.Intent;
import android.os.Bundle;
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
import com.example.doubl.mynews.MyNews.Models.ResultTopStories;
import com.example.doubl.mynews.MyNews.Models.TopStories;
import com.example.doubl.mynews.MyNews.Utils.ItemClickSupport;
import com.example.doubl.mynews.MyNews.Utils.NewYorkTimesStream;
import com.example.doubl.mynews.MyNews.Views.RecyclerViews.Divider;
import com.example.doubl.mynews.MyNews.Views.RecyclerViews.TopStoriesAdapter;
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
public class TopStoriesFragment extends Fragment {

    public static final String BUNDLE_URL= "BUNDLE_URL";
    //-------------------
    //FOR DESIGN
    //-------------------
    @BindView(R.id.recycler_view_top_stories)RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_top_stories)SwipeRefreshLayout swipeRefreshLayout;


    //-------------------
    //FOR DATA
    //-------------------

    Disposable disposable;
    private List<ResultTopStories> resultTopStoriesList;
    private TopStoriesAdapter adapter;

    public TopStoriesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view= inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        this.executeHttpRequestWithRetrofit();
        this.configureSwipeRefreshLayout();
        this.configureOnClickRecyclerView();
        return view;
    }

    public void configureRecyclerView() {
        resultTopStoriesList = new ArrayList<>();
        adapter = new TopStoriesAdapter(resultTopStoriesList, getContext(), Glide.with(this));
        recyclerView.addItemDecoration(new Divider(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    //-----------------
    // ACTION
    //-----------------

    // Configure item click on RecyclerView
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.all_fragment_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // Get ResultMostPopular from adapter
                        String articleTopStories;
                        articleTopStories = adapter.getUrl(position);
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra(BUNDLE_URL, articleTopStories);
                        startActivity(intent);


                    }
                });
    }

    //----------------------------
    //HTTP REQUEST WITH RETROFIT
    //----------------------------

    public void executeHttpRequestWithRetrofit() {
        this.disposable = NewYorkTimesStream.streamFetchTopStories("home").subscribeWith(new DisposableObserver<TopStories>() {
            @Override
            public void onNext(TopStories topStories) {
                updateUI(topStories.getResults());
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

    //------------------
    // UPDATE UI
    //------------------
    public void updateUI(List<ResultTopStories> articleTopStrories) {
        swipeRefreshLayout.setRefreshing(false);
        resultTopStoriesList.clear();
        resultTopStoriesList.addAll(articleTopStrories);
        adapter.notifyDataSetChanged();
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
