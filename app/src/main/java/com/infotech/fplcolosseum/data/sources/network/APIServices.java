package com.infotech.fplcolosseum.data.sources.network;

import com.infotech.fplcolosseum.utilities.Constants;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
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


    //login api
    @POST(Constants.LOGIN_URL)
//    @Headers({
//            "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:103.0) Gecko/20100101 Firefox/103.0",
//            "Accept-Language: en-GB,en;q=0.5"
//    })
    @FormUrlEncoded
    Call<ResponseBody> userLogin(
            @Field("login") String login,
            @Field("password") String password,
            @Field("app") String app,
            @Field("redirect_uri") String redirectUri
    );

    @GET("https://fantasy.premierleague.com/api/me")
    @Headers({
            "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:103.0) Gecko/20100101 Firefox/103.0",
            "Accept-Language: en-GB,en;q=0.5"
    })
    Call<ResponseBody> getManagerProfileData();

    @GET(Constants.LOGOUT_URL) // https://users.premierleague.com/accounts/logout/?app=plfpl-web&redirect_uri=https://fantasy.premierleague.com/
    Call<ResponseBody> userLogout(@QueryMap Map<String, String> queryParams);

    @GET("https://fantasy.premierleague.com/api/entry/{entry_id}/event/{event_id}/picks/")  // https://fantasy.premierleague.com/api/entry/116074/event/14/picks/
    Call<ResponseBody> gameWeekPicks(@Path("entry_id") long entry_id, @Path("event_id") long gameWeekNumber);

    @POST("https://fantasy.premierleague.com/api/entry/{entry_id}") // https://fantasy.premierleague.com/api/entry/116074/
    Call<ResponseBody> gameWeekEntries(@Path("entry_id") long entry_id);

    @GET("https://fantasy.premierleague.com/api/event/{event_id}/live/") // https://fantasy.premierleague.com/api/event/14/live/
    Call<ResponseBody> gameWeekLive(@Path("event_id") long gameWeekNumber);

    @GET("https://fantasy.premierleague.com/api/fixtures/?event=14") // https://fantasy.premierleague.com/api/fixtures/?event=14
    Call<ResponseBody> gameWeekFixtures(@Query("event") long gameWeekNumber);

    @GET("https://fantasy.premierleague.com/api/my-team/{entry_id}/") //  https://fantasy.premierleague.com/api/my-team/116074/
    Call<ResponseBody> gameWeekMyTeam(@Path("entry_id") long entry_id);

    @GET("https://fantasy.premierleague.com/api/bootstrap-static/") // https://fantasy.premierleague.com/api/bootstrap-static/
    Call<ResponseBody> getGameWeeKStaticData();

}
