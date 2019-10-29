package com.androidsrc.futbolin.communications.http.auth.get;

import java.io.Serializable;
import java.util.List;

public class getTeams implements Serializable {
    List<FriendlyTeam> teams;
    List<FriendlyTeam> sparrings;

    public getTeams(){}

    public List<FriendlyTeam> getSparrings() {
        return sparrings;
    }

    public void setSparrings(List<FriendlyTeam> sparrings) {
        this.sparrings = sparrings;
    }

    public List<FriendlyTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<FriendlyTeam> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "getTeams{" +
                "teams=" + teams +
                ", sparrings=" + sparrings +
                '}';
    }
}
