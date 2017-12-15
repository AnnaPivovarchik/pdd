package com.anna.pdd.Home;


import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by anna on 11/15/17.
 */

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mHomeView;

    @SuppressLint("RestrictedApi")
    public HomePresenter(@NonNull HomeContract.View homeView){
        mHomeView = checkNotNull(homeView, "HomeView cannot be null");

        mHomeView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void openDrawer() {

    }

    @Override
    public void hideDrawer() {

    }
}
