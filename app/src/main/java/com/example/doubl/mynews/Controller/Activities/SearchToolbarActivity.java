package com.example.doubl.mynews.Controller.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doubl.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchToolbarActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.search_articles_button)Button searchArticleButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_toolbar);
        ButterKnife.bind(this);
        this.configureToolbar();
        searchArticleButton.setOnClickListener(this);

    }



    private void configureToolbar(){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public void onClick(View v) {
        onButtonClicked();
    }

    private void onButtonClicked(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
