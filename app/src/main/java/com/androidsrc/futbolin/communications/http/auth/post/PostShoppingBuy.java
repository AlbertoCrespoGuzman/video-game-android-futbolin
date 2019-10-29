package com.androidsrc.futbolin.communications.http.auth.post;

public class PostShoppingBuy {
    String device_id;
    long id;
    long player_id;

    public PostShoppingBuy(){

    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(long player_id) {
        this.player_id = player_id;
    }

    @Override
    public String toString() {
        return "PostShoppingBuy{" +
                "device_id='" + device_id + '\'' +
                ", id=" + id +
                ", player_id=" + player_id +
                '}';
    }
}
