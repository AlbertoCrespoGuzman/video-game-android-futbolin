package com.androidsrc.futbolin.communications.http.auth.post;

public class PostMatchResponse {

    long rival;
    String log_file;
    int remaining;
    String remaining_readable;


    public PostMatchResponse(){

    }

    public long getRival() {
        return rival;
    }

    public void setRival(long rival) {
        this.rival = rival;
    }

    public String getLog_file() {
        return log_file;
    }

    public void setLog_file(String log_file) {
        this.log_file = log_file;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public String getRemaining_readable() {
        return remaining_readable;
    }

    public void setRemaining_readable(String remaining_readable) {
        this.remaining_readable = remaining_readable;
    }

    @Override
    public String toString() {
        return "PostMatchResponse{" +
                "rival=" + rival +
                ", log_file='" + log_file + '\'' +
                ", remaining=" + remaining +
                ", remaining_readable='" + remaining_readable + '\'' +
                '}';
    }
}
