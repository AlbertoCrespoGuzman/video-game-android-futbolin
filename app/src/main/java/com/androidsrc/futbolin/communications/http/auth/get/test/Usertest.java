package com.androidsrc.futbolin.communications.http.auth.get.test;

import com.google.gson.annotations.SerializedName;

public class Usertest {

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @SerializedName("user")
    public User user;

    public class User {

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

        @SerializedName("last_name")
        private String lastName;
        @SerializedName("id")
        private long id;
        @SerializedName("first_name")
        private String firstName;

        @Override
        public String toString() {
            return "User{" +
                    "lastName='" + lastName + '\'' +
                    ", id=" + id  +
                    ", firstName='" + firstName + '\'' +
                    '}';
        }
    }
}