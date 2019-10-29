package com.androidsrc.futbolin.database.models;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Notification {

    @DatabaseField( allowGeneratedIdInsert=true, generatedId = true)
    long id;
    @DatabaseField
    long lastTrain;
    @DatabaseField
    boolean trainActive = true;
    @DatabaseField
    boolean trainNotification;
    @DatabaseField
    int trainNotificationAccount = 0;
    @DatabaseField
    boolean matchNotification;
    @DatabaseField
    int matchNotificationAccount = 0;
    @DatabaseField
    boolean matchActive = true;
    @DatabaseField
    String nextMatchTeam;
    @DatabaseField
    long nextMatch;
    @DatabaseField
    boolean liveSoundsActive = true;
    @DatabaseField
    String language;


    public Notification(){}

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getTrainNotificationAccount() {
        return trainNotificationAccount;
    }

    public void setTrainNotificationAccount(int trainNotificationAccount) {
        this.trainNotificationAccount = trainNotificationAccount;
    }

    public int getMatchNotificationAccount() {
        return matchNotificationAccount;
    }

    public void setMatchNotificationAccount(int matchNotificationAccount) {
        this.matchNotificationAccount = matchNotificationAccount;
    }

    public String getNextMatchTeam() {
        return nextMatchTeam;
    }

    public void setNextMatchTeam(String nextMatchTeam) {
        this.nextMatchTeam = nextMatchTeam;
    }

    public long getId() {
        return id;
    }

    public boolean isTrainNotification() {
        return trainNotification;
    }

    public void setTrainNotification(final boolean trainNotification) {
        Log.e("NOTIFICATION", "setTrainNOTIFICATION -> " + trainNotification);
        this.trainNotification = trainNotification;
    }

    public boolean isLiveSoundsActive() {
        return liveSoundsActive;
    }

    public void setLiveSoundsActive(boolean liveSoundsActive) {
        this.liveSoundsActive = liveSoundsActive;
    }

    public boolean isMatchNotification() {
        return matchNotification;
    }

    public void setMatchNotification(boolean matchNotification) {
        this.matchNotification = matchNotification;
    }

    public boolean isTrainActive() {
        return trainActive;
    }

    public void setTrainActive(boolean trainActive) {
        this.trainActive = trainActive;
    }

    public boolean isMatchActive() {
        return matchActive;
    }

    public void setMatchActive(boolean matchActive) {
        this.matchActive = matchActive;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNextMatch() {
        return nextMatch;
    }

    public void setNextMatch(long nextMatch) {
        this.nextMatch = nextMatch;
    }

    public long getLastTrain() {
        return lastTrain;
    }

    public void setLastTrain(long lastTrain) {
        this.lastTrain = lastTrain;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", lastTrain=" + lastTrain +
                ", trainActive=" + trainActive +
                ", trainNotification=" + trainNotification +
                ", trainNotificationAccount=" + trainNotificationAccount +
                ", matchNotification=" + matchNotification +
                ", matchNotificationAccount=" + matchNotificationAccount +
                ", matchActive=" + matchActive +
                ", nextMatchTeam='" + nextMatchTeam + '\'' +
                ", nextMatch=" + nextMatch +
                ", liveSoundsActive=" + liveSoundsActive +
                ", language='" + language + '\'' +
                '}';
    }
}
