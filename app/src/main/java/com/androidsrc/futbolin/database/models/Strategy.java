package com.androidsrc.futbolin.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable
public class Strategy {

    @DatabaseField( allowGeneratedIdInsert=true, generatedId = true)
    private long id;

    @DatabaseField
    String name;

    @ForeignCollectionField(eager = true)
    transient ForeignCollection<Position> positionORM;

    List<Position> positions = new ArrayList<>();


    public Strategy(){

    }

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

    public List<Position> getPositions() {

        List<Position> p = new ArrayList<>();
        if(positionORM != null && positionORM.size()>0) {
                for (Position s : positionORM) {
                    p.add(s);
                }
            }
        else{
            p = positions;
        }
        positions = p;

        return positions;

    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public ForeignCollection<Position> getPositionsORM() {
        return positionORM;
    }

    public void setPositionsORM(ForeignCollection<Position> positionsORM) {
        this.positionORM = positionsORM;
    }


    public void addPositionORM(Position l){
        try {
            this.positionORM.add(l);
        }catch(Exception e){
            try {
                this.positionORM.update(l);
            }catch (SQLException ex){ex.printStackTrace();}
        }
    }
    @Override
    public String toString() {
        return "Strategy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", positions=" + getPositions().toString() +
                '}';
    }
}
