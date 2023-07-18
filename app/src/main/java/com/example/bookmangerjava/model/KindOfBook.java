package com.example.bookmangerjava.model;

public class KindOfBook {
    private String _id;
    private String name;

    public KindOfBook(String name) {
        this.name = name;
    }

    public KindOfBook(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    private int __v;

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public int get__v() {
        return __v;
    }
}
