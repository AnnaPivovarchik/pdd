package com.anna.pdd.Entities;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tickets {

    @SerializedName("tickets")
    @Expose
    private List<Ticket> tickets = null;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}