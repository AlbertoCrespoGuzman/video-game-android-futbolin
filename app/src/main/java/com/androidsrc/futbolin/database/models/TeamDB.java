package com.androidsrc.futbolin.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TeamDB {

    @DatabaseField( allowGeneratedIdInsert=true, generatedId = true)
    long localid;
    @DatabaseField
    long id;
    @DatabaseField
    String name;
    @DatabaseField
    String short_name;

    public TeamDB(){}


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

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public long getLocalid() {
        return localid;
    }

    public void setLocalid(long localid) {
        this.localid = localid;
    }

    @Override
    public String toString() {
        return "TeamDB{" +
                "localid=" + localid +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                '}';
    }
}
