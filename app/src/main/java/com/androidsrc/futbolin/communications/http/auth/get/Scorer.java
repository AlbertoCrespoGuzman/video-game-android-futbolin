package com.androidsrc.futbolin.communications.http.auth.get;

public class Scorer {
    String minutes;
    String player;

    public Scorer(){}

    public String getMinute() {
        return minutes;
    }

    public void setMinute(String minute) {
        this.minutes = minute;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Scorer{" +
                "minute=" + minutes +
                ", player='" + player + '\'' +
                '}';
    }
}
