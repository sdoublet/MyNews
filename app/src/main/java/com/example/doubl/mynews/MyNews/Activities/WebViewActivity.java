package com.example.doubl.mynews.MyNews.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.doubl.mynews.MyNews.Fragments.MostPopularFragment;
import com.example.doubl.mynews.MyNews.Fragments.TopStoriesFragment;
import com.example.doubl.mynews.MyNews.Models.ResultMostPopular;
import com.example.doubl.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.web_view)
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        this.configureToolbar();
        this.configureWebViewMostPopular();
        this.configureWebViewTopStories();


    }

    public void configureToolbar() {
        ResultMostPopular resultMostPopular = new ResultMostPopular();
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void configureWebViewMostPopular() {
        String url = getIntent().getStringExtra(MostPopularFragment.BUNDLE_URL);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }

    public void configureWebViewTopStories() {
        String url = getIntent().getStringExtra(TopStoriesFragment.BUNDLE_URL);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }


}
