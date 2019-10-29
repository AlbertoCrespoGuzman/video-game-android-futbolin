package com.androidsrc.futbolin.communications.http.auth;

/**
 * Created by alberto on 5/17/18.
 */

public class ResponseError {

    String type;
    String message;

    public ResponseError(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseError{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
