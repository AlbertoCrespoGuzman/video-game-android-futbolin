package com.androidsrc.futbolin.communications.http.auth.patch;

import java.util.List;

public class PatchFormation {
    String device_id;
    List<Long> formation;

    public PatchFormation(){}

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public List<Long> getFormation() {
        return formation;
    }

    public void setFormation(List<Long> formation) {
        this.formation = formation;
    }

    @Override
    public String toString() {
        return "PatchFormation{" +
                "device_id='" + device_id + '\'' +
                ", formation=" + formation +
                '}';
    }
}
