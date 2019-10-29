package com.androidsrc.futbolin.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Position  implements Serializable {

    @DatabaseField( allowGeneratedIdInsert=true, generatedId = true)
    transient long id;

    @DatabaseField
    float left;

    @DatabaseField
    float top;

    @DatabaseField
    String position;

    @DatabaseField(canBeNull = true)
    int number;

    @DatabaseField(canBeNull = true, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true,useGetSet =true)
    transient private Strategy strategy;

    String short_name;

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public Position(){}

    public float getLeft() {
        return left;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", left=" + left +
                ", top=" + top +
                ", position='" + position + '\'' +
                ", number=" + number +
                ", strategy=" + strategy +
                ", short_name='" + short_name + '\'' +
                '}';
    }
}
