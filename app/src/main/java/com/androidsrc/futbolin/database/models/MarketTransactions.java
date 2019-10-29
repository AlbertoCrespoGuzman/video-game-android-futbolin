package com.androidsrc.futbolin.database.models;

import java.util.List;

public class MarketTransactions {
    int current_page;
    int from;
    int to;
    int total;


    int last_page;
    int per_page;
    String next_page_url;
    String path;
    String prev_page_url;

    List<DataMarketTransaction> data;

    public MarketTransactions(){}

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
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

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public List<DataMarketTransaction> getData() {
        return data;
    }

    public void setData(List<DataMarketTransaction> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MarketTransactions{" +
                "current_page=" + current_page +
                ", from=" + from +
                ", to=" + to +
                ", total=" + total +
                ", last_page=" + last_page +
                ", per_page=" + per_page +
                ", next_page_url='" + next_page_url + '\'' +
                ", path='" + path + '\'' +
                ", prev_page_url='" + prev_page_url + '\'' +
                ", data=" + data +
                '}';
    }
}
