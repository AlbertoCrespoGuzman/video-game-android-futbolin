package com.androidsrc.futbolin.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

@DatabaseTable
public class Player implements Serializable {
    @DatabaseField( allowGeneratedIdInsert=true, generatedId = true)
    private long local_id;
    @DatabaseField
    private long id;
      @DatabaseField
    String first_name;
      @DatabaseField
    String last_name;
     @DatabaseField
    String position;
      @DatabaseField
    int age;
      @DatabaseField
    boolean retiring;
      @DatabaseField
    int injury_id;
      @DatabaseField
    int recovery;
       @DatabaseField
    boolean healed;
       @DatabaseField
    int goalkeeping;
     @DatabaseField
    int defending;
     @DatabaseField
    int dribbling;
     @DatabaseField
    int heading;
     @DatabaseField
    int jumping;
     @DatabaseField
    int passing;
     @DatabaseField
    int precision;
     @DatabaseField
    int speed;
    @DatabaseField
    int strength;
    @DatabaseField
    int tackling;
    @DatabaseField
    int condition;
    @DatabaseField
    int stamina;
    @DatabaseField
    int experience;
    @DatabaseField
     String last_upgraded;

    Skill last_upgrade;
    @DatabaseField
    int value;
    @DatabaseField
    int number;
    @DatabaseField
    long team_id;
    @DatabaseField
    String short_name;
    @DatabaseField
    int average;
    @DatabaseField
    int cards_count;
    @DatabaseField
    boolean suspended;
    @DatabaseField
    boolean upgraded;
    @DatabaseField
    boolean transferable;
    @DatabaseField
    String bladeHandlerIcons;
    @DatabaseField
    String name;
    SellingPlayer selling;
    public Player(){}

    public SellingPlayer getSelling() {
        return selling;
    }

    public void setSelling(SellingPlayer selling) {
        this.selling = selling;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public int getInjury_id() {
        return injury_id;
    }

    public void setInjury_id(int injury_id) {
        this.injury_id = injury_id;
    }

    public int getRecovery() {
        return recovery;
    }

    public void setRecovery(int recovery) {
        this.recovery = recovery;
    }


    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
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

    public boolean isRetiring() {
        return retiring;
    }

    public void setRetiring(boolean retiring) {
        this.retiring = retiring;
    }

    public boolean isHealed() {
        return healed;
    }

    public void setHealed(boolean healed) {
        this.healed = healed;
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




    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(long team_id) {
        this.team_id = team_id;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public int getCards_count() {
        return cards_count;
    }

    public void setCards_count(int cards_count) {
        this.cards_count = cards_count;
    }

    public String getLast_upgraded() {
        return last_upgraded;
    }

    public void setLast_upgraded(String last_upgraded) {
        this.last_upgraded = last_upgraded;
    }

    public Skill getLast_upgrade() {
        return last_upgrade;
    }

    public void setLast_upgrade(Skill last_upgrade) {
        this.last_upgrade = last_upgrade;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }

    public boolean isTransferable() {
        return transferable;
    }

    public void setTransferable(boolean transferable) {
        this.transferable = transferable;
    }

    public String getBladeHandlerIcons() {
        return bladeHandlerIcons;
    }

    public void setBladeHandlerIcons(String bladeHandlerIcons) {
        this.bladeHandlerIcons = bladeHandlerIcons;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", position='" + position + '\'' +
                ", age=" + age +
                ", retiring=" + retiring +
                ", injury_id=" + injury_id +
                ", recovery=" + recovery +
                ", healed=" + healed +
                ", goalkeeping=" + goalkeeping +
                ", defending=" + defending +
                ", dribbling=" + dribbling +
                ", heading=" + heading +
                ", jumping=" + jumping +
                ", passing=" + passing +
                ", precision=" + precision +
                ", speed=" + speed +
                ", strength=" + strength +
                ", tackling=" + tackling +
                ", condition=" + condition +
                ", stamina=" + stamina +
                ", experience=" + experience +
                ", last_upgraded='" + last_upgraded + '\'' +
                ", last_upgrade=" + last_upgrade +
                ", value=" + value +
                ", number=" + number +
                ", team_id=" + team_id +
                ", short_name='" + short_name + '\'' +
                ", average=" + average +
                ", cards_count=" + cards_count +
                ", suspended=" + suspended +
                ", upgraded=" + upgraded +
                ", transferable=" + transferable +
                ", bladeHandlerIcons='" + bladeHandlerIcons + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
