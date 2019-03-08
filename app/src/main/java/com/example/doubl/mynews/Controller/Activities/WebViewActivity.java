package com.example.doubl.mynews.Controller.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.doubl.mynews.Controller.Fragments.MostPopularFragment;
import com.example.doubl.mynews.Controller.Fragments.TopStoriesFragment;
import com.example.doubl.mynews.Controller.Models.ResultMostPopular;
import com.example.doubl.mynews.Controller.Models.TopStories;
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

    private void configureToolbar() {
        ResultMostPopular resultMostPopular = new ResultMostPopular();
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void configureWebViewMostPopular(){
        String url = getIntent().getStringExtra(MostPopularFragment.BUNDLE_URL);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }
    private void configureWebViewTopStories(){
        String url = getIntent().getStringExtra(TopStoriesFragment.BUNDLE_URL);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }
}
