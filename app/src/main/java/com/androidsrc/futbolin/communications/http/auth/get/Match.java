package com.androidsrc.futbolin.communications.http.auth.get;

public class Match {
    String stadium;
    int type_id;
    int local_id;
    int local_goals;
    int visit_id;
    int visit_goals;
    int winner; // 0 = tied, 1 = local = 2 = visit
    String logfile;

    public Match(){

    }

    public String getLogfile() {
        return logfile;
    }

    public void setLogfile(String logfile) {
        this.logfile = logfile;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public int getLocal_goals() {
        return local_goals;
    }

    public void setLocal_goals(int local_goals) {
        this.local_goals = local_goals;
    }

    public int getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(int visit_id) {
        this.visit_id = visit_id;
    }

    public int getVisit_goals() {
        return visit_goals;
    }

    public void setVisit_goals(int visit_goals) {
        this.visit_goals = visit_goals;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Match{" +
                "stadium='" + stadium + '\'' +
                ", type_id=" + type_id +
                ", local_id=" + local_id +
                ", local_goals=" + local_goals +
                ", visit_id=" + visit_id +
                ", visit_goals=" + visit_goals +
                ", winner=" + winner +
                ", logfile='" + logfile + '\'' +
                '}';
    }
}
