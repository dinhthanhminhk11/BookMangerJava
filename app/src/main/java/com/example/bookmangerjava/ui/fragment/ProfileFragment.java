package com.example.bookmangerjava.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookmangerjava.R;
import com.example.bookmangerjava.constant.AppConstant;
import com.example.bookmangerjava.databinding.FragmentProfileBinding;
import com.example.bookmangerjava.model.User;
import com.example.bookmangerjava.model.UserClient;
import com.example.bookmangerjava.ui.activity.ChangePassActivity;
import com.example.bookmangerjava.ui.activity.LibrarianActivity;
import com.example.bookmangerjava.ui.activity.LoginActivity;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        UserClient userClient = UserClient.getInstance();
        binding.nameUser.setText(userClient.getFullName());
        binding.username.setText(userClient.getUsername());
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.book2).error(R.drawable.book2);
        Glide.with(binding.imageUser.getContext()).load(AppConstant.BASE + "/uploads/" + userClient.getImage()).apply(options).into(binding.imageUser);
    }

    private void initView() {
        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LibrarianActivity.class);
                intent.putExtra(AppConstant.USER, new User(UserClient.getInstance().getFullName(), UserClient.getInstance().getImage(), UserClient.getInstance().getUsername()));
                startActivity(intent);
            }
        });

        binding.contentLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comFirmExit();
            }
        });

        binding.contentChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePassActivity.class));
            }
        });
    }

    private void comFirmExit() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Notification").setMessage("Are you sure you want to escape").setPositiveButton("Ok", null).setNegativeButton("Cancel", null).show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}