package com.androidsrc.futbolin.communications.http.auth.get;

import com.androidsrc.futbolin.database.models.Player;

public class RoundScorers {
    long category_id;
    int goals;
    Player player;

    public RoundScorers(){}

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "RoundScorers{" +
                "category_id=" + category_id +
                ", goals=" + goals +
                ", player=" + player +
                '}';
    }
}
