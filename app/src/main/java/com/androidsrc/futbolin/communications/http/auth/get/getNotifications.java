package com.androidsrc.futbolin.communications.http.auth.get;

import com.androidsrc.futbolin.database.models.SystemSubNotifications;

import java.util.List;

public class getNotifications {

    List<SystemSubNotifications> notifications;

    public getNotifications(){}

    public List<SystemSubNotifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<SystemSubNotifications> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return "getNotifications{" +
                "notifications=" + notifications +
                '}';
    }
}
