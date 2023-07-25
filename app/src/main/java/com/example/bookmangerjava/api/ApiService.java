package com.example.bookmangerjava.api;

import com.example.bookmangerjava.model.Book;
import com.example.bookmangerjava.model.KindOfBook;
import com.example.bookmangerjava.model.LoanSlip;
import com.example.bookmangerjava.model.TopMost;
import com.example.bookmangerjava.model.User;
import com.example.bookmangerjava.model.response.BodyBookResponse;
import com.example.bookmangerjava.model.response.BodyKindOfBookResponse;
import com.example.bookmangerjava.model.response.BodyListLoanSlip;
import com.example.bookmangerjava.model.response.BodyLoanSlipResponse;
import com.example.bookmangerjava.model.response.BodyLoginResponse;
import com.example.bookmangerjava.model.response.BodyResponseAddBook;
import com.example.bookmangerjava.model.response.BodyResponseAddKindOfBook;
import com.example.bookmangerjava.model.response.BodyResponseRevenue;
import com.example.bookmangerjava.model.response.BodySizeHome;
import com.example.bookmangerjava.model.response.BodyUserListResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("category")
    Call<BodyKindOfBookResponse> getListKindOfBook();

    @POST("category")
    Call<BodyResponseAddKindOfBook> addKindOfBook(@Body KindOfBook kindOfBook);

    @DELETE("category/{id}")
    Call<BodyResponseAddKindOfBook> deleteKindOfBook(@Path("id") String id);

    @PATCH("category/{id}")
    Call<BodyResponseAddKindOfBook> updateKindOfBook(@Path("id") String id, @Body KindOfBook kindOfBook);

    @GET("book")
    Call<BodyBookResponse> getListBook();

    @POST("book")
    Call<BodyResponseAddBook> addBook(@Body Book book);

    @DELETE("book/{id}")
    Call<BodyResponseAddBook> deleteBook(@Path("id") String id);

    @PATCH("book/{id}")
    Call<BodyResponseAddBook> updateBook(@Path("id") String id, @Body Book book);

    @POST("auth/login")
    Call<BodyLoginResponse> login(@Body User user);

    @Multipart
    @POST("auth/register")
    Call<BodyLoginResponse> register(@Part("fullName") RequestBody fullName, @Part("username") RequestBody username, @Part("password") RequestBody password, @Part MultipartBody.Part image);

    @GET("auth")
    Call<BodyUserListResponse> getListUser();

    @DELETE("auth/{id}")
    Call<BodyLoginResponse> deleteAccount(@Path("id") String id);

    @Multipart
    @PATCH("auth/updateUser")
    Call<BodyLoginResponse> updateUser(@Part("fullName") RequestBody fullName, @Part("username") RequestBody username, @Part MultipartBody.Part image);

    @Multipart
    @PATCH("auth/updateUser")
    Call<BodyLoginResponse> updateUserWithoutImage(@Part("fullName") RequestBody fullName, @Part("username") RequestBody username);

    @PATCH("auth/changePassword")
    Call<BodyLoginResponse> changePass(@Body User user);

    @POST("loanslip")
    Call<BodyLoanSlipResponse> addLoanSlip(@Body LoanSlip loanSlip);

    @DELETE("loanslip/{id}")
    Call<BodyLoanSlipResponse> deleteLoanSlip(@Path("id") String id);

    @GET("loanslip")
    Call<BodyListLoanSlip> getListLoanSlip();

    @PATCH("loanslip/updateStatus/{id}")
    Call<BodyLoanSlipResponse> updateLoanSlip(@Path("id") String id);

    @GET("sizeHome")
    Call<BodySizeHome> getSizeHome();

    @GET("getTopMost")
    Call<List<TopMost>> getTopMost();

    @GET("getCountReven")
    Call<BodyResponseRevenue> getPopularBooks(@Query("startDate") long startDate, @Query("endDate") long endDate);
}
