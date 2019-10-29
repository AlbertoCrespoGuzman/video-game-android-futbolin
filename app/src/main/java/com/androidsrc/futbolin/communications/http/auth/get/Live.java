package com.androidsrc.futbolin.communications.http.auth.get;

import java.util.List;

public class Live {

    String category_name;
    String zone_name;
    String tournament_name;
    int round_number;

    List<MatchLive> matches;

    List<CategoryPositions> positions;

    public Live(){}

    public int getRound_number() {
        return round_number;
    }

    public void setRound_number(int round_number) {
        this.round_number = round_number;
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

    public String getTournament_name() {
        return tournament_name;
    }

    public void setTournament_name(String tournament_name) {
        this.tournament_name = tournament_name;
    }

    public List<MatchLive> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchLive> matches) {
        this.matches = matches;
    }

    public List<CategoryPositions> getPositions() {
        return positions;
    }

    public void setPositions(List<CategoryPositions> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "Live{" +
                "category_name='" + category_name + '\'' +
                ", zone_name='" + zone_name + '\'' +
                ", tournament_name='" + tournament_name + '\'' +
                ", round_number=" + round_number +
                ", matches=" + matches +
                ", positions=" + positions +
                '}';
    }
}
