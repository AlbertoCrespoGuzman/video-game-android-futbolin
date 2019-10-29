package com.androidsrc.futbolin.communications.http.auth.get;

import java.util.List;

public class Tournament {
    long id;
    String name;
    int categories;
    int zones;

    public Tournament(){}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategories() {
        return categories;
    }

    public void setCategories(int categories) {
        this.categories = categories;
    }

    public int getZones() {
        return zones;
    }

    public void setZones(int zones) {
        this.zones = zones;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categories=" + categories +
                ", zones=" + zones +
                '}';
    }
}
