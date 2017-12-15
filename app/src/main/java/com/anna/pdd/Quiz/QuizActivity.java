package com.anna.pdd.Quiz;

import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.anna.pdd.Entities.Java.UserAnswer;
import com.anna.pdd.Entities.Question;
import com.anna.pdd.Entities.Ticket;
import com.anna.pdd.Question.QuestionFragment;
import com.anna.pdd.R;
import com.anna.pdd.Result.ResultActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity implements QuizContract.View, QuestionFragment.OnFragmentInteractionListener {

    public static final String id = "com.anna.pdd.Quiz.id";

    private QuizContract.Presenter mPresenter;

    private int mTicketId;
    private Ticket mTicket;

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private Snackbar mSnackbar;

    int mCurrentPage = 0;
    Timer mTimer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Intent intent = getIntent();
        mTicketId = intent.getIntExtra(id, 1);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(String.format(getString(R.string.ticket), mTicketId));
        mPresenter = new QuizPresenter(this);
        mViewPager = findViewById(R.id.viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
        if(mTicket == null)
             mPresenter.loadTicket(mTicketId);
    }

    @Override
    public void setPresenter(QuizContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(int messageId) {
        mSnackbar = Snackbar.make(this.findViewById(R.id.coordinatorLayout), R.string.error_loading, Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction(R.string.retry, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadTicket(mTicketId);
            }
        });
        mSnackbar.show();
    }

    @Override
    public void showTicket(Ticket ticket) {
        mTicket = ticket;
        mPagerAdapter = new QuestionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    public Question getQuestion(int id){
        Question question = null;
        for(Question question1 : mTicket.getQuestions()){
            if(question1.getId() == id){
                question = question1;
                break;
            }
        }
        return question;
    }

    @Override
    public void startResult(ArrayList<UserAnswer> list) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putParcelableArrayListExtra(ResultActivity.QUIZ, (ArrayList<? extends Parcelable>) list);
        intent.putExtra(ResultActivity.TICKET_ID, mTicketId);
        startActivity(intent);
    }

    @Override
    public void slideNext(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        }, 300);
    }


    @Override
    public void onAnswerSelected(int id, boolean isTrue) {
        mPresenter.addUserAnswer(id, isTrue);
    }

    private class QuestionPagerAdapter extends FragmentStatePagerAdapter {
        public QuestionPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return QuestionFragment.newInstance(mTicket.getQuestions().get(position).getId());
        }

        @Override
        public int getCount() {
            return mTicket.getQuestions().size();
        }
    }
}
