package com.androidsrc.futbolin.communications.http.auth.get;

import com.androidsrc.futbolin.database.models.Player;

import java.util.List;

public class FriendlyTeam {
    long id;
    String name;
    String short_name;
    String primary_color;
    String secondary_color;
    String text_color;
    int shield;
    String stadium_name;
    int strategy_id = 2;
    boolean playable;
    long user_id;
    String played;
    List<Integer> formation;
    long funds;
    String user_name;
    int average;
    long spending_margin;


    public long getSpending_margin() {
        return spending_margin;
    }

    public void setSpending_margin(long spending_margin) {
        this.spending_margin = spending_margin;
    }

    public FriendlyTeam(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getPrimary_color() {
        return primary_color;
    }

    public void setPrimary_color(String primary_color) {
        this.primary_color = primary_color;
    }

    public String getSecondary_color() {
        return secondary_color;
    }

    public void setSecondary_color(String secondary_color) {
        this.secondary_color = secondary_color;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public String getStadium_name() {
        return stadium_name;
    }

    public void setStadium_name(String stadium_name) {
        this.stadium_name = stadium_name;
    }

    public int getStrategy_id() {
        return strategy_id;
    }

    public void setStrategy_id(int strategy_id) {
        this.strategy_id = strategy_id;
    }

    public boolean isPlayable() {
        return playable;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getPlayed() {
        return played;
    }

    public void setPlayed(String played) {
        this.played = played;
    }

    public List<Integer> getFormation() {
        return formation;
    }

    public void setFormation(List<Integer> formation) {
        this.formation = formation;
    }

    public long getFunds() {
        return funds;
    }

    public void setFunds(long funds) {
        this.funds = funds;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    @Override
    public String toString() {
        return "FriendlyTeam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", primary_color='" + primary_color + '\'' +
                ", secondary_color='" + secondary_color + '\'' +
                ", text_color='" + text_color + '\'' +
                ", shield=" + shield +
                ", stadium_name='" + stadium_name + '\'' +
                ", strategy_id=" + strategy_id +
                ", playable=" + playable +
                ", user_id=" + user_id +
                ", played='" + played + '\'' +
                ", formation=" + formation +
                ", funds=" + funds +
                ", user_name='" + user_name + '\'' +
                ", average=" + average +
                '}';
    }
}
