package com.anna.pdd.Home;

import com.anna.pdd.BasePresenter;
import com.anna.pdd.BaseView;

/**
 * Created by anna on 11/15/17.
 */

public interface HomeContract {

    interface View extends BaseView<Presenter>{

        void showDrawer();

        void closeDrawer();

    }

    interface Presenter extends BasePresenter{

        void openDrawer();

        void hideDrawer();

    }
}
