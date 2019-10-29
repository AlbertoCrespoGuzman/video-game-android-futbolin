package com.androidsrc.futbolin.communications.http.auth.patch;

public class PatchStrategy {
    String device_id;
    long strategy;

    public PatchStrategy(){

    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public long getStrategy() {
        return strategy;
    }

    public void setStrategy(long strategy) {
        this.strategy = strategy;
    }

    @Override
    public String toString() {
        return "PatchStrategy{" +
                "device_id='" + device_id + '\'' +
                ", strategy_id=" + strategy +
                '}';
    }
}
