package com.androidsrc.futbolin.communications.http.auth.post;

public class PostPlayerOffer {
    String device_id;
    long player_id;
    long offer;

    public PostPlayerOffer(){}

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public long getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(long player_id) {
        this.player_id = player_id;
    }

    public long getOffer() {
        return offer;
    }

    public void setOffer(long offer) {
        this.offer = offer;
    }

    @Override
    public String toString() {
        return "PostPlayerOffer{" +
                "device_id='" + device_id + '\'' +
                ", player_id=" + player_id +
                ", offer=" + offer +
                '}';
    }
}
