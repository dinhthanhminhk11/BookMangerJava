package com.example.bookmangerjava.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;

import com.example.bookmangerjava.model.Book;

import java.util.List;

public class CustomBookAdapter extends ArrayAdapter<Book> {
    public CustomBookAdapter(Context context, List<Book> kindOfBookList) {
        super(context, 0, kindOfBookList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        CheckedTextView textView = convertView.findViewById(android.R.id.text1);
        Book book = getItem(position);
        if (book != null) {
            textView.setText(book.getName());
            textView.setTextColor(Color.rgb(191, 76, 65));
            textView.setTextSize(15);
        }

        return convertView;
    }
}
