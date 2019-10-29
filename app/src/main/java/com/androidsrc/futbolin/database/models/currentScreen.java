package com.androidsrc.futbolin.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class currentScreen {
    @DatabaseField( allowGeneratedIdInsert=true, generatedId = true)
    long id;
    @DatabaseField
    String fragmentName;

    public currentScreen(){}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    @Override
    public String toString() {
        return "currentScreen{" +
                "id=" + id +
                ", fragmentName='" + fragmentName + '\'' +
                '}';
    }
}
