package com.example.doubl.mynews.MyNews.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doubl.mynews.MyNews.Models.ResultSearchApi;
import com.example.doubl.mynews.MyNews.Models.SearchApi;
import com.example.doubl.mynews.MyNews.Utils.ApiKey;
import com.example.doubl.mynews.MyNews.Utils.ItemClickSupport;
import com.example.doubl.mynews.MyNews.Utils.NewYorkTimesStream;
import com.example.doubl.mynews.MyNews.Views.RecyclerViews.Divider;
import com.example.doubl.mynews.MyNews.Views.RecyclerViews.SearchArticleAdapter;
import com.example.doubl.mynews.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class ResultSearchActivity extends AppCompatActivity {

    public static final String BUNDLE_URL = "BUNDLE_URL";

    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerView;


    Disposable disposable;
    //List<ResultSearchApi> resultSearchApiList;
    private List<ResultSearchApi> mResult;
    private SearchArticleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        this.configureToolbar();
        this.executeHttpRequestWithRetrofit();
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();

    }


    public void configureRecyclerView() {
        mResult = new ArrayList<>();
        adapter = new SearchArticleAdapter(mResult, this, Glide.with(this));
        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    //-----------------
    // ACTION
    //-----------------

    //Configure itemClickOnRecyclerView
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.all_fragment_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // Get ResultMostPopular from adapter
                        String articleSearch;
                        articleSearch = adapter.getUrl(position);
                        Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                        intent.putExtra(BUNDLE_URL, articleSearch);
                        startActivity(intent);


                    }
                });
    }

    //----------------------------
    //HTTP REQUEST WITH RETROFIT
    //----------------------------

    public void executeHttpRequestWithRetrofit() {

        Intent intent = getIntent();
        final String beginDate = intent.getStringExtra("beginDate");
        final String endDate = intent.getStringExtra("endDate");
        final String query = intent.getStringExtra("query");
        final String filterQuery = intent.getStringExtra("filterQuery");


        this.disposable = NewYorkTimesStream.streamFetchSearchArticle(beginDate, endDate, 30, filterQuery, query, "newest", ApiKey.API_KEY).subscribeWith(new DisposableObserver<SearchApi>() {
            @Override
            public void onNext(SearchApi searchApi) {
                updateUI(searchApi.getResponse().getDocs());
                int size = searchApi.getResponse().getDocs().size();
                if (size == 0) {
                    Toast.makeText(getApplicationContext(), "no result", Toast.LENGTH_SHORT).show();


                }

                Log.e("TAG", searchApi.getStatus());
                Log.e("TAG", searchApi.getResponse().toString());
                Log.e("TAG", "SearchApi size: " + Integer.toString(size));
                Log.e("TAG", "beginDate: " + beginDate);
                Log.e("TAG", "endDate: " + endDate);
                Log.e("TAG", "queryInput: " + query);
                Log.e("TAG", "filter " + filterQuery);


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
    public void updateUI(List<ResultSearchApi> resultSearchApis) {
        mResult.clear();
        mResult.addAll(resultSearchApis);
        adapter.notifyDataSetChanged();
    }

    private void configureToolbar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Result");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


}


