package com.example.bookmangerjava.controller;

import com.example.bookmangerjava.api.ApiService;
import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.constant.RetrofitRequest;
import com.example.bookmangerjava.model.Book;
import com.example.bookmangerjava.model.KindOfBook;
import com.example.bookmangerjava.model.LoanSlip;
import com.example.bookmangerjava.model.TopMost;
import com.example.bookmangerjava.model.User;
import com.example.bookmangerjava.model.request.RequestDeleteAccount;
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

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

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

    public void changePass(User user, final ApiCallback<BodyLoginResponse> callback) {
        apiService.changePass(user).enqueue(new Callback<BodyLoginResponse>() {
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

    public void updateBook(String id, Book book, final ApiCallback<BodyResponseAddBook> callback) {
        apiService.updateBook(id, book).enqueue(new Callback<BodyResponseAddBook>() {
            @Override
            public void onResponse(Call<BodyResponseAddBook> call, Response<BodyResponseAddBook> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyResponseAddBook> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getListBook(final ApiCallback<BodyBookResponse> callback) {
        apiService.getListBook().enqueue(new Callback<BodyBookResponse>() {
            @Override
            public void onResponse(Call<BodyBookResponse> call, Response<BodyBookResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyBookResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void addBook(Book book, final ApiCallback<BodyResponseAddBook> callback) {
        apiService.addBook(book).enqueue(new Callback<BodyResponseAddBook>() {
            @Override
            public void onResponse(Call<BodyResponseAddBook> call, Response<BodyResponseAddBook> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyResponseAddBook> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void deleteBook(String id, final ApiCallback<BodyResponseAddBook> callback) {
        apiService.deleteBook(id).enqueue(new Callback<BodyResponseAddBook>() {
            @Override
            public void onResponse(Call<BodyResponseAddBook> call, Response<BodyResponseAddBook> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyResponseAddBook> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void register(String fullName, String username, String password, File imageFile, final ApiCallback<BodyLoginResponse> callback) {
        RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);

        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageRequestBody);

        RequestBody fullNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), fullName);
        RequestBody usernameRequestBody = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody passwordRequestBody = RequestBody.create(MediaType.parse("text/plain"), password);
        apiService.register(fullNameRequestBody, usernameRequestBody, passwordRequestBody, imagePart).enqueue(new Callback<BodyLoginResponse>() {
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

    public void updateUser(String fullName, String username, File imageFile, final ApiCallback<BodyLoginResponse> callback) {
        RequestBody fullNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), fullName);
        RequestBody usernameRequestBody = RequestBody.create(MediaType.parse("text/plain"), username);

        MultipartBody.Part imagePart = null;
        if (imageFile != null) {
            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageRequestBody);
        }

        Call<BodyLoginResponse> call;
        if (imagePart != null) {
            call = apiService.updateUser(fullNameRequestBody, usernameRequestBody, imagePart);
        } else {
            call = apiService.updateUserWithoutImage(fullNameRequestBody, usernameRequestBody);
        }

        call.enqueue(new Callback<BodyLoginResponse>() {
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

    public void getListUser(final ApiCallback<BodyUserListResponse> callback) {
        apiService.getListUser().enqueue(new Callback<BodyUserListResponse>() {
            @Override
            public void onResponse(Call<BodyUserListResponse> call, Response<BodyUserListResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyUserListResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void deleteAccount(String username, final ApiCallback<BodyLoginResponse> callback) {
        apiService.deleteAccount(username).enqueue(new Callback<BodyLoginResponse>() {
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

    public void addLoanSlip(LoanSlip loanSlip, final ApiCallback<BodyLoanSlipResponse> callback) {
        apiService.addLoanSlip(loanSlip).enqueue(new Callback<BodyLoanSlipResponse>() {
            @Override
            public void onResponse(Call<BodyLoanSlipResponse> call, Response<BodyLoanSlipResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyLoanSlipResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void deleteLoanSlip(String id, final ApiCallback<BodyLoanSlipResponse> callback) {
        apiService.deleteLoanSlip(id).enqueue(new Callback<BodyLoanSlipResponse>() {
            @Override
            public void onResponse(Call<BodyLoanSlipResponse> call, Response<BodyLoanSlipResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyLoanSlipResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getListLoanSlip(final ApiCallback<BodyListLoanSlip> callback) {
        apiService.getListLoanSlip().enqueue(new Callback<BodyListLoanSlip>() {
            @Override
            public void onResponse(Call<BodyListLoanSlip> call, Response<BodyListLoanSlip> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyListLoanSlip> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void updateLoanSlip(String id, final ApiCallback<BodyLoanSlipResponse> callback) {
        apiService.updateLoanSlip(id).enqueue(new Callback<BodyLoanSlipResponse>() {
            @Override
            public void onResponse(Call<BodyLoanSlipResponse> call, Response<BodyLoanSlipResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyLoanSlipResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getSizeHome(final ApiCallback<BodySizeHome> callback) {
        apiService.getSizeHome().enqueue(new Callback<BodySizeHome>() {
            @Override
            public void onResponse(Call<BodySizeHome> call, Response<BodySizeHome> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodySizeHome> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getTopMost(final ApiCallback<List<TopMost>> callback) {
        apiService.getTopMost().enqueue(new Callback<List<TopMost>>() {
            @Override
            public void onResponse(Call<List<TopMost>> call, Response<List<TopMost>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<List<TopMost>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getRevenue(long startDate, long endDate, final ApiCallback<BodyResponseRevenue> callback) {
        apiService.getPopularBooks(startDate, endDate).enqueue(new Callback<BodyResponseRevenue>() {
            @Override
            public void onResponse(Call<BodyResponseRevenue> call, Response<BodyResponseRevenue> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<BodyResponseRevenue> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
