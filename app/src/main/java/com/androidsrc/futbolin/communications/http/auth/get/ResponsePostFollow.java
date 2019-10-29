package com.androidsrc.futbolin.communications.http.auth.get;

public class ResponsePostFollow {

    long id;

    public ResponsePostFollow(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ResponsePostFollow{" +
                "id=" + id +
                '}';
    }
}
