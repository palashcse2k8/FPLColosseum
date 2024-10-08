package com.infotech.fplcolosseum.data.sources.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIHandler {
    APIHandler() {
    }

    public static <T> LiveData<T> callAPI(Call<ResponseBody> callingAPI, Class<T> apiResponseType) {

        final MutableLiveData<T> apiData = new MutableLiveData<>();
//        Logger.d("api call => ", "Call request" + (++apiCallCount));

        try {
            callingAPI.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

//                    Logger.d("api response => ", "Response received" + (++apiResponseCount));
                    if (response.isSuccessful()) {

                        try (ResponseBody responseBody = response.body()) {
                            if (responseBody != null) {
                                T convertedResponse = convertResponse(responseBody, apiResponseType);
                                apiData.setValue(convertedResponse);
                            } else {
                                apiData.setValue(null);
                            }
                        } catch (JsonSyntaxException | IOException e) {
//                            Logger.d("response", "exception occurred");
                            apiData.setValue(null);
                            e.printStackTrace();
                        }

                    } else {
                        apiData.setValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
//                    UIUtils.toast("API Calling fail", WARNING);
                    apiData.setValue(null);
                }
            });
        } catch (Exception e) {
            apiData.setValue(null);
        }
        return apiData;
    }

//    public static <T> T convertResponseWithType(ResponseBody responseBody, Type type) throws IOException {
//
//        Gson gson = new Gson();
//        String json = responseBody.string();
////        Logger.d("apiResponse=>> " + json);
//        try {
//            return gson.fromJson(json, type);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    //special case for login api
    public static <T> LiveData<ApiResponse<T>> makeApiCall(Call<ResponseBody> callingAPI, Class<T> apiResponseType) {

        MutableLiveData<ApiResponse<T>> resultLiveData = new MutableLiveData<>();
        // Enqueue the call and handle the response
        callingAPI.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();

                    if (responseBody != null) {

                        try {
                            T convertedResponse = convertResponse(responseBody, apiResponseType);
                            resultLiveData.postValue(ApiResponse.success(convertedResponse));
                        } catch (IOException e) {
                            resultLiveData.postValue(ApiResponse.error("Failed to parse response", null));
                            e.printStackTrace();
                        }
                    } else {
                        // Handle case where there is no response body
                        if (apiResponseType == String.class) {
                            // Returning a custom string if the type is String and response body is null
                            resultLiveData.postValue(ApiResponse.success((T) "Success"));
                        } else {
                            resultLiveData.postValue(ApiResponse.success(null));
                        }
                    }
                } else {

                    // Handle unsuccessful response
                    ResponseBody errorBody = response.errorBody();
                    String errorMessage = "API call failed with status: " + response.code() + " " + response.message();

                    if (errorBody != null) {
                        try {
                            // Extract the error message from the error body
                            String errorResponse = errorBody.string();
                            JSONObject jsonObject = new JSONObject(errorResponse);
                            errorMessage = findMessageInJson(jsonObject);

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    resultLiveData.postValue(ApiResponse.error(errorMessage, null));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // API call failed
                resultLiveData.postValue(ApiResponse.error("API call failed: " + t.getMessage(), null));

                String errorMessage;

                // Detect timeout
                if (t instanceof SocketTimeoutException) {
                    errorMessage = "Request timed out. Please try again.";
                }
                // Detect no internet connection or network issues
                else if (t instanceof UnknownHostException || t instanceof IOException) {
                    errorMessage = "No internet connection. Please check your network and try again.";
                }
                // Other types of exceptions
                else {
                    errorMessage = "API call failed: " + t.getMessage();
                }

                // Post the error message to LiveData
                resultLiveData.postValue(ApiResponse.error(errorMessage, null));
            }
        });

        return resultLiveData;
    }

    public static <T> LiveData<ApiResponse<List<T>>> makeApiCallForList(Call<ResponseBody> callingAPI, Class<T> typeOfT) {
        MutableLiveData<ApiResponse<List<T>>> resultLiveData = new MutableLiveData<>();

        callingAPI.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        List<T> convertedResponse = convertListResponse(response.body(), typeOfT);
                        resultLiveData.postValue(ApiResponse.success(convertedResponse));
                    } catch (IOException e) {
                        resultLiveData.postValue(ApiResponse.error("API call failed: " + e.getMessage(), null));
                        e.printStackTrace();
                    }
                } else {
                    resultLiveData.postValue(ApiResponse.error("API call failed: response unsuccessful", null));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                resultLiveData.postValue(ApiResponse.error("API call failed: " + t.getMessage(), null));
                t.printStackTrace();
            }
        });

        return resultLiveData;
    }

    private static String findMessageInJson(JSONObject jsonObject) {
        // Base case: Check if the current object contains the "message" key
        if (jsonObject.has("message")) {
            return jsonObject.optString("message", "");
        }

        // Recursively search through all keys in the current object
        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String key = it.next();
            try {
                Object value = jsonObject.get(key);

                // If the value is another JSONObject, search within that object
                if (value instanceof JSONObject) {
                    String result = findMessageInJson((JSONObject) value);
                    if (!result.isEmpty()) {
                        return result;
                    }
                }

                // If the value is a JSONArray, search within each JSONObject in the array
                if (value instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) value;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Object arrayElement = jsonArray.get(i);
                        if (arrayElement instanceof JSONObject) {
                            String result = findMessageInJson((JSONObject) arrayElement);
                            if (!result.isEmpty()) {
                                return result;
                            }
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Return an empty string if no "message" key is found
        return "";
    }

//    public static <T> LiveData<ApiResponse<T>> makeApiCall2(Call<ResponseBody> callingAPI, Class<T> apiResponseType) {
//        MutableLiveData<ApiResponse<T>> resultLiveData = new MutableLiveData<>();
//
//        callingAPI.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    try {
//                        new TypeToken<T>() {}.getType();
//                        T convertedResponse = convertResponseWithType(response.body(), T, typeOfT);
//                        resultLiveData.postValue(ApiResponse.success(convertedResponse));
//                    } catch (IOException e) {
//                        resultLiveData.postValue(ApiResponse.error("API call failed: " + e.getMessage(), null));
//                        e.printStackTrace();
//                    }
//                } else {
//                    resultLiveData.postValue(ApiResponse.error("API call failed: response unsuccessful", null));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                resultLiveData.postValue(ApiResponse.error("API call failed: " + t.getMessage(), null));
//                t.printStackTrace();
//            }
//        });
//
//        return resultLiveData;
//    }

//    public static <T> T convertResponseWithType(ResponseBody responseBody, Type type) throws IOException {
//        Gson gson = new Gson();
//        String json = responseBody.string();
//        try {
//            return gson.fromJson(json, type);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IOException("JSON parsing error", e);
//        }
//    }

//    public static <T> List<T> convertListResponse(ResponseBody responseBody, Class<T> type) throws IOException {
//        Gson gson = new Gson();
//        Type conversionType = new TypeToken<List<T>>() {}.getType();
//        String json = responseBody.string();
//        try {
//            return gson.fromJson(json, conversionType);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IOException("JSON parsing error", e);
//        }
//    }

    public static <T> List<T> convertListResponse(ResponseBody responseBody, Class<T> type) throws IOException {
        Gson gson = new Gson();
        String json = responseBody.string();
        try {
            Type conversionType = TypeToken.getParameterized(List.class, type).getType();
            return gson.fromJson(json, conversionType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("JSON parsing error", e);
        }
    }

    public static <T> T convertResponse(ResponseBody responseBody, Class<T> classofT) throws IOException {

        Gson gson = new Gson();
        String json = responseBody.string();
//        Logger.d("apiResponse=>> " + json);
        try {
            return gson.fromJson(json, classofT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
