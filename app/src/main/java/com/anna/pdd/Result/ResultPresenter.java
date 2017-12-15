package com.anna.pdd.Result;

import com.anna.pdd.Entities.Java.UserAnswer;
import com.anna.pdd.Entities.Realm.SolvedTicket;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by anna on 11/23/17.
 */

public class ResultPresenter implements ResultContract.Presenter {

    private Realm mRealm;

    private ResultContract.View mView;

    public ResultPresenter(ResultContract.View view) {
        mView = view;
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void unsubscribe() {
        mRealm.close();
    }

    @Override
    public void subscribe() {
        mView.setPresenter(this);
    }

    @Override
    public void saveScore(ArrayList<UserAnswer> userAnswers, int ticketId) {
        Number prevId = mRealm.where(SolvedTicket.class).count();
        Number newId = null;
        if(prevId.intValue() != 0) {
            newId = mRealm.where(SolvedTicket.class).max("mId");
            newId = newId.intValue() + 1;
        }
        else{
            newId = 1;
        }
        SolvedTicket solvedTicket = new SolvedTicket();
        solvedTicket.setId(newId.longValue());
        solvedTicket.setTicketId(ticketId);
        solvedTicket.setAllCount(userAnswers.size());
        solvedTicket.setRightCount(getRightAnswersCount(userAnswers));
        mRealm.beginTransaction();
        mRealm.insert(solvedTicket);
        mRealm.commitTransaction();
    }

    private int getRightAnswersCount(ArrayList<UserAnswer> userAnswers){
        int counter = 0;
        for(UserAnswer userAnswer : userAnswers){
            if(userAnswer.isTrue()) counter++;
        }
        return counter;
    }
}
