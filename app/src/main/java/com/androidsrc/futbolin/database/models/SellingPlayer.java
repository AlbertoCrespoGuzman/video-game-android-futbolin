package com.androidsrc.futbolin.database.models;

import java.io.Serializable;

public class SellingPlayer implements Serializable{
    long player_id;
    long value;
    long best_offer_value;
    long best_offer_team;
    String closes_at;

    public SellingPlayer(){

    }

    public long getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(long player_id) {
        this.player_id = player_id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getBest_offer_value() {
        return best_offer_value;
    }

    public void setBest_offer_value(long best_offer_value) {
        this.best_offer_value = best_offer_value;
    }

    public long getBest_offer_team() {
        return best_offer_team;
    }

    public void setBest_offer_team(long best_offer_team) {
        this.best_offer_team = best_offer_team;
    }

    public String getCloses_at() {
        return closes_at;
    }

    public void setCloses_at(String closes_at) {
        this.closes_at = closes_at;
    }

    @Override
    public String toString() {
        return "SellingPlayer{" +
                "player_id=" + player_id +
                ", value=" + value +
                ", best_offer_value=" + best_offer_value +
                ", best_offer_team=" + best_offer_team +
                ", closes_at='" + closes_at + '\'' +
                '}';
    }
}
