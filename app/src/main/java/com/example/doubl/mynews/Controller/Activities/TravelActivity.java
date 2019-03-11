package com.example.doubl.mynews.Controller.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.doubl.mynews.Controller.Models.ResultTopStories;
import com.example.doubl.mynews.Controller.Models.TopStories;
import com.example.doubl.mynews.Controller.Utils.ItemClickSupport;
import com.example.doubl.mynews.Controller.Utils.NewYorkTimesStream;
import com.example.doubl.mynews.Controller.Views.RecyclerViews.TopStoriesAdapter;
import com.example.doubl.mynews.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class TravelActivity extends AppCompatActivity {

    public static final String BUNDLE_URL= "BUNDLE_URL";
    //-------------------
    //FOR DESIGN
    //-------------------
    @BindView(R.id.recycler_view_travel)
    RecyclerView recyclerView;


    public String sectionOtherFragment = "travel";
    //-------------------
    //FOR DATA
    //-------------------

    Disposable disposable;
    public List<ResultTopStories> resultTopStoriesList;
    private TopStoriesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        ButterKnife.bind(this);
        this.configureToolbar();
        configureRecyclerView();
        configureOnClickRecyclerView();
        executeHttpRequestWithRetrofit();
    }

    public void configureRecyclerView() {
        resultTopStoriesList = new ArrayList<>();
        adapter = new TopStoriesAdapter(resultTopStoriesList, this, Glide.with(this));
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                        Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                        intent.putExtra(BUNDLE_URL, articleTopStories);
                        startActivity(intent);


                    }
                });
    }

    //----------------------------
    //HTTP REQUEST WITH RETROFIT
    //----------------------------

    private void executeHttpRequestWithRetrofit() {
        this.disposable = NewYorkTimesStream.streamFetchTopStories(sectionOtherFragment).subscribeWith(new DisposableObserver<TopStories>() {
            @Override
            public void onNext(TopStories topStories) {
                updateUI(topStories.getResults());
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

    //------------------
    // UPDATE UI
    //------------------
    public void updateUI(List<ResultTopStories> articleTopStrories) {

        resultTopStoriesList.clear();
        resultTopStoriesList.addAll(articleTopStrories);
        adapter.notifyDataSetChanged();
    }



    private void configureToolbar(){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(sectionOtherFragment);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
}
