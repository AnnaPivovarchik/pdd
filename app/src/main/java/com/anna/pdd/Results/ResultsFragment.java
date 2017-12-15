package com.anna.pdd.Results;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.anna.pdd.Entities.Realm.SolvedTicket;
import com.anna.pdd.Entities.Ticket;
import com.anna.pdd.Home.MainActivity;
import com.anna.pdd.Quiz.QuizActivity;
import com.anna.pdd.R;
import com.anna.pdd.Result.ResultActivity;
import com.anna.pdd.Result.ResultContract;
import com.anna.pdd.Result.ResultPresenter;

import org.w3c.dom.Text;

import java.util.List;

public class ResultsFragment extends Fragment  implements ResultsContract.View{

    private ResultsContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private ResultsAdapter mResultsAdapter;

    private List<SolvedTicket> mSolvedTickets;

    public ResultsFragment() {
    }

    public static ResultsFragment newInstance() {
        ResultsFragment fragment = new ResultsFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        mPresenter.loadResults(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, null);
        mRecyclerView = view.findViewById(R.id.resultsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));
        return view;
    }

    @Override
    public void setPresenter(ResultsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showResult(List<SolvedTicket> solvedTicketList) {
        mSolvedTickets = solvedTicketList;
        mResultsAdapter = new ResultsAdapter();
        mRecyclerView.setAdapter(mResultsAdapter);
    }

    private class ResultsAdapter extends RecyclerView.Adapter<ResultHolder>{
        @Override
        public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.card_result, parent, false);
            return new ResultsFragment.ResultHolder(view);
        }

        @Override
        public void onBindViewHolder(ResultHolder holder, int position) {
            SolvedTicket solvedTicket = mSolvedTickets.get(position);
            holder.bind(solvedTicket);
        }

        @Override
        public int getItemCount() {
            return mSolvedTickets.size();
        }
    }

    private class ResultHolder extends RecyclerView.ViewHolder {
        private SolvedTicket mSolvedTicket;

        private TextView mTicketTextView;
        private TextView mRightCountTextView;
        private TextView mWrongCountTextView;
        private TextView mPercentageTextView;
        private Button mButtonRestart;

        public ResultHolder(View itemView){
            super(itemView);
            mTicketTextView = itemView.findViewById(R.id.ticketName);
            mRightCountTextView = itemView.findViewById(R.id.rightAnswersCount);
            mWrongCountTextView = itemView.findViewById(R.id.wrongAnswersCount);
            mPercentageTextView = itemView.findViewById(R.id.percentage);
            mButtonRestart = itemView.findViewById(R.id.restartTicketButton);
        }

        public void bind(SolvedTicket solvedTicket){
            mSolvedTicket = solvedTicket;
            mTicketTextView.setText(getString(R.string.ticket_id_text_view, mSolvedTicket.getTicketId()));
            int rightCount = mSolvedTicket.getRightCount();
            mRightCountTextView.setText(String.valueOf(rightCount));
            int wrongCount = mSolvedTicket.getAllCount() - rightCount;
            mWrongCountTextView.setText(String.valueOf(wrongCount));
            long percentage = Math.round(100 * ((double) rightCount/((double) rightCount + (double) wrongCount)));
            mPercentageTextView.setText(String.valueOf(percentage));
            mButtonRestart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), QuizActivity.class);
                    intent.putExtra(QuizActivity.id, mSolvedTicket.getTicketId());
                    Bundle bundle = new Bundle();
                    bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, (int) mSolvedTicket.getId());
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Ticket selected");
                    ((MainActivity)getActivity()).getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    startActivity(intent);
                }
            });
        }

    }
}
