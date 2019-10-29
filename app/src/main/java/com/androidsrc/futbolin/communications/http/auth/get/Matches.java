package com.androidsrc.futbolin.communications.http.auth.get;

import java.io.Serializable;

public class Matches  implements Serializable {
    MatchStats local;
    MatchStats visit;

    public Matches(){}

    public MatchStats getLocal() {
        return local;
    }

    public void setLocal(MatchStats local) {
        this.local = local;
    }

    public MatchStats getVisit() {
        return visit;
    }

    public void setVisit(MatchStats visit) {
        this.visit = visit;
    }

    @Override
    public String toString() {
        return "Matches{" +
                "local=" + local +
                ", visit=" + visit +
                '}';
    }
}
