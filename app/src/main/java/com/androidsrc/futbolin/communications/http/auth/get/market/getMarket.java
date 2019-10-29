package com.androidsrc.futbolin.communications.http.auth.get.market;

import java.util.List;

public class getMarket {
    List<MarketObject> market;

    public getMarket(){}

    public List<MarketObject> getMarket() {
        return market;
    }

    public void setMarket(List<MarketObject> market) {
        this.market = market;
    }

    @Override
    public String toString() {
        return "getMarket{" +
                "market=" + market +
                '}';
    }
}
