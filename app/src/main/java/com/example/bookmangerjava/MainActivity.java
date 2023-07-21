package com.example.bookmangerjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bookmangerjava.databinding.ActivityMainBinding;
import com.example.bookmangerjava.ui.activity.AddLoanSlipActivity;
import com.example.bookmangerjava.ui.activity.LoanSlipActivity;
import com.example.bookmangerjava.ui.fragment.BarChartFragment;
import com.example.bookmangerjava.ui.fragment.HomeFragment;
import com.example.bookmangerjava.ui.fragment.ProfileFragment;
import com.example.bookmangerjava.ui.fragment.TopMostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Date;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;
    private HashMap<Integer, Fragment> fragmentMap = new HashMap<>();
    private int currentFragmentId = 0;

    private int backPressedCount = 0;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId != currentFragmentId) {
                    showFragment(getFragmentByItemId(itemId));
                    currentFragmentId = itemId;
                }
                return true;
            }
        });

        binding.imageViewAddIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddLoanSlipActivity.class));
            }
        });

        fragmentManager = getSupportFragmentManager();

        fragmentMap.put(R.id.navigation_home, new HomeFragment());
        fragmentMap.put(R.id.navigation_top_most, new TopMostFragment());
        fragmentMap.put(R.id.navigation_chart, new BarChartFragment());
        fragmentMap.put(R.id.navigation_me, new ProfileFragment());

        showFragment(fragmentMap.get(R.id.navigation_home));
        currentFragmentId = R.id.navigation_home;

    }

    private Fragment getFragmentByItemId(int itemId) {
        return fragmentMap.get(itemId);
    }

    private void showFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (backPressedCount == 1) {
            finishAffinity();
        } else {
            backPressedCount++;
            Toast.makeText(this, "Nhấn Back một lần nữa để thoát ứng dụng", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressedCount = 0;
                }
            }, 2000);
        }
    }
}