package com.androidsrc.futbolin.database.models;

import java.io.Serializable;

public class Trophy implements Serializable {

    long tournament_id;
    String tournament_name;
    int position;


    public Trophy(){}

    public long getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(long tournament_id) {
        this.tournament_id = tournament_id;
    }

    public String getTournament_name() {
        return tournament_name;
    }

    public void setTournament_name(String tournament_name) {
        this.tournament_name = tournament_name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Trophy{" +
                "tournament_id=" + tournament_id +
                ", tournament_name='" + tournament_name + '\'' +
                ", position=" + position +
                '}';
    }
}
