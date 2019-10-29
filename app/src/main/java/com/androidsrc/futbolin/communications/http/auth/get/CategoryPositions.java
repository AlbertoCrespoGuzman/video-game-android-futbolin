package com.androidsrc.futbolin.communications.http.auth.get;

public class CategoryPositions {
    long id;
    long team_id;
    int points;
    int played;
    int won;
    int tied;
    int lost;
    int goals_favor;
    int goals_against;
    int goals_difference;
    int last_position;
    String team_name;
    String team_short_name;

    public CategoryPositions(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(long team_id) {
        this.team_id = team_id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getTied() {
        return tied;
    }

    public void setTied(int tied) {
        this.tied = tied;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
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

    public int getLast_position() {
        return last_position;
    }

    public void setLast_position(int last_position) {
        this.last_position = last_position;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_short_name() {
        return team_short_name;
    }

    public void setTeam_short_name(String team_short_name) {
        this.team_short_name = team_short_name;
    }

    @Override
    public String toString() {
        return "CategoryPositions{" +
                "id=" + id +
                ", team_id=" + team_id +
                ", points=" + points +
                ", played=" + played +
                ", won=" + won +
                ", tied=" + tied +
                ", lost=" + lost +
                ", goals_favor=" + goals_favor +
                ", goals_against=" + goals_against +
                ", goals_difference=" + goals_difference +
                ", last_position=" + last_position +
                ", team_name='" + team_name + '\'' +
                ", team_short_name='" + team_short_name + '\'' +
                '}';
    }
}
