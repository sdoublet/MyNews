package com.example.doubl.mynews.MyNews.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.doubl.mynews.MyNews.Views.ViewPager.PageAdapter;
import com.example.doubl.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String categories;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.configureToolbar();
        this.configureViewPager();
        this.configureDrawerLayout();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notifications_setting:
                Intent intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                return true;
            case R.id.about:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                break;
            case R.id.search_button:
                Intent intentSearch = new Intent(this, SearchToolbarActivity.class);
                startActivity(intentSearch);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void configureToolbar() {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureViewPager() {
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void configureDrawerLayout() {

        this.drawerLayout = findViewById(R.id.drawer_layout_activity_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();


        switch (id) {
            case R.id.Technology:
                categories = "technology";
                Intent intentTechnology = new Intent(this, TopStoriesCategoriesActivity.class);
                startActivity(intentTechnology);
                break;
            case R.id.sports:
                categories = "sports";
                Intent intentSports = new Intent(this, TopStoriesCategoriesActivity.class);
                startActivity(intentSports);
                break;
            case R.id.travel:
                categories = "travel";
                Intent intentTravel = new Intent(this, TopStoriesCategoriesActivity.class);
                startActivity(intentTravel);
                break;
            case R.id.health:
                categories = "health";
                Intent intentHealth = new Intent(this, TopStoriesCategoriesActivity.class);
                startActivity(intentHealth);
                break;
            case R.id.politics:
                categories = "politics";
                Intent intentPolitics = new Intent(this, TopStoriesCategoriesActivity.class);
                startActivity(intentPolitics);
                break;
            case R.id.science:
                categories = "science";
                Intent intentScience = new Intent(this, TopStoriesCategoriesActivity.class);
                startActivity(intentScience);
                break;
            case R.id.movies:
                categories = "movies";
                Intent intentMovies = new Intent(this, TopStoriesCategoriesActivity.class);
                startActivity(intentMovies);
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
