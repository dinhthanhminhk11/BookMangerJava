package com.example.bookmangerjava.model;

public class Book {
    private String name;
    private int price;
    private int discount;
    private String nameCategory;

    public String idCategory;
    public String _id;
    public int __v;

    public Book(String name, int price, int discount, String idCategory) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.idCategory = idCategory;
    }

    public Book(String name, int price, int discount, String idCategory, String nameCategory, String _id) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
        this._id = _id;

    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public String get_id() {
        return _id;
    }

    public int get__v() {
        return __v;
    }
}
