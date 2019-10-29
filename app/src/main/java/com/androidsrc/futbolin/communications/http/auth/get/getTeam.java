package com.androidsrc.futbolin.communications.http.auth.get;

import com.androidsrc.futbolin.database.models.Team;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class getTeam implements Serializable {

    public Team getTeam() {
        return team;
    }

    public void getTeam(Team team) {
        this.team = team;
    }

    @SerializedName("team")
    private Team team;


}