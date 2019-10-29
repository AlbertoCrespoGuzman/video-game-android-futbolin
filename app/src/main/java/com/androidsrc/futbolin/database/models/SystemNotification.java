package com.androidsrc.futbolin.database.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SystemNotification implements Serializable {

    @SerializedName("unread")
    int unread;
    @SerializedName("messages")
    List<SystemSubNotifications> messages;
    @SerializedName("notifications")
    List<SystemSubNotifications> notifications;
    @SerializedName("transferables")
    List<Player> transferables;
    @SerializedName("suspensions")
    List<Player> suspensions;
    @SerializedName("injuries")
    List<Player> injuries;
    @SerializedName("retiring")
    List<Player> retiring;
    @SerializedName("upgraded")
    List<Player> upgraded;

    public SystemNotification(){

    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public List<SystemSubNotifications> getMessages() {
        return messages;
    }

    public void setMessages(List<SystemSubNotifications> messages) {
        this.messages = messages;
    }

    public List<SystemSubNotifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<SystemSubNotifications> notifications) {
        this.notifications = notifications;
    }

    public List<Player> getTransferables() {
        return transferables;
    }

    public void setTransferables(List<Player> transferables) {
        this.transferables = transferables;
    }

    public List<Player> getSuspensions() {
        return suspensions;
    }

    public void setSuspensions(List<Player> suspensions) {
        this.suspensions = suspensions;
    }

    public List<Player> getInjuries() {
        return injuries;
    }

    public void setInjuries(List<Player> injuries) {
        this.injuries = injuries;
    }

    public List<Player> getRetiring() {
        return retiring;
    }

    public void setRetiring(List<Player> retiring) {
        this.retiring = retiring;
    }

    public List<Player> getUpgraded() {
        return upgraded;
    }

    public void setUpgraded(List<Player> upgraded) {
        this.upgraded = upgraded;
    }

    @Override
    public String toString() {
        return "SystemNotification{" +
                "unread=" + unread +
                ", messages=" + messages +
                ", notifications=" + notifications +
                ", transferables=" + transferables +
                ", suspensions=" + suspensions +
                ", injuries=" + injuries +
                ", retiring=" + retiring +
                ", upgraded=" + upgraded +
                '}';
    }
}
