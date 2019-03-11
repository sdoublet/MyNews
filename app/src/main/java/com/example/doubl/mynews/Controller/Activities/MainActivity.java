package com.example.doubl.mynews.Controller.Activities;

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;


import com.example.doubl.mynews.Controller.Fragments.OtherFragment;

import com.example.doubl.mynews.Controller.Views.ViewPager.PageAdapter;
import com.example.doubl.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;


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
                Intent intentSearch = new Intent(this, SearchActivity.class);
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
        ViewPager viewPager = findViewById(R.id.view_pager);
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
        OtherFragment otherFragment = new OtherFragment();
        switch (id) {
            case R.id.Technology:
                otherFragment.sectionOtherFragment = "technology";
                Toast.makeText(this, otherFragment.sectionOtherFragment, Toast.LENGTH_SHORT).show();
Intent intentTechnology = new Intent(this, TechnologyActivity.class);
startActivity(intentTechnology);
                break;
            case R.id.sports:
                otherFragment.sectionOtherFragment = "sports";
                Toast.makeText(this, otherFragment.sectionOtherFragment, Toast.LENGTH_SHORT).show();
                Intent intentSports = new Intent(this, SportsActivity.class);
                startActivity(intentSports);
                break;
            case R.id.travel:
                otherFragment.sectionOtherFragment = "travel";
                Toast.makeText(this, otherFragment.sectionOtherFragment, Toast.LENGTH_SHORT).show();
                Intent intentTravel = new Intent(this, TravelActivity.class);
                startActivity(intentTravel);
                break;
            case R.id.arts:
                otherFragment.sectionOtherFragment = "arts";
                Toast.makeText(this, otherFragment.sectionOtherFragment, Toast.LENGTH_SHORT).show();
                break;
            case R.id.politics:
                otherFragment.sectionOtherFragment = "politics";
                Toast.makeText(this, otherFragment.sectionOtherFragment, Toast.LENGTH_SHORT).show();
                break;
            case R.id.science:
                otherFragment.sectionOtherFragment = "science";
                Toast.makeText(this, otherFragment.sectionOtherFragment, Toast.LENGTH_SHORT).show();
                break;
            case R.id.movies:
                otherFragment.sectionOtherFragment = "movies";
                Toast.makeText(this, otherFragment.sectionOtherFragment, Toast.LENGTH_SHORT).show();
                break;


        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
//    private void showOtherFrag(){

//        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.drawer_layout_activity_main);
//        if (visibleFragment==null){
//            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout_activity_main, )
//        }
//        navigationView.getMenu().getItem(2).setChecked(true);
//    }


}
