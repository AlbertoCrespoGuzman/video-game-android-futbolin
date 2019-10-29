package com.androidsrc.futbolin.communications.http.auth.post;

public class PostMatch {
    String device_id;
    long rival;

    public PostMatch(){}

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public long getRival() {
        return rival;
    }

    public void setRival(long rival) {
        this.rival = rival;
    }

    @Override
    public String toString() {
        return "PostMatch{" +
                "device_id='" + device_id + '\'' +
                ", rival=" + rival +
                '}';
    }
}
