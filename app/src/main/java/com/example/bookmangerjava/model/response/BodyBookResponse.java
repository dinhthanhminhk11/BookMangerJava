package com.example.bookmangerjava.model.response;

import com.example.bookmangerjava.model.Book;
import com.example.bookmangerjava.model.Message;

import java.util.List;

public class BodyBookResponse {
    private List<Book> data;
    private Message message;

    public List<Book> getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
