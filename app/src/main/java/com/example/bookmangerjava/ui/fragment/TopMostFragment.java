package com.example.bookmangerjava.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookmangerjava.R;
import com.example.bookmangerjava.api.ApiService;
import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.controller.ApiController;
import com.example.bookmangerjava.databinding.FragmentTopMostBinding;
import com.example.bookmangerjava.model.TopMost;
import com.example.bookmangerjava.ui.adapter.TopMostAdapter;

import java.util.List;


public class TopMostFragment extends Fragment {
    private FragmentTopMostBinding binding;
    private ApiController apiController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTopMostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        apiController = new ApiController();
        apiController.getTopMost(new ApiCallback<List<TopMost>>() {
            @Override
            public void onSuccess(List<TopMost> data) {
                TopMostAdapter topMostAdapter = new TopMostAdapter(data);
                binding.recyclerview.setAdapter(topMostAdapter);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
}