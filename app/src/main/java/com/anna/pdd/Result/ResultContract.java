package com.anna.pdd.Result;

import com.anna.pdd.BasePresenter;
import com.anna.pdd.BaseView;
import com.anna.pdd.Entities.Java.UserAnswer;
import com.anna.pdd.Entities.Ticket;
import com.anna.pdd.Quiz.QuizContract;

import java.util.ArrayList;

/**
 * Created by anna on 11/23/17.
 */

public interface ResultContract {
    interface View extends BaseView<ResultContract.Presenter> {

        void showResult();

    }

    interface Presenter extends BasePresenter {
        void saveScore(ArrayList<UserAnswer> userAnswers, int ticketId);

        void unsubscribe();
    }
}
