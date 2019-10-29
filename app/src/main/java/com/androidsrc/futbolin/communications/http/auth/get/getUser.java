package com.androidsrc.futbolin.communications.http.auth.get;

import com.androidsrc.futbolin.database.models.SystemNotification;
import com.androidsrc.futbolin.database.models.Team;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class getUser implements Serializable {

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @SerializedName("user")
    private User user;
    @SerializedName("notifications")
    private SystemNotification notifications;

    public SystemNotification getNotifications() {
        return notifications;
    }

    public void setNotifications(SystemNotification notifications) {
        this.notifications = notifications;
    }

    public static class User implements Serializable{

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Team getTeam() {
            return team;
        }

        public void setTeam(Team team) {
            this.team = team;
        }

        @SerializedName("last_name")
        private String lastName;
        @SerializedName("language")
        private String language;
        @SerializedName("id")
        private long id;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("credits")
        private int credits;
        @SerializedName("last_activity")
        private String last_activity;
        @SerializedName("team")
        private Team team;

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public int getCredits() {
            return credits;
        }

        public void setCredits(int credits) {
            this.credits = credits;
        }

        public String getLast_activity() {
            return last_activity;
        }

        public void setLast_activity(String last_activity) {
            this.last_activity = last_activity;
        }

        @Override
        public String toString() {
            return "User{" +
                    "lastName='" + lastName + '\'' +
                    ", language='" + language + '\'' +
                    ", id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", credits=" + credits +
                    ", last_activity='" + last_activity + '\'' +
                    ", team=" + team +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "getUser{" +
                "user=" + user +
                ", notifications=" + notifications +
                '}';
    }
}