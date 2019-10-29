package com.androidsrc.futbolin.communications.http.auth.get;

import java.util.List;

public class Scorers {
    List<Scorer> local;
    List<Scorer> visit;

    public Scorers(){}

    public List<Scorer> getLocal() {
        return local;
    }

    public void setLocal(List<Scorer> local) {
        this.local = local;
    }

    public List<Scorer> getVisit() {
        return visit;
    }

    public void setVisit(List<Scorer> visit) {
        this.visit = visit;
    }

    @Override
    public String toString() {
        return "Scorers{" +
                "local=" + local +
                ", visit=" + visit +
                '}';
    }
}
