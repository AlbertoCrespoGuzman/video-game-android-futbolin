package com.androidsrc.futbolin.communications.http.auth.get;

import java.util.List;

public class Round {
    long category_id;
    int number;
    long datetime;
    List<RoundMatch> matches;

    public Round(){

    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public List<RoundMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<RoundMatch> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "Round{" +
                "category_id=" + category_id +
                ", number=" + number +
                ", datetime=" + datetime +
                ", matches=" + matches +
                '}';
    }
}
