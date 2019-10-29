package com.androidsrc.futbolin.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class MarketNotification {
    @DatabaseField( allowGeneratedIdInsert=true, generatedId = true)
    long id;

    @DatabaseField
    long followplayerid;

    @DatabaseField
    long offerplayerid;

    @DatabaseField
    long messageid;

    @DatabaseField
    boolean notified;

    public MarketNotification(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFollowplayerid() {
        return followplayerid;
    }

    public void setFollowplayerid(long followplayerid) {
        this.followplayerid = followplayerid;
    }

    public long getOfferplayerid() {
        return offerplayerid;
    }

    public void setOfferplayerid(long offerplayerid) {
        this.offerplayerid = offerplayerid;
    }

    public long getMessageid() {
        return messageid;
    }

    public void setMessageid(long messageid) {
        this.messageid = messageid;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    @Override
    public String toString() {
        return "MarketNotification{" +
                "id=" + id +
                ", followplayerid=" + followplayerid +
                ", offerplayerid=" + offerplayerid +
                ", messageid=" + messageid +
                ", notified=" + notified +
                '}';
    }
}
