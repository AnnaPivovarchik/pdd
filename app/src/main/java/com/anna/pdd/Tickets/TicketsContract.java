package com.anna.pdd.Tickets;

import com.anna.pdd.BasePresenter;
import com.anna.pdd.BaseView;
import com.anna.pdd.Entities.Tickets;
import com.anna.pdd.Home.HomeContract;

/**
 * Created by anna on 11/20/17.
 */

public interface TicketsContract {

    interface View extends BaseView<Presenter> {

        void showTickets(Tickets tickets);

        void showError();

    }

    interface Presenter extends BasePresenter {

        void loadTickets();

    }
}
