package com.androidsrc.futbolin.communications.http.auth.get;

import com.androidsrc.futbolin.database.models.Team;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class getTeamWithFormationId implements Serializable {

    public TeamWithFormationId getTeam() {
        return team;
    }

    public void getTeam(TeamWithFormationId team) {
        this.team = team;
    }

    @SerializedName("team")
    private TeamWithFormationId team;


}