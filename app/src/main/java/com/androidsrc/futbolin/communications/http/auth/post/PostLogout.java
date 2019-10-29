package com.androidsrc.futbolin.communications.http.auth.post;

public class PostLogout {
    String device_id;

    public PostLogout(){

    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    @Override
    public String toString() {
        return "PostLogout{" +
                "device_id='" + device_id + '\'' +
                '}';
    }
}
