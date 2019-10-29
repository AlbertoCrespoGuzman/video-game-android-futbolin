package com.androidsrc.futbolin.database.models;

import java.util.List;

public class MeTransactions {
    int current_page;
    List<DataMeTransaction> data;
    int from;
    int to;
    int per_page;
    int total;
    String prev_page_url;
    int last_page;
    String next_page_url;
    String path;

    public MeTransactions(){}

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<DataMeTransaction> getData() {
        return data;
    }

    public void setData(List<DataMeTransaction> data) {
        this.data = data;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "MeTransactions{" +
                "current_page=" + current_page +
                ", data=" + data +
                ", from=" + from +
                ", to=" + to +
                ", per_page=" + per_page +
                ", total=" + total +
                ", prev_page_url='" + prev_page_url + '\'' +
                ", last_page=" + last_page +
                ", next_page_url='" + next_page_url + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
