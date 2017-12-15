package com.anna.pdd.Quiz;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.anna.pdd.Entities.Java.UserAnswer;
import com.anna.pdd.Entities.Ticket;
import com.anna.pdd.PddApplication;
import com.anna.pdd.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by anna on 11/20/17.
 */

public class QuizPresenter implements QuizContract.Presenter {

    private QuizContract.View mView;
    private ArrayList<UserAnswer> mUserAnswers;

    private static int QUESTIONS_COUNT = 20;

    @Override
    public void addUserAnswer(int id, boolean isTrue) {
        mUserAnswers.add(new UserAnswer(id, isTrue));
        if (mUserAnswers.size() < QUESTIONS_COUNT) {
            mView.slideNext();
        } else {
            mView.startResult(mUserAnswers);
        }
    }

    @SuppressLint("RestrictedApi")
    public QuizPresenter(@NonNull QuizContract.View view){
        mView = checkNotNull(view, "HomeView cannot be null");
        mUserAnswers = new ArrayList<>();
        mView.setPresenter(this);
    }

    @Override

    public void subscribe() {
        mView.setPresenter(this);
    }

    @Override
    public void loadTicket(int id) {
        PddApplication.getPddApi().getTicket(id).enqueue(new Callback<Ticket>() {

            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                Ticket ticket = response.body();
                if(ticket == null){
                    mView.showError(R.string.error_loading);
                    return;
                }
                mView.showTicket(ticket);
            }
            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                mView.showError(R.string.error_loading);
            }
        });
    }
}
