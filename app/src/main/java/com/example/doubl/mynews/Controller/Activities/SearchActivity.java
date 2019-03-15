package com.example.doubl.mynews.Controller.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doubl.mynews.Controller.Models.ResultSearchApi;
import com.example.doubl.mynews.Controller.Models.SearchApi;
import com.example.doubl.mynews.Controller.Utils.ItemClickSupport;
import com.example.doubl.mynews.Controller.Utils.NewYorkTimesStream;
import com.example.doubl.mynews.Controller.Views.RecyclerViews.SearchArticleAdapter;
import com.example.doubl.mynews.R;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchActivity extends AppCompatActivity {

    public static final String BUNDLE_URL = "BUNDLE_URL";

    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerView;


    Disposable disposable;
    List<ResultSearchApi> resultSearchApiList;

    private SearchArticleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        this.configureToolbar();
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();

    }


    public void configureRecyclerView() {
        resultSearchApiList = new ArrayList<>();
        adapter = new SearchArticleAdapter(resultSearchApiList, this, Glide.with(this));
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

    private void executeHttpRequestWithRetrofit() {

        String beginDate = null;
        String endDate = null;


        this.disposable = NewYorkTimesStream.streamFetchSearchArticle(null, null, 30, null, "TRUMP", "newest").subscribeWith(new DisposableObserver<SearchApi>() {
            @Override
            public void onNext(SearchApi searchApi) {
                updateUI(searchApi.getResponse().getDocs());
                int size = searchApi.getResponse().getDocs().size();
                if (size == 0) {
                    Toast.makeText(getApplicationContext(), "no result", Toast.LENGTH_SHORT).show();
                }
                System.out.println(size);
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
    public void updateUI(List<ResultSearchApi> articleSearchApiList) {

        articleSearchApiList.clear();
        articleSearchApiList.addAll(resultSearchApiList);
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
