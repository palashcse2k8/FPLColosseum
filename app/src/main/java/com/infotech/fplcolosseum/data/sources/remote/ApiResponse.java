package com.infotech.fplcolosseum.data.sources.remote;

public class ApiResponse<T> {
    private final T data;
    private final String errorMessage;

    public ApiResponse(T data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return data != null && errorMessage == null;
    }
}