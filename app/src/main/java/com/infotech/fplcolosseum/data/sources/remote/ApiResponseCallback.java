package com.infotech.fplcolosseum.data.sources.remote;

import androidx.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public interface ApiResponseCallback<T> {
    void onSuccess(T response);

    void onError(String errorMessage);
}