package com.androidsrc.futbolin.communications.http.auth.post;

public class PostTrain {

    String device_id;
    boolean restart;

    public PostTrain(){}

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public boolean isRestart() {
        return restart;
    }

    public void setRestart(boolean restart) {
        this.restart = restart;
    }

    @Override
    public String toString() {
        return "PostTrain{" +
                "device_id='" + device_id + '\'' +
                ", restart=" + restart +
                '}';
    }
}
