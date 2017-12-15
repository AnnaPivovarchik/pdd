package com.anna.pdd.Result;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.anna.pdd.Entities.Answer;
import com.anna.pdd.Entities.Java.UserAnswer;
import com.anna.pdd.Question.QuestionFragment;
import com.anna.pdd.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ResultActivity extends AppCompatActivity implements ResultContract.View{

    public static final String QUIZ = "com.anna.pdd.Quiz";
    public static final String TICKET_ID = "com.anna.pdd.ticket.id";

    private ResultContract.Presenter mPresenter;

    private ArrayList<UserAnswer> mUserAnswers;
    private int mTicketId;

    private TextView mResultTextView;
    private RecyclerView mRecyclerView;
    private UserAnswersAdapter mAdapter;
    private Toolbar mToolbar;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mPresenter = new ResultPresenter(this);
        mUserAnswers = getIntent().getParcelableArrayListExtra(QUIZ);
        Collections.sort(mUserAnswers, new Comparator<UserAnswer>() {
            @Override
            public int compare(UserAnswer o1, UserAnswer o2) {
                return o1.getId() - o2.getId();
            }
        });
        mTicketId = getIntent().getIntExtra(TICKET_ID, 1);
        mPresenter.saveScore(mUserAnswers, mTicketId);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mResultTextView = findViewById(R.id.resultTextView);
        mResultTextView.setText(String.format(getString(R.string.result_text_view),
                getRightAnswersCount(),
                mUserAnswers.size()));
        mRecyclerView = findViewById(R.id.resultRecyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager
                (this));
        mAdapter = new UserAnswersAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void setPresenter(ResultContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void showResult() {

    }

    private int getRightAnswersCount(){
        int counter = 0;
        for(UserAnswer userAnswer : mUserAnswers){
            if(userAnswer.isTrue()) counter++;
        }
        return counter;
    }

    private class UserAnswersAdapter extends RecyclerView.Adapter<ResultActivity.UserAnswersHolder>{

        @Override
        public ResultActivity.UserAnswersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.quiz_result_item, parent, false);
            return new ResultActivity.UserAnswersHolder(view);
        }

        @Override
        public int getItemCount() {
            return mUserAnswers.size();
        }

        @Override
        public void onBindViewHolder(ResultActivity.UserAnswersHolder holder, int position) {
            UserAnswer userAnswer = mUserAnswers.get(position);
            Resources res = getResources();
            holder.bind(userAnswer, position + 1, res.getDrawable(R.drawable.ic_done_black_24dp), res.getDrawable(R.drawable.ic_close_black_24dp));
        }
    }

    private class UserAnswersHolder extends RecyclerView.ViewHolder{
        private TextView mQuestionIdTextView;
        private ImageView mQuestionStatusImageView;
        private UserAnswer mUserAnswer;

        public UserAnswersHolder(View itemView){
            super(itemView);
            mQuestionIdTextView = itemView.findViewById(R.id.questionIdTextView);
            mQuestionStatusImageView = itemView.findViewById(R.id.questionStatusImageView);
        }

        public void bind(UserAnswer userAnswer, int position,  Drawable right, Drawable wrong) {
            mUserAnswer = userAnswer;
            mQuestionIdTextView.setText(String.valueOf(position));
            if(mUserAnswer.isTrue()){
                    mQuestionStatusImageView.setImageDrawable(right);
            }
            else{
                    mQuestionStatusImageView.setImageDrawable(wrong);
            }
        }
    }
}
