package com.androidsrc.futbolin.communications.http.auth.get.market;

public class MarketObject {
    long id;
    long value;
    long offer_value;
    long offer_team;
    long closes_at;
    MarketPlayer player;
    boolean following;

    public MarketObject(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getOffer_value() {
        return offer_value;
    }

    public void setOffer_value(long offer_value) {
        this.offer_value = offer_value;
    }

    public long getCloses_at() {
        return closes_at;
    }

    public void setCloses_at(long closes_at) {
        this.closes_at = closes_at;
    }

    public MarketPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MarketPlayer player) {
        this.player = player;
    }

    public long getOffer_team() {
        return offer_team;
    }

    public void setOffer_team(long offer_team) {
        this.offer_team = offer_team;
    }

    @Override
    public String toString() {
        return "MarketObject{" +
                "id=" + id +
                ", value=" + value +
                ", offer_value=" + offer_value +
                ", offer_team=" + offer_team +
                ", closes_at=" + closes_at +
                ", player=" + player +
                ", following=" + following +
                '}';
    }
}
