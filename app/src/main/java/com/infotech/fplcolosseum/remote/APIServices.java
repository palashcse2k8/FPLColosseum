package com.infotech.fplcolosseum.remote;

import com.infotech.fplcolosseum.utilities.Constants;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface APIServices {

    @GET("api/LeagueFunction")
    @Headers({
            "Content-Type: application/json",
            "Functionkey: " + Constants.FUNCTION_KEY  // Use the constant here
    })
    Call<ResponseBody> getLeagueData(@QueryMap Map<String, String> queryParams);

    @GET("api/LeagueFunction")
    @Headers({
            "Content-Type: application/json",
            "Functionkey: " + Constants.FUNCTION_KEY  // Use the constant here
    })
    Call<ResponseBody> getManagerData(@QueryMap Map<String, String> queryParams);

    @GET("api/PlayerStatsFunction")
    @Headers({
            "Content-Type: application/json",
            "Functionkey: " + Constants.FUNCTION_KEY  // Use the constant here
    })
    Call<ResponseBody> getPlayerData(@QueryMap Map<String, String> queryParams);
}
