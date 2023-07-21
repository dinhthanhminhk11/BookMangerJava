package com.example.bookmangerjava.model.response;

import com.example.bookmangerjava.model.Message;
import com.example.bookmangerjava.model.User;

import java.util.List;

public class BodyUserListResponse {
    private List<User> data;
    private Message message;

    public List<User> getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
