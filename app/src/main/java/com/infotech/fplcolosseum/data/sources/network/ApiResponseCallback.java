package com.infotech.fplcolosseum.data.sources.network;

public interface ApiResponseCallback<T> {
    void onSuccess(T response);

    void onError(String errorMessage);
}