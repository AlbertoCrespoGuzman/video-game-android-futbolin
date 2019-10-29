package com.androidsrc.futbolin.communications.http.auth.get;

public class RoundMatch {
    int assistance;
    int income;
    Match match;
    long local_id;
    long visit_id;
    String local_name;
    String visit_name;

    public RoundMatch(){

    }

    public long getLocal_id() {
        return local_id;
    }

    public void setLocal_id(long local_id) {
        this.local_id = local_id;
    }

    public long getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(long visit_id) {
        this.visit_id = visit_id;
    }

    public String getLocal_name() {
        return local_name;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    public String getVisit_name() {
        return visit_name;
    }

    public void setVisit_name(String visit_name) {
        this.visit_name = visit_name;
    }

    public int getAssistance() {
        return assistance;
    }

    public void setAssistance(int assistance) {
        this.assistance = assistance;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public String toString() {
        return "RoundMatch{" +
                "assistance=" + assistance +
                ", income=" + income +
                ", match=" + match +
                ", local_id=" + local_id +
                ", visit_id=" + visit_id +
                ", local_name='" + local_name + '\'' +
                ", visit_name='" + visit_name + '\'' +
                '}';
    }
}
