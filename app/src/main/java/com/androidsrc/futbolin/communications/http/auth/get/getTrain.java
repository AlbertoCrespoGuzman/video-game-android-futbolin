package com.androidsrc.futbolin.communications.http.auth.get;

public class getTrain {
    boolean trained;
    int count;
    int points;
    int next;
    boolean show_options;
    int credits;

    public getTrain(){}

    public boolean isTrained() {
        return trained;
    }

    public void setTrained(boolean trained) {
        this.trained = trained;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public boolean isShow_options() {
        return show_options;
    }

    public void setShow_options(boolean show_options) {
        this.show_options = show_options;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "getTrain{" +
                "trained=" + trained +
                ", count=" + count +
                ", points=" + points +
                ", next=" + next +
                ", show_options=" + show_options +
                '}';
    }
}
