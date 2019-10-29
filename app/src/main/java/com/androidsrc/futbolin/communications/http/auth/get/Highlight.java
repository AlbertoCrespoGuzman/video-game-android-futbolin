package com.androidsrc.futbolin.communications.http.auth.get;

public class Highlight {
    String minutes;
    String background;
    String color;
    String highlight;
    int type;
    int team;

    @Override
    public String toString() {
        return "Highlight{" +
                "minutes='" + minutes + '\'' +
                ", background='" + background + '\'' +
                ", color='" + color + '\'' +
                ", highlight='" + highlight + '\'' +
                ", type=" + type +
                ", team=" + team +
                '}';
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public Highlight(){}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

}
