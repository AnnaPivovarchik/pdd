package com.anna.pdd.Tickets;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.anna.pdd.Entities.Question;
import com.anna.pdd.Entities.Ticket;
import com.anna.pdd.Entities.Tickets;
import com.anna.pdd.Home.MainActivity;
import com.anna.pdd.PddApplication;
import com.anna.pdd.Quiz.QuizActivity;
import com.anna.pdd.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketsFragment extends Fragment implements TicketsContract.View {

    private TicketsContract.Presenter mPresenter;
    private Spinner mSpinner;
    private Button mButtonStart;
    private Snackbar mSnackbar;
    private AdView mAdView;

    public TicketsFragment() {
    }

    public static TicketsFragment newInstance() {
        TicketsFragment fragment = new TicketsFragment();
        return fragment;
    }

    @Override
    public void setPresenter(TicketsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tickets, container, false);
        mSpinner = v.findViewById(R.id.selectTicketSpinner);
        mButtonStart = v.findViewById(R.id.selectTicketButton);
        mAdView = (AdView) v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("5B983A5BB8534E8D855175106946CF62")
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                mAdView.setVisibility(View.GONE);
            }
        });
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mSpinner.getSelectedItemPosition();
                Ticket ticket = (Ticket) mSpinner.getAdapter().getItem(pos);
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra(QuizActivity.id, ticket.getId());
                Bundle bundle = new Bundle();
                bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, ticket.getId());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Ticket selected");
                ((MainActivity)getActivity()).getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        mPresenter.loadTickets();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showError() {
        mSnackbar = Snackbar.make(getView().findViewById(R.id.coordinatorLayout), R.string.error_loading, Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction(R.string.retry, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadTickets();
            }
        });
        mSnackbar.show();
    }

    @Override
    public void showTickets(Tickets tickets) {
        List<Ticket> ticketList = tickets.getTickets();
        ArrayAdapter<Ticket> adapter = new ArrayAdapter<Ticket>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ticketList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mButtonStart.setEnabled(true);
        mSpinner.setAdapter(adapter);
        if(mSnackbar != null){
            mSnackbar.dismiss();
        }
    }

}
