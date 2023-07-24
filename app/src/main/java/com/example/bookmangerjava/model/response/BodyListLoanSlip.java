package com.example.bookmangerjava.model.response;

import com.example.bookmangerjava.model.LoanSlip;
import com.example.bookmangerjava.model.Message;

import java.util.List;

public class BodyListLoanSlip {
    private List<LoanSlip> data;
    private Message message;

    public List<LoanSlip> getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
