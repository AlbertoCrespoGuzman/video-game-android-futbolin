package com.androidsrc.futbolin.communications.http.auth.get;

import com.androidsrc.futbolin.database.models.Team;

import java.util.List;
import java.util.Map;

public class getMatch {
    int incomes;
    int assistance;
    boolean show_remaining;
    int remaining;
    String remaining_readable;
    String datetime;
    String stadium;
    Scorers scorers;

    TeamMatch local;
    TeamMatch visit;
    List<Highlight> highlights;

    public getMatch(){}

    public int getIncomes() {
        return incomes;
    }

    public void setIncomes(int incomes) {
        this.incomes = incomes;
    }

    public boolean isShow_remaining() {
        return show_remaining;
    }

    public void setShow_remaining(boolean show_remaining) {
        this.show_remaining = show_remaining;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public String getRemaining_readable() {
        return remaining_readable;
    }

    public void setRemaining_readable(String remaining_readable) {
        this.remaining_readable = remaining_readable;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public Scorers getScorers() {
        return scorers;
    }

    public void setScorers(Scorers scorers) {
        this.scorers = scorers;
    }

    public List<Highlight> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<Highlight> highlights) {
        this.highlights = highlights;
    }

    public int getAssistance() {
        return assistance;
    }

    public void setAssistance(int assistance) {
        this.assistance = assistance;
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

    @Override
    public String toString() {
        return "getMatch{" +
                "incomes=" + incomes +
                ", assistance=" + assistance +
                ", show_remaining=" + show_remaining +
                ", remaining=" + remaining +
                ", remaining_readable='" + remaining_readable + '\'' +
                ", datetime='" + datetime + '\'' +
                ", stadium='" + stadium + '\'' +
                ", scorers=" + scorers +
                ", local=" + local +
                ", visit=" + visit +
                ", highlights=" + highlights +
                '}';
    }
}
