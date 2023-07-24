package com.example.bookmangerjava.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.bookmangerjava.R;
import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.controller.ApiController;
import com.example.bookmangerjava.databinding.ActivityAddLoanSlipBinding;
import com.example.bookmangerjava.model.Book;
import com.example.bookmangerjava.model.LoanSlip;
import com.example.bookmangerjava.model.response.BodyBookResponse;
import com.example.bookmangerjava.model.response.BodyLoanSlipResponse;
import com.example.bookmangerjava.ui.adapter.CustomBookAdapter;
import com.example.bookmangerjava.ui.adapter.CustomKindOfBookAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddLoanSlipActivity extends AppCompatActivity {

    private ApiController apiController;
    private ActivityAddLoanSlipBinding binding;
    private CustomBookAdapter customBookAdapter;
    private String idBook = "";
    private int priceBook = 0;
    private long timeLong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddLoanSlipBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiController = new ApiController();
        initToolbar();
        initData();

        binding.btDangKi.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.nameUser.getText().toString())) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(binding.phoneUser.getText().toString())) {
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            } else if (!isValidVietnamesePhoneNumber(binding.phoneUser.getText().toString())) {
                Toast.makeText(this, "Please enter a valid Vietnamese phone number", Toast.LENGTH_SHORT).show();
            } else if (timeLong == 0) {
                Toast.makeText(this, "Please chose date", Toast.LENGTH_SHORT).show();
            } else {
                apiController.addLoanSlip(new LoanSlip(binding.nameUser.getText().toString(), binding.phoneUser.getText().toString(), idBook, priceBook, timeLong), new ApiCallback<BodyLoanSlipResponse>() {
                    @Override
                    public void onSuccess(BodyLoanSlipResponse data) {
                        Toast.makeText(AddLoanSlipActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                        if (data.getMessage().isStatus()) {
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(AddLoanSlipActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.spBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Book book = customBookAdapter.getItem(position);
                if (book != null) {
                    idBook = book.get_id();

                    float totalMoney = (book.getPrice()) * (Float.parseFloat(String.valueOf(book.getDiscount())) / 100);
                    float totalMoney1 = (book.getPrice()) - totalMoney;
                    double totalMoney2 = (double) totalMoney1;
                    double roundOff = (double) Math.round(totalMoney2 * 100) / 100;
                    priceBook = (int) roundOff;

                    binding.price.setText(priceBook + "$");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.date.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int d = calendar.get(Calendar.DAY_OF_MONTH);
            int m = calendar.get(Calendar.MONTH);
            int y = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddLoanSlipActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar selectedDateCalendar = Calendar.getInstance();
                    selectedDateCalendar.set(year, month, dayOfMonth);
                    long timeInMillis = selectedDateCalendar.getTimeInMillis();
                    timeLong = timeInMillis;
                    final String NgayGD = year + "-" + (month + 1) + "-" + dayOfMonth;
                    binding.date.setText(NgayGD);
                }
            }, y, m, d);
            datePickerDialog.show();
        });

    }

    private void initData() {
        apiController.getListBook(new ApiCallback<BodyBookResponse>() {
            @Override
            public void onSuccess(BodyBookResponse data) {
                customBookAdapter = new CustomBookAdapter(AddLoanSlipActivity.this, data.getData());
                binding.spBook.setAdapter(customBookAdapter);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("");
        }
    }

    private boolean isValidVietnamesePhoneNumber(String phoneNumber) {
        String phonePattern = "^(\\+?84|0)(3[2-9]|5[25689]|7[06-9]|8[1-689]|9\\d)(\\d{7}|\\d{8})$";

        return Patterns.PHONE.matcher(phoneNumber).matches() && phoneNumber.matches(phonePattern);
    }
}