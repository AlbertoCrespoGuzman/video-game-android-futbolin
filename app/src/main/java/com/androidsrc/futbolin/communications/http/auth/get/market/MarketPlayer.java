package com.androidsrc.futbolin.communications.http.auth.get.market;

import java.util.List;

public class MarketPlayer {
    long id;
    String name;
    long team_id;
    String team;
    String position = "";
    int average = 99;
    List<String> icons;
    int age;


    public MarketPlayer(){}

    public List<String> getIcons() {
        return icons;
    }

    public void setIcons(List<String> icons) {
        this.icons = icons;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
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

    public long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(long team_id) {
        this.team_id = team_id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "MarketPlayer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", team_id=" + team_id +
                ", team='" + team + '\'' +
                ", position='" + position + '\'' +
                ", average=" + average +
                '}';
    }
}
