package com.example.bookmangerjava.ui.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.bookmangerjava.databinding.FragmentBarChartBinding;

import java.text.SimpleDateFormat;


public class BarChartFragment extends Fragment {

    private FragmentBarChartBinding binding;
    private DatePickerDialog mDatePickerDialog;
    private String tvStartDate, tvEndDate;

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
                String ngay1 = binding.monthAndYearStart.getText().toString() + "-" + binding.dayStart.getText().toString();
                String ngay2 = binding.monthAndYearEnd.getText().toString() + "-" + binding.dayEnd.getText().toString();
//                float t = mDaoBook.tienDoanhthuTheoNgay(ngay1.trim(), ngay2.trim());
//                if (t == 0.0) {
//                    tvRevenue.setText("Revenue:  No Hope");
//                    tvDaTra.setText("Paid: " + mDaoBook.soluongPhieuDaTra(ngay1.trim(), ngay2.trim()));
//                    tvChuaTra.setText("Unpaid: " + mDaoBook.soluongPhieuChuaTra(ngay1.trim(), ngay2.trim()));
//                } else {
//                    tvRevenue.setText("Revenue: " + t + " $(no discount)");
//                    tvDaTra.setText("Paid: " + mDaoBook.soluongPhieuDaTra(ngay1.trim(), ngay2.trim()));
//                    tvChuaTra.setText("Unpaid: " + mDaoBook.soluongPhieuChuaTra(ngay1.trim(), ngay2.trim()));
//                }
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