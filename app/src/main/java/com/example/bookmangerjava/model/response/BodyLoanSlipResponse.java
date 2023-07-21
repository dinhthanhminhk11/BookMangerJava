package com.example.bookmangerjava.model.response;

import com.example.bookmangerjava.model.LoanSlip;
import com.example.bookmangerjava.model.Message;

public class BodyLoanSlipResponse {
    private LoanSlip data;
    private Message message;

    public LoanSlip getData() {
        return data;
    }

    public Message getMessage() {
        return message;
    }
}
