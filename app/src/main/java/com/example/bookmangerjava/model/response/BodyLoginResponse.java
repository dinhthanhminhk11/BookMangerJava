package com.example.bookmangerjava.model.response;

import com.example.bookmangerjava.model.Message;
import com.example.bookmangerjava.model.User;

public class BodyLoginResponse {
    private User data;
    private Message message;

    public User getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
