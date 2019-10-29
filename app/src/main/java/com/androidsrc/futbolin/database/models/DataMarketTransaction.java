package com.androidsrc.futbolin.database.models;

import com.androidsrc.futbolin.communications.http.auth.get.FriendlyTeam;

public class DataMarketTransaction {
    long value;
    String created_at;



    Player player;
    FriendlyTeam seller;
    FriendlyTeam buyer;

    public DataMarketTransaction(){}

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public FriendlyTeam getSeller() {
        return seller;
    }

    public void setSeller(FriendlyTeam seller) {
        this.seller = seller;
    }

    public FriendlyTeam getBuyer() {
        return buyer;
    }

    public void setBuyer(FriendlyTeam buyer) {
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        return "DataMarketTransaction{" +
                "value=" + value +
                ", created_at='" + created_at + '\'' +
                ", player=" + player +
                ", seller=" + seller +
                ", buyer=" + buyer +
                '}';
    }
}
