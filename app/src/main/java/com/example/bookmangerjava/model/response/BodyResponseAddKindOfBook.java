package com.example.bookmangerjava.model.response;

import com.example.bookmangerjava.model.KindOfBook;
import com.example.bookmangerjava.model.Message;

public class BodyResponseAddKindOfBook {
    private KindOfBook data;
    private Message message;

    public KindOfBook getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
