package com.androidsrc.futbolin.database.models;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by alberto on 2017/12/05.
 */

@DatabaseTable
public class User implements Serializable {

    public User(){
    }

    @DatabaseField( allowGeneratedIdInsert=true, generatedId = true)
    private long id;
    @DatabaseField
    String first_name;
    @DatabaseField
    String last_name;

    String language;

    @DatabaseField
    String email;
    @DatabaseField
    String password;
    @DatabaseField
    String password_confirmation;
    @DatabaseField
    String device_id;
    @DatabaseField
    String device_name;
    @DatabaseField
    transient String token;
    @DatabaseField
    transient boolean logged;

    String push_notification_token;


    public String getPush_notification_token() {
        return push_notification_token;
    }

    public void setPush_notification_token(String push_notification_token) {
        this.push_notification_token = push_notification_token;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
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



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", language='" + language + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", password_confirmation='" + password_confirmation + '\'' +
                ", device_id='" + device_id + '\'' +
                ", device_name='" + device_name + '\'' +
                ", token='" + token + '\'' +
                ", logged=" + logged +
                ", push_notification_token='" + push_notification_token + '\'' +
                '}';
    }
}
