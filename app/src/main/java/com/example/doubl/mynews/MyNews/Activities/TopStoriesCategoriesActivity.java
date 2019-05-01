package com.example.doubl.mynews.MyNews.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.doubl.mynews.MyNews.Models.ResultTopStories;
import com.example.doubl.mynews.MyNews.Models.TopStories;
import com.example.doubl.mynews.MyNews.Utils.ItemClickSupport;
import com.example.doubl.mynews.MyNews.Utils.NewYorkTimesStream;
import com.example.doubl.mynews.MyNews.Views.RecyclerViews.Divider;
import com.example.doubl.mynews.MyNews.Views.RecyclerViews.TopStoriesAdapter;
import com.example.doubl.mynews.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class TopStoriesCategoriesActivity extends AppCompatActivity {



        public static final String BUNDLE_URL= "BUNDLE_URL";
        //-------------------
        //FOR DESIGN
        //-------------------
        @BindView(R.id.recycler_view_tech)
        RecyclerView recyclerView;



        //-------------------
        //FOR DATA
        //-------------------

        Disposable disposable;
        public List<ResultTopStories> resultTopStoriesList;
        private TopStoriesAdapter adapter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_top_stories_categories);
            ButterKnife.bind(this);
            this.configureToolbar();
            configureRecyclerView();
            configureOnClickRecyclerView();
            executeHttpRequestWithRetrofit();

        }

        public void configureRecyclerView() {
            resultTopStoriesList = new ArrayList<>();
            adapter = new TopStoriesAdapter(resultTopStoriesList, this, Glide.with(this));
            recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(this.adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    /**
     * Execute http request
     * Use Retrofit to convert json format and execute asyncTask in main thread
     */
        public void executeHttpRequestWithRetrofit() {
            this.disposable = NewYorkTimesStream.streamFetchTopStories(MainActivity.categories).subscribeWith(new DisposableObserver<TopStories>() {
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
        public void updateUI(List<ResultTopStories> articleTopStories) {

            resultTopStoriesList.clear();
            resultTopStoriesList.addAll(articleTopStories);
            adapter.notifyDataSetChanged();
        }



        private void configureToolbar(){
            android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            String capitalizedCategories = MainActivity.categories;
            capitalizedCategories = capitalizedCategories.substring(0,1).toUpperCase()+capitalizedCategories.substring(1).toLowerCase();
            Objects.requireNonNull(getSupportActionBar()).setTitle(capitalizedCategories);
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);

        }


}


