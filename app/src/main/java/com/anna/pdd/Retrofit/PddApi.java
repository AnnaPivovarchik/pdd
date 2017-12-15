package com.anna.pdd.Retrofit;

import com.anna.pdd.Entities.Ticket;
import com.anna.pdd.Entities.Tickets;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by anna on 11/17/17.
 */

public interface PddApi {
    @GET("/api/tickets")
    Call<Tickets> getTicketIds();

    @GET("/api/ticket")
    Call<Ticket> getTicket(@Query("id") int id);
}
