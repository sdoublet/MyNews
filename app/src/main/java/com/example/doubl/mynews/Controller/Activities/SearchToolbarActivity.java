package com.example.doubl.mynews.Controller.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.doubl.mynews.R;

public class SearchToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_toolbar);
        this.configureToolbar();
    }



    private void configureToolbar(){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
}
