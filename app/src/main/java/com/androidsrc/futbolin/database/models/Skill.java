package com.androidsrc.futbolin.database.models;

import java.io.Serializable;

public class Skill implements Serializable {
    int goalkeeping;
    int defending;
    int dribbling;
    int heading;
    int jumping;
    int passing;
    int precision;
    int speed;
    int strength;
    int tackling;
    int condition;
    int stamina;
    int experience;

    public Skill(){

    }

    public int getGoalkeeping() {
        return goalkeeping;
    }

    public void setGoalkeeping(int goalkeeping) {
        this.goalkeeping = goalkeeping;
    }

    public int getDefending() {
        return defending;
    }

    public void setDefending(int defending) {
        this.defending = defending;
    }

    public int getDribbling() {
        return dribbling;
    }

    public void setDribbling(int dribbling) {
        this.dribbling = dribbling;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public int getJumping() {
        return jumping;
    }

    public void setJumping(int jumping) {
        this.jumping = jumping;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getTackling() {
        return tackling;
    }

    public void setTackling(int tackling) {
        this.tackling = tackling;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "goalkeeping=" + goalkeeping +
                ", defending=" + defending +
                ", dribbling=" + dribbling +
                ", heading=" + heading +
                ", jumping=" + jumping +
                ", passing=" + passing +
                ", precision=" + precision +
                ", speed=" + speed +
                ", stregth=" + strength +
                ", tackling=" + tackling +
                ", condition=" + condition +
                ", stamina=" + stamina +
                ", experience=" + experience +
                '}';
    }
}
