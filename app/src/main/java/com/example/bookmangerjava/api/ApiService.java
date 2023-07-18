package com.example.bookmangerjava.api;

import com.example.bookmangerjava.model.KindOfBook;
import com.example.bookmangerjava.model.User;
import com.example.bookmangerjava.model.response.BodyKindOfBookResponse;
import com.example.bookmangerjava.model.response.BodyLoginResponse;
import com.example.bookmangerjava.model.response.BodyResponseAddKindOfBook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("category")
    Call<BodyKindOfBookResponse> getListKindOfBook();

    @POST("category")
    Call<BodyResponseAddKindOfBook> addKindOfBook(@Body KindOfBook kindOfBook);

    @DELETE("category/{id}")
    Call<BodyResponseAddKindOfBook> deleteKindOfBook(@Path("id") String id);

    @PATCH("category/{id}")
    Call<BodyResponseAddKindOfBook> updateKindOfBook(@Path("id") String id, @Body KindOfBook kindOfBook);

    @POST("auth/login")
    Call<BodyLoginResponse> login(@Body User user);

}
