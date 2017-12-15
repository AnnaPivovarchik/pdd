package com.anna.pdd.Quiz;

import com.anna.pdd.BasePresenter;
import com.anna.pdd.BaseView;
import com.anna.pdd.Entities.Java.UserAnswer;
import com.anna.pdd.Entities.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anna on 11/20/17.
 */

public interface QuizContract {
    interface View extends BaseView<QuizContract.Presenter> {

        void showError(int messageId);

        void showTicket(Ticket ticket);

        void startResult(ArrayList<UserAnswer> userAnswers);

        void slideNext();

    }

    interface Presenter extends BasePresenter {

        void loadTicket(int id);

        void addUserAnswer(int id, boolean isTrue);

    }
}
