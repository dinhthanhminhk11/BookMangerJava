package com.example.bookmangerjava.model.response;

import com.example.bookmangerjava.model.Book;
import com.example.bookmangerjava.model.Message;

public class BodyResponseAddBook {
    private Book data;
    private Message message;

    public Book getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
