package com.example.bookmangerjava.ui.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.PointF;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.db.williamchart.view.LineChartView;
import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.controller.ApiController;
import com.example.bookmangerjava.databinding.FragmentBarChartBinding;
import com.example.bookmangerjava.model.response.BodyResponseRevenue;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;


public class BarChartFragment extends Fragment {

    private FragmentBarChartBinding binding;
    private DatePickerDialog mDatePickerDialog;
    private String tvStartDate, tvEndDate;
    private ApiController apiController;
    private long startDate = 0;
    private long endDate = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBarChartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        apiController = new ApiController();
        datePicker();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void datePicker() {
        binding.btnEndDate.setOnClickListener(view -> {
            final Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int yeat = cal.get(Calendar.YEAR);
            mDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                    final int date = i2;
                    String monthandYear = i + "-" + (i1 + 1);
                    binding.dayEnd.setText(String.valueOf(date));
                    binding.monthAndYearEnd.setText(monthandYear);
                    tvEndDate = i + "-" + i1 + "-" + i2;

                    Calendar selectedDateCalendar = Calendar.getInstance();
                    selectedDateCalendar.set(i, i1, i2);
                    endDate = selectedDateCalendar.getTimeInMillis();
                    checkDate(tvStartDate, tvEndDate);
                    //   Toast.makeText(RevenueActivity.this, tvEndDate, Toast.LENGTH_SHORT).show();
                }
            }, yeat, month, day);
            mDatePickerDialog.show();


        });
        binding.btnStartDate.setOnClickListener(view -> {
            final Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int yeat = cal.get(Calendar.YEAR);
            mDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    final int date = i2;
                    String monthandYear = i + "-" + (i1 + 1);
                    binding.dayStart.setText(String.valueOf(date));
                    binding.monthAndYearStart.setText(monthandYear);
                    tvStartDate = i + "-" + i1 + "-" + i2;

                    Calendar selectedDateCalendar = Calendar.getInstance();
                    selectedDateCalendar.set(i, i1, i2);
                    startDate = selectedDateCalendar.getTimeInMillis();

                    checkDate(tvStartDate, tvEndDate);
                }
            }, yeat, month, day);

            mDatePickerDialog.show();

        });
    }

    private void checkDate(String dateStart, String dateEnd) {
        SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (dfm.parse(dateStart).before(dfm.parse(dateEnd))) {

                Log.e("Data", startDate + " startDate");
                Log.e("Data", endDate + " endDate");
                apiController.getRevenue(startDate, endDate, new ApiCallback<BodyResponseRevenue>() {
                    @Override
                    public void onSuccess(BodyResponseRevenue data) {
                        if (data.getMessage().isStatus()) {
                            binding.tvRevenue.setText("Revenue: " + data.getData().getTotalMoney() + "$");
                            binding.tvDaTra.setText("Đã Trả: " + data.getData().getAmountPaid());
                            binding.tvChuaTra.setText("Chưa Trả: " + data.getData().getUnpaidAmount());
                        } else {
                            Toast.makeText(getActivity(), data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }
                });
                binding.dayEnd.setTextColor(Color.rgb(152, 4, 45));
                binding.monthAndYearEnd.setTextColor(Color.rgb(152, 4, 45));
                binding.dayStart.setTextColor(Color.rgb(152, 4, 45));
                binding.monthAndYearStart.setTextColor(Color.rgb(152, 4, 45));
            } else if (dfm.parse(dateEnd).before(dfm.parse(dateStart))) {
                Toast.makeText(getActivity(), "End Date Must Not Be Less Than Start Date", Toast.LENGTH_SHORT).show();
                binding.dayEnd.setTextColor(Color.RED);
                binding.monthAndYearEnd.setTextColor(Color.RED);
            } else if (dfm.parse(dateStart).equals(dfm.parse(dateEnd))) {
                Toast.makeText(getActivity(), "The End Date Can't Be The Same As The Start Date", Toast.LENGTH_SHORT).show();
                binding.dayStart.setTextColor(Color.RED);
                binding.monthAndYearStart.setTextColor(Color.RED);
                binding.dayEnd.setTextColor(Color.RED);
                binding.monthAndYearEnd.setTextColor(Color.RED);
            }
        } catch (Exception e) {

        }
    }
}