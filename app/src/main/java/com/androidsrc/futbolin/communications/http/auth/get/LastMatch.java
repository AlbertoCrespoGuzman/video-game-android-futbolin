package com.androidsrc.futbolin.communications.http.auth.get;

import java.io.Serializable;

public class LastMatch implements Serializable {
    String date;
    long local_id;
    String local;
    int local_goals;
    long visit_id;
    String visit;
    int visit_goals;
    String log_file;
    boolean won;

    public LastMatch (){}

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getLocal_id() {
        return local_id;
    }

    public void setLocal_id(long local_id) {
        this.local_id = local_id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getLocal_goals() {
        return local_goals;
    }

    public void setLocal_goals(int local_goals) {
        this.local_goals = local_goals;
    }

    public long getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(long visit_id) {
        this.visit_id = visit_id;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public int getVisit_goals() {
        return visit_goals;
    }

    public void setVisit_goals(int visit_goals) {
        this.visit_goals = visit_goals;
    }

    public String getLog_file() {
        return log_file;
    }

    public void setLog_file(String log_file) {
        this.log_file = log_file;
    }

    @Override
    public String toString() {
        return "LastMatch{" +
                "date='" + date + '\'' +
                ", local_id=" + local_id +
                ", local='" + local + '\'' +
                ", local_goals=" + local_goals +
                ", visit_id=" + visit_id +
                ", visit='" + visit + '\'' +
                ", visit_goals=" + visit_goals +
                ", log_file='" + log_file + '\'' +
                ", won='" + won + '\'' +
                '}';
    }
}
