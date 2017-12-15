package com.anna.pdd.Question;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.anna.pdd.Entities.Answer;
import com.anna.pdd.Entities.Question;
import com.anna.pdd.Quiz.QuizActivity;
import com.anna.pdd.R;



public class QuestionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private AnswersAdapter mAdapter;

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String SELECTED_ANSWER_NUMBER ="selected_answer_number";
    static final String IS_ANSWERED_STATUS ="is_answered_status";

    private int mQuestionId;
    private Question mQuestion;

    private Button mButtonAnswer;
    private Button mButtonHelp;
    private TextView mQuestionText;
    private ImageView mQuestionImage;

    public QuestionFragment() {
    }

    public static QuestionFragment newInstance(int id) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_PAGE_NUMBER, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestionId = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_ANSWERED_STATUS, !mButtonAnswer.isClickable());
        outState.putInt(SELECTED_ANSWER_NUMBER, mAdapter.getCheckedPosition());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        mQuestion = ((QuizActivity) getActivity()).getQuestion(mQuestionId);
        mQuestionText = v.findViewById(R.id.questionTextView);
        mQuestionText.setText(mQuestion.getText());
        mQuestionImage = v.findViewById(R.id.questionImageView);
        if(mQuestion.getImageName() != null){
            Picasso.with(getContext()).load(mQuestion.getImageName().toString()).into(mQuestionImage);
        }
        else{
            mQuestionImage.setImageDrawable(getResources().getDrawable(R.drawable.placeholder));
        }
        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));
        mAdapter = new AnswersAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mButtonAnswer = v.findViewById(R.id.buttonAnswer);
        mButtonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonAnswer.setClickable(false);
                mButtonAnswer.setVisibility(View.INVISIBLE);
                mRecyclerView.setEnabled(false);
                int position = mAdapter.getCheckedPosition();
                mAdapter.setAnswered();
                mButtonHelp.setVisibility(View.VISIBLE);
                mButtonHelp.setClickable(true);
                mListener.onAnswerSelected(mQuestion.getId(), mQuestion.getAnswers().get(mAdapter.getCheckedPosition()).isIsTrue());
            }
        });
        mButtonHelp = v.findViewById(R.id.buttonHelp);
        mButtonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.explanation)
                        .setMessage(mQuestion.getComment())
                        .setCancelable(false)
                        .setNegativeButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        if(savedInstanceState != null){
            mAdapter.setCheckedPosition(savedInstanceState.getInt(SELECTED_ANSWER_NUMBER, 0));
            if(savedInstanceState.getBoolean(IS_ANSWERED_STATUS, true)) {
                mButtonHelp.setVisibility(View.VISIBLE);
                mButtonHelp.setClickable(true);
                mButtonAnswer.setClickable(false);
                mButtonAnswer.setVisibility(View.INVISIBLE);
                mAdapter.setAnswered();
            }
        }
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class AnswersAdapter extends RecyclerView.Adapter<AnswersHolder>{
        private int mCheckedPosition = 0;
        private boolean mAnswered = false;

        public void setCheckedPosition(int checkedPosition) {
            mCheckedPosition = checkedPosition;
        }

        public int getCheckedPosition() {
            return mCheckedPosition;
        }

        public void setAnswered(){
            mAnswered = true;
            notifyDataSetChanged();
        }

        @Override
        public AnswersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.answer_item, parent, false);
            return new AnswersHolder(view);
        }

        @Override
        public int getItemCount() {
            return mQuestion.getAnswers().size();
        }

        @Override
        public void onBindViewHolder(AnswersHolder holder, int position) {
            Answer answer = mQuestion.getAnswers().get(position);
            holder.bind(answer, position == mCheckedPosition, mAnswered);
        }
    }

    private class AnswersHolder extends RecyclerView.ViewHolder{
        private RadioButton mRadioButton;
        private Answer mAnswer;

        public AnswersHolder(View itemView){
            super(itemView);

            mRadioButton = itemView.findViewById(R.id.radioButton);
        }

        public void bind(Answer answer, boolean isChecked, boolean isAnswered) {
            mAnswer = answer;
            mRadioButton.setText(mAnswer.getText());
            mRadioButton.setChecked(isChecked);
            if(isAnswered ) {
                mRadioButton.setClickable(false);
                if(isChecked) {
                    if (answer.isIsTrue())
                        mRadioButton.setButtonDrawable(R.drawable.ic_done_black_24dp);
                    else mRadioButton.setButtonDrawable(R.drawable.ic_close_black_24dp);
                }
                else{
                    if(answer.isIsTrue()) mRadioButton.setButtonDrawable(R.drawable.ic_done_black_24dp);
                    else mRadioButton.setVisibility(View.INVISIBLE);
                }
            }
            else {
                mRadioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.setCheckedPosition(getAdapterPosition());
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onAnswerSelected(int id, boolean isTrue);
    }
}
