package com.example.bookmangerjava.model.response;

import com.example.bookmangerjava.model.Message;
import com.example.bookmangerjava.model.Revenue;

public class BodyResponseRevenue {
    public Revenue data;
    public Message message;

    public Revenue getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
