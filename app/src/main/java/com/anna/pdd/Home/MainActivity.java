package com.anna.pdd.Home;

import android.app.Application;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.anna.pdd.Entities.Ticket;
import com.anna.pdd.Entities.Tickets;
import com.anna.pdd.PddApplication;
import com.anna.pdd.R;
import com.anna.pdd.Results.ResultsContract;
import com.anna.pdd.Results.ResultsFragment;
import com.anna.pdd.Results.ResultsPresenter;
import com.anna.pdd.Tickets.TicketsFragment;
import com.anna.pdd.Tickets.TicketsPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TicketsPresenter mTicketsPresenter;
    private ResultsPresenter mResultsPresenter;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showFragment();
        //
    }
    

    public FirebaseAnalytics getFirebaseAnalytics() {
        return mFirebaseAnalytics;
    }

    private void showFragment() {
        TicketsFragment fragment = (TicketsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_tickets);
        fragment = fragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mTicketsPresenter = new TicketsPresenter(fragment);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tickets) {
            showFragment();
        } else if (id == R.id.nav_results) {
            ResultsFragment fragment = (ResultsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_results);
            fragment = fragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            mResultsPresenter = new ResultsPresenter(fragment);
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
