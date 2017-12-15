package com.anna.pdd.Answer;

import com.anna.pdd.BasePresenter;
import com.anna.pdd.BaseView;
import com.anna.pdd.Entities.Tickets;
import com.anna.pdd.Tickets.TicketsContract;

/**
 * Created by anna on 11/20/17.
 */

public interface AnswerContract {

    interface View extends BaseView<TicketsContract.Presenter> {

        void showTickets(Tickets tickets);

        void showError(int messageId);

    }

    interface Presenter extends BasePresenter {

        void loadTickets();

    }
}
