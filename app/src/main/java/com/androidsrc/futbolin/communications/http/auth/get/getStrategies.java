package com.androidsrc.futbolin.communications.http.auth.get;

import com.androidsrc.futbolin.database.models.Strategy;

import java.util.List;

public class getStrategies {

    List<Strategy> strategies;

    public getStrategies(){}

    public List<Strategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<Strategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public String toString() {
        return "getStrategies{" +
                "strategies=" + strategies +
                '}';
    }
}
