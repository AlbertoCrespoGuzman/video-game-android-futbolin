package com.androidsrc.futbolin.communications.http.auth.get;

import java.io.Serializable;

public class MatchStats  implements Serializable {
    int tied;
    int won;
    int lost;
    int total;
    int goals_favor;
    int goals_against;
    int goals_difference;

    public MatchStats(){}

    public int getTied() {
        return tied;
    }

    public void setTied(int tied) {
        this.tied = tied;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getGoals_favor() {
        return goals_favor;
    }

    public void setGoals_favor(int goals_favor) {
        this.goals_favor = goals_favor;
    }

    public int getGoals_against() {
        return goals_against;
    }

    public void setGoals_against(int goals_against) {
        this.goals_against = goals_against;
    }

    public int getGoals_difference() {
        return goals_difference;
    }

    public void setGoals_difference(int goals_difference) {
        this.goals_difference = goals_difference;
    }

    @Override
    public String toString() {
        return "MatchStats{" +
                "tied=" + tied +
                ", won=" + won +
                ", lost=" + lost +
                ", total=" + total +
                ", goals_favor=" + goals_favor +
                ", goals_against=" + goals_against +
                ", goals_difference=" + goals_difference +
                '}';
    }
}
