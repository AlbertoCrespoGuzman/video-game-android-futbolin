package com.androidsrc.futbolin.communications.http.auth.get;

import com.androidsrc.futbolin.utils.ScorersAccount;

import java.util.List;

public class MatchLive {

    int assistance;
    int incomes;
    long datetime;
    String stadium;
    TeamMatch local;
    TeamMatch visit;
    Scorers scorers;
    List<Highlight> plays;


    transient ScorersAccount scorersAccount;

    public MatchLive(){}

    public ScorersAccount getScorersAccount() {
        return scorersAccount;
    }

    public void setScorersAccount(ScorersAccount scorersAccount) {
        this.scorersAccount = scorersAccount;
    }

    public int getAssistance() {
        return assistance;
    }

    public void setAssistance(int assistance) {
        this.assistance = assistance;
    }

    public int getIncomes() {
        return incomes;
    }

    public void setIncomes(int incomes) {
        this.incomes = incomes;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public TeamMatch getLocal() {
        return local;
    }

    public void setLocal(TeamMatch local) {
        this.local = local;
    }

    public TeamMatch getVisit() {
        return visit;
    }

    public void setVisit(TeamMatch visit) {
        this.visit = visit;
    }

    public Scorers getScorers() {
        return scorers;
    }

    public void setScorers(Scorers scorers) {
        this.scorers = scorers;
    }

    public List<Highlight> getPlays() {
        return plays;
    }

    public void setPlays(List<Highlight> plays) {
        this.plays = plays;
    }

    @Override
    public String toString() {
        return "MatchLive{" +
                "assistance=" + assistance +
                ", incomes=" + incomes +
                ", datetime=" + datetime +
                ", stadium='" + stadium + '\'' +
                ", local=" + local +
                ", visit=" + visit +
                ", scorers=" + scorers +
                ", plays=" + plays +
                ", scorersAccount=" + scorersAccount +
                '}';
    }
}
