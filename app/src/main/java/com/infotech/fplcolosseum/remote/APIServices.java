package com.infotech.fplcolosseum.remote;

import com.infotech.fplcolosseum.utilities.Constants;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

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


    //login api
    @POST(Constants.LOGIN_URL)
    @Headers({
            "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:103.0) Gecko/20100101 Firefox/103.0",
            "Accept-Language: en-GB,en;q=0.5"
    })
    @FormUrlEncoded
    Call<ResponseBody> userLogin(
            @Field("login") String login,
            @Field("password") String password,
            @Field("app") String app,
            @Field("redirect_uri") String redirectUri
    );
}
