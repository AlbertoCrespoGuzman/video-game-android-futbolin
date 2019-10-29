package com.androidsrc.futbolin.database.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SystemSubNotifications  implements Serializable {

    @SerializedName("id")
    long id;
    @SerializedName("title")
    String title;
    @SerializedName("message")
    String message;
    @SerializedName("published")
    String published;
    @SerializedName("unread")
    boolean unread;
    @SerializedName("created_at")
    String created_at;


    public SystemSubNotifications(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "SystemSubNotifications{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", published='" + published + '\'' +
                ", unread=" + unread +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
