package com.androidsrc.futbolin.communications.http.auth.post;

public class PostShoppingBuyResponse {
    int credits;

    public PostShoppingBuyResponse(){}

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "PostShoppingBuyResponse{" +
                "credits=" + credits +
                '}';
    }
}
