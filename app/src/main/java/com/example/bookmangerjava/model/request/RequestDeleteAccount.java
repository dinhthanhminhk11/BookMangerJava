package com.example.bookmangerjava.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestDeleteAccount {
    @SerializedName("username")
    private String username;

    public RequestDeleteAccount(String username) {
        this.username = username;
    }
}
