package com.androidsrc.futbolin.communications.http.auth.get;

public class NextMatch {
    long datetime;
    String Stadium;
    FriendlyTeam local;
    FriendlyTeam visit;

    public NextMatch(){

    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getStadium() {
        return Stadium;
    }

    public void setStadium(String stadium) {
        Stadium = stadium;
    }

    public FriendlyTeam getLocal() {
        return local;
    }

    public void setLocal(FriendlyTeam local) {
        this.local = local;
    }

    public FriendlyTeam getVisit() {
        return visit;
    }

    public void setVisit(FriendlyTeam visit) {
        this.visit = visit;
    }

    @Override
    public String toString() {
        return "NextMatch{" +
                "datetime=" + datetime +
                ", Stadium='" + Stadium + '\'' +
                ", local=" + local +
                ", visit=" + visit +
                '}';
    }
}
