package com.androidsrc.futbolin.communications.http.auth.post;

public class PostPlayerTransferible {
    String device_id;

    public PostPlayerTransferible(){

    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    @Override
    public String toString() {
        return "PostPlayerTransferible{" +
                "device_id='" + device_id + '\'' +
                '}';
    }
}
