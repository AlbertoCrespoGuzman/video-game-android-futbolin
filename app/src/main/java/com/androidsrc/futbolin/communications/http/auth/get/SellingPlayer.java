package com.androidsrc.futbolin.communications.http.auth.get;

public class SellingPlayer {

    long id;
    long played_id;
    long value;
    long closes_at;
    long updated_at;

    public SellingPlayer(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlayed_id() {
        return played_id;
    }

    public void setPlayed_id(long played_id) {
        this.played_id = played_id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getCloses_at() {
        return closes_at;
    }

    public void setCloses_at(long closes_at) {
        this.closes_at = closes_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "SellingPlayer{" +
                "id=" + id +
                ", played_id=" + played_id +
                ", value=" + value +
                ", closes_at=" + closes_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
