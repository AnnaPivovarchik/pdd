package com.anna.pdd.Results;

import com.anna.pdd.Entities.Realm.SolvedTicket;
import com.anna.pdd.Result.ResultContract;

import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by anna on 11/24/17.
 */

public class ResultsPresenter implements ResultsContract.Presenter{

    private Realm mRealm;
    private ResultsContract.View mView;

    public ResultsPresenter(ResultsContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void loadResults(boolean isBestResults) {
        List<SolvedTicket> solvedTicketList = null;
        if(isBestResults) {
            solvedTicketList = mRealm.where(SolvedTicket.class).findAll();
        }
        else{
            solvedTicketList = mRealm.where(SolvedTicket.class).findAll().sort("mId", Sort.DESCENDING);
        }
        mView.showResult(solvedTicketList);
    }

    @Override
    public void unsubscribe() {
        mRealm.close();
    }
}
