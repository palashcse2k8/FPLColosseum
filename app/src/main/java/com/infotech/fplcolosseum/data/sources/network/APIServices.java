package com.infotech.fplcolosseum.data.sources.network;

import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamUpdateModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekTransferUpdateModel;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

//team manager ids : 10359552, 116074

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

    @GET("https://fantasy.premierleague.com/api/entry/{entry_id}") // https://fantasy.premierleague.com/api/entry/116074/
    Call<ResponseBody> gameWeekEntriesInformation(@Path("entry_id") long entry_id);

    @GET("https://fantasy.premierleague.com/api/event/{event_id}/live/") // https://fantasy.premierleague.com/api/event/14/live/
    Call<ResponseBody> gameWeekLive(@Path("event_id") long gameWeekNumber);
//
//    @GET("https://fantasy.premierleague.com/api/fixtures/?event={event}") // https://fantasy.premierleague.com/api/fixtures/?event=14
//    Call<ResponseBody> gameWeekFixtureData(@Query("event") long gameWeekNumber);

    @GET("https://fantasy.premierleague.com/api/fixtures/")
    Call<ResponseBody> gameWeekFixtureData(@Query("event") long gameWeekNumber);

    @GET("https://fantasy.premierleague.com/api/fixtures/")
    Call<ResponseBody> allGameWeekFixtureData(@Query("future") long gameWeekNumber);

    @GET("https://fantasy.premierleague.com/api/my-team/{entry_id}/") //  https://fantasy.premierleague.com/api/my-team/116074/
    Call<ResponseBody> gameWeekMyTeam(@Path("entry_id") long entry_id);

    @POST("https://fantasy.premierleague.com/api/my-team/{entry_id}/") //  https://fantasy.premierleague.com/api/my-team/116074/
    Call<ResponseBody> updateMyTeam(@Path("entry_id") long entry_id , @Body GameWeekMyTeamUpdateModel request);

    @POST("https://fantasy.premierleague.com/api/transfers/") //  https://fantasy.premierleague.com/api/transfers/
    Call<ResponseBody> transferMyTeam(@Body GameWeekTransferUpdateModel request);

    @GET("https://fantasy.premierleague.com/api/bootstrap-static/") // https://fantasy.premierleague.com/api/bootstrap-static/
    Call<ResponseBody> getGameWeeKStaticData();

    @GET("https://fantasy.premierleague.com/api/team/set-piece-notes/") // "https://fantasy.premierleague.com/api/team/set-piece-notes/"
    Call<ResponseBody> getSetPieceNotes();

    //update user data
    @POST("https://fantasy.premierleague.com/api/entry-update/") // "https://fantasy.premierleague.com/api/team/set-piece-notes/"
    Call<ResponseBody> updateUserData();

    //create classic league
    @GET("https://fantasy.premierleague.com/api/entry/league-classic/") // "https://fantasy.premierleague.com/api/team/set-piece-notes/"
    Call<ResponseBody> createClassicLeague();
    @GET("https://fantasy.premierleague.com/api/dream-team/") // "https://fantasy.premierleague.com/api/team/set-piece-notes/"
    Call<ResponseBody> getDreamTeam();

    @GET("https://fantasy.premierleague.com/api/dream-team/{gameWeek}") // "https://fantasy.premierleague.com/api/team/set-piece-notes/"
    Call<ResponseBody> getWeekDreamTeam(@Path("gameWeek") long gameWeek);

    @GET("https://fantasy.premierleague.com/api/element-summary/{player_id}/")  // https://fantasy.premierleague.com/api/element-summary/351/
    Call<ResponseBody> getPlayerSummary(@Path("player_id") long player_id);

    @GET("https://fantasy.premierleague.com/api/event-status/")      //https://fantasy.premierleague.com/api/event-status/
    Call<ResponseBody> getCurrentGameWeekStatus();

    @GET("https://fantasy.premierleague.com/api/stats/best-classic-private-leagues/")     //https://fantasy.premierleague.com/api/stats/best-classic-private-leagues/
    Call<ResponseBody> getBestClassicPrivateLeagues();

    @GET("https://fantasy.premierleague.com/api/stats/most-valuable-teams/")     //https://fantasy.premierleague.com/api/stats/most-valuable-teams/
    Call<ResponseBody> getMostValuableTeams();

    @GET("https://fantasy.premierleague.com/api/leagues-classic/{leagueId}/standings/")     //https://fantasy.premierleague.com/api/leagues-classic/333974/standings/?phase=1&page_standings=3&page_new_entries=2
    Call<ResponseBody> getLeagueInformation(@Path("leagueId") long leagueId,
                                             @Query("phase") int phase,
                                             @Query("page_standings") int pageStandings,
                                             @Query("page_new_entries") int pageNewEntries);

    @GET("https://fantasy.premierleague.com/api/entry/{manager_id}/transfers/")      //https://fantasy.premierleague.com/api/entry/2727830/transfers/
    Call<ResponseBody> getTransferHistory(@Path("manager_id") long manager_id);

}
