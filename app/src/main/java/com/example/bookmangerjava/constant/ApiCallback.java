package com.example.bookmangerjava.constant;

public interface ApiCallback<T> {
    void onSuccess(T data);

    void onError(Throwable t);
}
