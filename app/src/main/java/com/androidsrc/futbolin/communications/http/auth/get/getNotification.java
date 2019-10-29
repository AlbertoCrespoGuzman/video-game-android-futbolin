package com.androidsrc.futbolin.communications.http.auth.get;

public class getNotification {

    long id;
    String title;
    String message;
    int unread;

    public getNotification(){}

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

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    @Override
    public String toString() {
        return "getNotification{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", unread=" + unread +
                '}';
    }
}
