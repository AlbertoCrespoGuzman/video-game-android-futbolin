package com.androidsrc.futbolin.communications.http.auth.post;

public class PostTeam {

    String device_id;
    String name;
    String short_name;
    String stadium_name;
    String primary_color;
    String secondary_color;
    int shield;

    public PostTeam(){}

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getStadium_name() {
        return stadium_name;
    }

    public void setStadium_name(String stadium_name) {
        this.stadium_name = stadium_name;
    }

    public String getPrimary_color() {
        return primary_color;
    }

    public void setPrimary_color(String primary_color) {
        this.primary_color = primary_color;
    }

    public String getSecondary_color() {
        return secondary_color;
    }

    public void setSecondary_color(String secondary_color) {
        this.secondary_color = secondary_color;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    @Override
    public String toString() {
        return "PostTeam{" +
                "devide_id='" + device_id + '\'' +
                ", name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", stadium_name='" + stadium_name + '\'' +
                ", primary_color='" + primary_color + '\'' +
                ", secondary_color='" + secondary_color + '\'' +
                ", shield=" + shield +
                '}';
    }
}
