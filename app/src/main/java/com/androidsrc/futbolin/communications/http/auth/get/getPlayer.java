package com.androidsrc.futbolin.communications.http.auth.get;


import com.androidsrc.futbolin.database.models.Player;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class getPlayer implements Serializable {

    public Player getPlayer() {
        return player;
    }

    public void getPlayer(Player player) {
        this.player = player;
    }

    @SerializedName("player")
    private Player player;


}