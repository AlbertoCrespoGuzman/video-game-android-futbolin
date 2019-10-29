package com.androidsrc.futbolin.database.models;

import com.androidsrc.futbolin.communications.http.auth.get.LastMatch;
import com.androidsrc.futbolin.communications.http.auth.get.Matches;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Team implements Serializable {
    @SerializedName("id")
    long id;
    @SerializedName("name")
    String name;
    @SerializedName("short_name")
    String short_name;
    @SerializedName("primary_color")
    String primary_color;
    @SerializedName("secondary_color")
    String secondary_color;
    @SerializedName("text_color")
    String text_color;
    @SerializedName("shield")
    int shield;
    @SerializedName("stadium_name")
    String stadium_name;
    @SerializedName("strategy_id")
    long strategy_id;
    @SerializedName("formation")
    List<Integer> formation = new ArrayList<>();
    @SerializedName("formation_objects")
    List<Player> formation_objects = new ArrayList<>();
    @SerializedName("playable")
    boolean playable;
    @SerializedName("last_trainning")
    String last_trainning; //DateTime
    @SerializedName("trainer")
    String trainer;
    @SerializedName("funds")
    int funds;
    @SerializedName("trainning_count")
    int trainning_count;
    @SerializedName("user_id")
    long user_id;
    @SerializedName("posession")
    String posession;
    @SerializedName("shots")
    int shots;
    @SerializedName("shots_goal")
    int shots_goal;
    @SerializedName("yellow_cards")
    int yellow_cards;
    @SerializedName("red_cards")
    int red_cards;
    @SerializedName("substitutions")
    int substitutions;
    @SerializedName("average")
    int average;
    @SerializedName("matches")
    Matches matches;
    @SerializedName("last_matches")
    List<LastMatch> last_matches;
    @SerializedName("matches_versus")
    Matches matches_versus;
    @SerializedName("last_matches_versus")
    List<LastMatch> last_matches_versus;
    @SerializedName("spending_margin")
    long spending_margin;
    @SerializedName("trophies")
    List<Trophy> trophies;


    public Team(){}


    public List<Trophy> getTrophies() {
        return trophies;
    }

    public void setTrophies(List<Trophy> trophies) {
        this.trophies = trophies;
    }

    public List<Integer> getFormation() {
        return formation;
    }

    public long getSpending_margin() {
        return spending_margin;
    }

    public void setSpending_margin(long spending_margin) {
        this.spending_margin = spending_margin;
    }

    public void setFormation(List<Integer> formation) {
        this.formation = formation;
    }

    public List<Player> getFormation_objects() {
        return formation_objects;
    }

    public void setFormation_objects(List<Player> formation_objects) {
        this.formation_objects = formation_objects;
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

    public int getFunds() {
        return funds;
    }

    public void setFunds(int funds) {
        this.funds = funds;
    }

    public String getPosession() {
        return posession;
    }

    public void setPosession(String posession) {
        this.posession = posession;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public int getShots_goal() {
        return shots_goal;
    }

    public void setShots_goal(int shots_goal) {
        this.shots_goal = shots_goal;
    }

    public int getYellow_cards() {
        return yellow_cards;
    }

    public void setYellow_cards(int yellow_cards) {
        this.yellow_cards = yellow_cards;
    }

    public int getRed_cards() {
        return red_cards;
    }

    public void setRed_cards(int red_cards) {
        this.red_cards = red_cards;
    }

    public int getSubstitutions() {
        return substitutions;
    }

    public void setSubstitutions(int substitutions) {
        this.substitutions = substitutions;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
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

    @Override
    public String toString() {
        return "Team{" +
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
                ", formation_objects=" + formation_objects +
                ", playable=" + playable +
                ", last_trainning='" + last_trainning + '\'' +
                ", trainer='" + trainer + '\'' +
                ", funds=" + funds +
                ", trainning_count=" + trainning_count +
                ", user_id=" + user_id +
                ", posession='" + posession + '\'' +
                ", shots=" + shots +
                ", shots_goal=" + shots_goal +
                ", yellow_cards=" + yellow_cards +
                ", red_cards=" + red_cards +
                ", substitutions=" + substitutions +
                ", average=" + average +
                ", matches=" + matches +
                ", last_matches=" + last_matches +
                ", matches_versus=" + matches_versus +
                ", last_matches_versus=" + last_matches_versus +
                ", spending_margin=" + spending_margin +
                ", trophies=" + trophies +
                '}';
    }
}
