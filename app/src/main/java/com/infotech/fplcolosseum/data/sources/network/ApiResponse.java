package com.infotech.fplcolosseum.data.sources.network;

//public class ApiResponse<T> {
//    private T data;
//    private String errorMessage;
//
//    public ApiResponse(T data, String errorMessage) {
//        this.data = data;
//        this.errorMessage = errorMessage;
//    }
//    public void setErrorMessage(String errorMessage) {
//        this.errorMessage = errorMessage;
//    }
//    public void setData(T data) {
//        this.data =  data;
//    }
//    public T getData() {
//        return data;
//    }
//
//    public String getErrorMessage() {
//        return errorMessage;
//    }
//
//    public boolean isSuccess() {
//        return data != null && errorMessage == null;
//    }
//}

public class ApiResponse<T> {

    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    private final Status status;
    private final T data;
    private final String message;

    private ApiResponse(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(Status.SUCCESS, data, null);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(Status.ERROR, data, message);
    }

    public static <T> ApiResponse<T> loading(T data) {
        return new ApiResponse<>(Status.LOADING, data, null);
    }

    // Getters...

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}