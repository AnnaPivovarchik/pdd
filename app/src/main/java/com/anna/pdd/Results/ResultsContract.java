package com.anna.pdd.Results;

import com.anna.pdd.BasePresenter;
import com.anna.pdd.BaseView;
import com.anna.pdd.Entities.Java.UserAnswer;
import com.anna.pdd.Entities.Realm.SolvedTicket;
import com.anna.pdd.Result.ResultContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anna on 11/24/17.
 */

public interface ResultsContract {
    interface View extends BaseView<ResultsContract.Presenter> {

        void showResult(List<SolvedTicket> solvedTickets);

    }

    interface Presenter extends BasePresenter {

        void loadResults(boolean isBestResults);

        void unsubscribe();

    }
}
