package com.example.bookmangerjava.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookmangerjava.databinding.FragmentHomeBinding;
import com.example.bookmangerjava.model.UserClient;
import com.example.bookmangerjava.ui.activity.BookActivity;
import com.example.bookmangerjava.ui.activity.KindOfBookActivity;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData() {
        UserClient userClient = UserClient.getInstance();
        if (userClient.getRole() == 1) {
            binding.idMember.setVisibility(View.VISIBLE);
        } else {
            binding.idMember.setVisibility(View.GONE);
        }
    }

    private void initView() {
        binding.idKindofbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), KindOfBookActivity.class));
            }
        });

        binding.idBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BookActivity.class));
            }
        });

        binding.idMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.idTop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}