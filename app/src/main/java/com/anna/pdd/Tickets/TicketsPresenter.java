package com.anna.pdd.Tickets;

import com.anna.pdd.BasePresenter;
import com.anna.pdd.Entities.Tickets;
import com.anna.pdd.Home.HomeContract;
import com.anna.pdd.PddApplication;
import com.anna.pdd.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TicketsPresenter implements TicketsContract.Presenter {

    private final TicketsContract.View mView;

    public TicketsPresenter(TicketsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void loadTickets() {
        PddApplication.getPddApi().getTicketIds().enqueue(new Callback<Tickets>() {

            @Override
            public void onResponse(Call<Tickets> call, Response<Tickets> response) {
                Tickets tickets = response.body();
                if(tickets == null){
                    mView.showError();
                    return;
                }
                mView.showTickets(tickets);
            }
            @Override
            public void onFailure(Call<Tickets> call, Throwable t) {
                mView.showError();
            }
        });
    }
}