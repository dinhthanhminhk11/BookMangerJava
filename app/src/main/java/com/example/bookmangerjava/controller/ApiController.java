package com.example.bookmangerjava.controller;

import com.example.bookmangerjava.api.ApiService;
import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.constant.RetrofitRequest;
import com.example.bookmangerjava.model.KindOfBook;
import com.example.bookmangerjava.model.User;
import com.example.bookmangerjava.model.response.BodyKindOfBookResponse;
import com.example.bookmangerjava.model.response.BodyLoginResponse;
import com.example.bookmangerjava.model.response.BodyResponseAddKindOfBook;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiController {
    private ApiService apiService;

    public ApiController() {
        apiService = RetrofitRequest.getRetrofitInstance().create(ApiService.class);
    }

    public void login(User user, final ApiCallback<BodyLoginResponse> callback) {
        apiService.login(user).enqueue(new Callback<BodyLoginResponse>() {
            @Override
            public void onResponse(Call<BodyLoginResponse> call, Response<BodyLoginResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyLoginResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getAllListKindOfBook(final ApiCallback<BodyKindOfBookResponse> callback) {
        apiService.getListKindOfBook().enqueue(new Callback<BodyKindOfBookResponse>() {
            @Override
            public void onResponse(Call<BodyKindOfBookResponse> call, Response<BodyKindOfBookResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyKindOfBookResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void addKindOfBook(KindOfBook kindOfBook, final ApiCallback<BodyResponseAddKindOfBook> callback) {
        apiService.addKindOfBook(kindOfBook).enqueue(new Callback<BodyResponseAddKindOfBook>() {
            @Override
            public void onResponse(Call<BodyResponseAddKindOfBook> call, Response<BodyResponseAddKindOfBook> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyResponseAddKindOfBook> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void deleteKindOfBook(String id, final ApiCallback<BodyResponseAddKindOfBook> callback) {
        apiService.deleteKindOfBook(id).enqueue(new Callback<BodyResponseAddKindOfBook>() {
            @Override
            public void onResponse(Call<BodyResponseAddKindOfBook> call, Response<BodyResponseAddKindOfBook> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyResponseAddKindOfBook> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void updateKindOfBook(String id, KindOfBook kindOfBook, final ApiCallback<BodyResponseAddKindOfBook> callback) {
        apiService.updateKindOfBook(id, kindOfBook).enqueue(new Callback<BodyResponseAddKindOfBook>() {
            @Override
            public void onResponse(Call<BodyResponseAddKindOfBook> call, Response<BodyResponseAddKindOfBook> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyResponseAddKindOfBook> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
