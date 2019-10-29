package com.androidsrc.futbolin.communications.http.auth.patch;

public class PatchPlayerValue {
    String device_id;
    long value;

    public PatchPlayerValue(){}

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PatchPlayerValue{" +
                "device_id='" + device_id + '\'' +
                ", value=" + value +
                '}';
    }
}
