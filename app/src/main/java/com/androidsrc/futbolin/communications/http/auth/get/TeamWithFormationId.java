package com.androidsrc.futbolin.communications.http.auth.get;


import com.androidsrc.futbolin.communications.http.auth.get.LastMatch;
import com.androidsrc.futbolin.communications.http.auth.get.Matches;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.database.models.Position;
import com.androidsrc.futbolin.database.models.Strategy;
import com.androidsrc.futbolin.database.models.Trophy;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TeamWithFormationId implements Serializable {

    long id;

    String name;

    String short_name;

    String primary_color;

    String secondary_color;

    String text_color;

    int shield;

    String stadium_name;

    long strategy_id;

    List<Integer> formation = new ArrayList<>();

    boolean playable;

    String next_friendly;

    String last_trainning; //DateTime

    String trainer;

    String user_name;

    int funds;

    long spending_margin;

    int trainning_count;

    long user_id;

    int average;
    @SerializedName("matches")
    Matches matches;
    @SerializedName("last_matches")
    List<LastMatch> last_matches;
    @SerializedName("matches_versus")
    Matches matches_versus;
    @SerializedName("last_matches_versus")
    List<LastMatch> last_matches_versus;
    @SerializedName("strategy")
    List<Position> strategy;
    @SerializedName("trophies")
    List<Trophy> trophies = new ArrayList<>();

    public String getNext_friendly() {
        return next_friendly;
    }

    public void setNext_friendly(String next_friendly) {
        this.next_friendly = next_friendly;
    }

    public List<Trophy> getTrophies() {
        return trophies;
    }

    public void setTrophies(List<Trophy> trophies) {
        this.trophies = trophies;
    }

    public long getSpending_margin() {
        return spending_margin;
    }

    public void setSpending_margin(long spending_margin) {
        this.spending_margin = spending_margin;
    }

    public TeamWithFormationId(){}

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

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

    public long getStrategy_id() {
        return strategy_id;
    }

    public void setStrategy_id(long strategy_id) {
        this.strategy_id = strategy_id;
    }

    public List<Integer> getFormation() {
        return formation;
    }

    public void setFormation(List<Integer> formation) {
        this.formation = formation;
    }

    public boolean isPlayable() {
        return playable;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    public String getLast_trainning() {
        return last_trainning;
    }

    public void setLast_trainning(String last_trainning) {
        this.last_trainning = last_trainning;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public int getFunds() {
        return funds;
    }

    public void setFunds(int funds) {
        this.funds = funds;
    }

    public int getTrainning_count() {
        return trainning_count;
    }

    public void setTrainning_count(int trainning_count) {
        this.trainning_count = trainning_count;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public List<Position> getStrategy() {
        return strategy;
    }

    public void setStrategy(List<Position> strategy) {
        this.strategy = strategy;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public Matches getMatches() {
        return matches;
    }

    public void setMatches(Matches matches) {
        this.matches = matches;
    }

    public List<LastMatch> getLast_matches() {
        return last_matches;
    }

    public void setLast_matches(List<LastMatch> last_matches) {
        this.last_matches = last_matches;
    }

    public Matches getMatches_versus() {
        return matches_versus;
    }

    public void setMatches_versus(Matches matches_versus) {
        this.matches_versus = matches_versus;
    }

    public List<LastMatch> getLast_matches_versus() {
        return last_matches_versus;
    }

    public void setLast_matches_versus(List<LastMatch> last_matches_versus) {
        this.last_matches_versus = last_matches_versus;
    }

    @Override
    public String toString() {
        return "TeamWithFormationId{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", primary_color='" + primary_color + '\'' +
                ", secondary_color='" + secondary_color + '\'' +
                ", text_color='" + text_color + '\'' +
                ", shield=" + shield +
                ", stadium_name='" + stadium_name + '\'' +
                ", strategy_id=" + strategy_id +
                ", formation=" + formation +
                ", playable=" + playable +
                ", next_friendly='" + next_friendly + '\'' +
                ", last_trainning='" + last_trainning + '\'' +
                ", trainer='" + trainer + '\'' +
                ", user_name='" + user_name + '\'' +
                ", funds=" + funds +
                ", spending_margin=" + spending_margin +
                ", trainning_count=" + trainning_count +
                ", user_id=" + user_id +
                ", average=" + average +
                ", matches=" + matches +
                ", last_matches=" + last_matches +
                ", matches_versus=" + matches_versus +
                ", last_matches_versus=" + last_matches_versus +
                ", strategy=" + strategy +
                ", trophies=" + trophies +
                '}';
    }
}
