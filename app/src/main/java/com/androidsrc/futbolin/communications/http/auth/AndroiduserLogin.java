package com.androidsrc.futbolin.communications.http.auth;

/**
 * Created by alberto on 2017/12/06.
 */

public class AndroiduserLogin {

    String email;
    String password;
    String device_id;
    String device_name;
    String push_notification_token;

    public AndroiduserLogin(){
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

    public String getPush_notification_token() {
        return push_notification_token;
    }

    public void setPush_notification_token(String push_notification_token) {
        this.push_notification_token = push_notification_token;
    }

    @Override
    public String toString() {
        return "AndroiduserLogin{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", device_id='" + device_id + '\'' +
                ", device_name='" + device_name + '\'' +
                ", push_notification_token='" + push_notification_token + '\'' +
                '}';
    }
}
