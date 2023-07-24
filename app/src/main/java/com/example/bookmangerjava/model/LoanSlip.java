package com.example.bookmangerjava.model;

public class LoanSlip {
    private String nameUser;
    private String phoneUser;
    private String idBook;
    private int price;
    private long borrowedDate;
    private long payDay;
    private long actualPaymentDate;
    private boolean isPay;
    private String _id;
    private int __v;

    private String nameBook;

    public LoanSlip(String nameUser, String phoneUser, String idBook, int price, long payDay) {
        this.nameUser = nameUser;
        this.phoneUser = phoneUser;
        this.idBook = idBook;
        this.price = price;
        this.payDay = payDay;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public String getIdBook() {
        return idBook;
    }

    public int getPrice() {
        return price;
    }

    public long getBorrowedDate() {
        return borrowedDate;
    }

    public long getPayDay() {
        return payDay;
    }

    public boolean isPay() {
        return isPay;
    }

    public String get_id() {
        return _id;
    }

    public int get__v() {
        return __v;
    }

    public String getNameBook() {
        return nameBook;
    }

    public long getActualPaymentDate() {
        return actualPaymentDate;
    }
}
