package com.example.bookmangerjava.model.response;

import com.example.bookmangerjava.model.KindOfBook;
import com.example.bookmangerjava.model.Message;

import java.util.List;

public class BodyKindOfBookResponse {
    private List<KindOfBook> data;
    private Message message;

    public List<KindOfBook> getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
