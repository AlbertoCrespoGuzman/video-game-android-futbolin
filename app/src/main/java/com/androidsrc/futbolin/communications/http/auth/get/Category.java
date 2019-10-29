package com.androidsrc.futbolin.communications.http.auth.get;

import java.util.List;

public class Category {
    long id;
    long tournament_id;
    int category;
    int zone;
    String category_name;
    String zone_name;
    List<Round> rounds;
    List<CategoryPositions> positions;
    List<RoundScorers> scorers;
    public Category(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(long tournament_id) {
        this.tournament_id = tournament_id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }

    public List<CategoryPositions> getPositions() {
        return positions;
    }

    public void setPositions(List<CategoryPositions> positions) {
        this.positions = positions;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public List<RoundScorers> getScorers() {
        return scorers;
    }

    public void setScorers(List<RoundScorers> scorers) {
        this.scorers = scorers;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", tournament_id=" + tournament_id +
                ", category=" + category +
                ", zone=" + zone +
                ", category_name='" + category_name + '\'' +
                ", zone_name='" + zone_name + '\'' +
                ", rounds=" + rounds +
                ", positions=" + positions +
                ", scorers=" + scorers +
                '}';
    }
}
