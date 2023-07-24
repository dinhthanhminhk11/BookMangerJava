package com.example.bookmangerjava.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.controller.ApiController;
import com.example.bookmangerjava.databinding.FragmentHomeBinding;
import com.example.bookmangerjava.model.UserClient;
import com.example.bookmangerjava.model.response.BodySizeHome;
import com.example.bookmangerjava.ui.activity.BookActivity;
import com.example.bookmangerjava.ui.activity.KindOfBookActivity;
import com.example.bookmangerjava.ui.activity.LoanSlipActivity;
import com.example.bookmangerjava.ui.activity.MemberActivity;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ApiController apiController;

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
        apiController.getSizeHome(new ApiCallback<BodySizeHome>() {
            @Override
            public void onSuccess(BodySizeHome data) {
                binding.tsMember.setText(data.getData().getUser() + "");
                binding.tsBook.setText(data.getData().getBook() + "");
                binding.tsKindOfBook.setText(data.getData().getCategory() + "");
                binding.tsLoanSlip.setText(data.getData().getLoanSlip() + "");
            }

            @Override
            public void onError(Throwable t) {

            }
        });
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
        apiController = new ApiController();
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
                startActivity(new Intent(getActivity(), MemberActivity.class));
            }
        });

        binding.idTop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.idloanslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoanSlipActivity.class));
            }
        });
    }


}