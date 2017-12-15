package com.anna.pdd.Entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.anna.pdd.R;

public class Ticket {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return "Билет " + id;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}