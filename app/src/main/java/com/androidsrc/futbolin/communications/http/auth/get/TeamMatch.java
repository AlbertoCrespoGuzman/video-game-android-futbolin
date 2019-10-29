package com.androidsrc.futbolin.communications.http.auth.get;

import com.androidsrc.futbolin.database.models.Position;
import com.androidsrc.futbolin.database.models.Team;

import java.util.List;

public class TeamMatch {
    String name;
    String primary_color;
    String secondary_color;
    List<Integer> rgb_primary;
    String text_color;
    String shield_file;
    int shield;
    int goals;
    String posession;
    int shots;
    int shots_goal;
    int yellow_cards;
    int red_cards;
    int substitutions;
    List<Position> formation;
    long team_id;

    public TeamMatch(){}

    public int getShots_goal() {
        return shots_goal;
    }

    public void setShots_goal(int shots_goal) {
        this.shots_goal = shots_goal;
    }

    public long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(long team_id) {
        this.team_id = team_id;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Integer> getRgb_primary() {
        return rgb_primary;
    }

    public void setRgb_primary(List<Integer> rgb_primary) {
        this.rgb_primary = rgb_primary;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }

    public String getShield_file() {
        return shield_file;
    }

    public void setShield_file(String shield_file) {
        this.shield_file = shield_file;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
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

    public List<Position> getFormation() {
        return formation;
    }

    public void setFormation(List<Position> formation) {
        this.formation = formation;
    }

    @Override
    public String toString() {
        return "TeamMatch{" +
                "name='" + name + '\'' +
                ", primary_color='" + primary_color + '\'' +
                ", secondary_color='" + secondary_color + '\'' +
                ", rgb_primary=" + rgb_primary +
                ", text_color='" + text_color + '\'' +
                ", shield_file='" + shield_file + '\'' +
                ", shield=" + shield +
                ", goals=" + goals +
                ", posession='" + posession + '\'' +
                ", shots=" + shots +
                ", shots_goals=" + shots_goal +
                ", yellow_cards=" + yellow_cards +
                ", red_cards=" + red_cards +
                ", substitutions=" + substitutions +
                ", formation=" + formation +
                ", team_id=" + team_id +
                '}';
    }
}
