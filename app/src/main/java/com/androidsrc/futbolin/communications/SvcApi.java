package com.androidsrc.futbolin.communications;

/**
 * Created by alberto on 18/06/15.
 */


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.androidsrc.futbolin.communications.http.auth.get.Live;
import com.androidsrc.futbolin.communications.http.auth.get.ResponsePostFollow;
import com.androidsrc.futbolin.communications.http.auth.get.SellingPlayer;
import com.androidsrc.futbolin.communications.http.auth.get.TeamWithFormationId;
import com.androidsrc.futbolin.communications.http.auth.get.getMatch;
import com.androidsrc.futbolin.communications.http.auth.get.getNotification;
import com.androidsrc.futbolin.communications.http.auth.get.getNotifications;
import com.androidsrc.futbolin.communications.http.auth.get.getPlayer;
import com.androidsrc.futbolin.communications.http.auth.get.getTeamWithFormationId;
import com.androidsrc.futbolin.communications.http.auth.get.getTeams;
import com.androidsrc.futbolin.communications.http.auth.get.getShoppingItems;
import com.androidsrc.futbolin.communications.http.auth.get.getStrategies;
import com.androidsrc.futbolin.communications.http.auth.get.getTeam;
import com.androidsrc.futbolin.communications.http.auth.get.getTournament;
import com.androidsrc.futbolin.communications.http.auth.get.getTrain;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.communications.http.auth.get.market.getMarket;
import com.androidsrc.futbolin.communications.http.auth.get.test.Usertest;
import com.androidsrc.futbolin.communications.http.auth.patch.PatchFormation;
import com.androidsrc.futbolin.communications.http.auth.patch.PatchMe;
import com.androidsrc.futbolin.communications.http.auth.patch.PatchPlayerValue;
import com.androidsrc.futbolin.communications.http.auth.patch.PatchStrategy;
import com.androidsrc.futbolin.communications.http.auth.post.PostMarketFollow;
import com.androidsrc.futbolin.communications.http.auth.post.PostMatch;
import com.androidsrc.futbolin.communications.http.auth.post.PostMatchResponse;
import com.androidsrc.futbolin.communications.http.auth.post.PostPlayerOffer;
import com.androidsrc.futbolin.communications.http.auth.post.PostPlayerTransferible;
import com.androidsrc.futbolin.communications.http.auth.post.PostShoppingBuy;
import com.androidsrc.futbolin.communications.http.auth.post.PostShoppingBuyResponse;
import com.androidsrc.futbolin.communications.http.auth.post.PostTeam;
import com.androidsrc.futbolin.communications.http.auth.post.PostTrain;
import com.androidsrc.futbolin.database.models.MarketTransactions;
import com.androidsrc.futbolin.database.models.MeTransactions;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.database.models.SystemSubNotifications;
import com.androidsrc.futbolin.database.models.Team;
import com.androidsrc.futbolin.database.models.User;
import com.androidsrc.futbolin.communications.http.auth.AndroiduserLogin;
import com.androidsrc.futbolin.communications.http.auth.AndroiduserLoginResponse;
import com.androidsrc.futbolin.communications.http.auth.AndroiduserRegistrationResponse;

import org.json.JSONObject;

import java.util.List;


public interface SvcApi {
    String REGISTER_ANDROID_USER = "api/register";
    String LOGIN_ANDROID_USER = "api/login";
    String LOGOUT_ANDROID_USER = "api/logout";
    String ME = "api/me";
    String TEAM = "api/team";
    String TEAMS = "api/teams";
    String STRATEGIES = "api/strategies";
    String TEAM_STRATEGY = "api/team/strategy";
    String TEAM_FORMATION = "api/team/formation";
    String TEAM_TRAIN = "api/team/train";
    String SHOPPING_ITEMS = "api/shopping/items";
    String SHOPPING_BUY = "api/shopping/buy";
    String MATCH = "api/match";
    String TOURNAMENT = "api/tournament";
    String TOURNAMENT_BY_CATEGORY_ID = "api/tournament/{category_id}";
    String TEAM_BY_ID = "api/team/{team_id}";
    String MATCH_LIVE = "api/match/live";
    String PLAYER_VALUE = "api/player/{player_id}/value";
    String PLAYER_TRANSFERIBLE = "api/player/{player_id}/transferable";
    String PLAYER_FREE = "api/player/{player_id}/free";
    String MARKET = "api/market";
    String PLAYER = "api/player/{player_id}";
    String PLAYER_OFFER = "api/player/offer";
    String NOTIFICATION = "api/me/notification/{notification_id}";
    String NOTIFICATIONS = "api/me/notifications";
    String ME_TRANSACTIONS = "api/me/transactions";
    String MARKET_TRANSACTIONS = "api/market/transactions";
    String MARKET_FOLLOWING = "api/market/following";
    String MARKET_FOLLOW = "api/market/follow";
    String MARKET_UNFOLLOW = "api/market/unfollow";
    String MARKET_OFFERS = "api/market/offers";

    @POST(REGISTER_ANDROID_USER)
    public Call<AndroiduserRegistrationResponse> registerAndroiduser(@Body User au);
    @POST(LOGIN_ANDROID_USER)
    public Call<AndroiduserLoginResponse> loginAndroiduser(@Body AndroiduserLogin au);
    @FormUrlEncoded
    @POST(LOGOUT_ANDROID_USER)
    public Call<AndroiduserLoginResponse> postLogout(@Field("device_id") String device_id);
    @GET(ME)
    public Call<getUser> getMe(@Query("device_id") String device_id);
    @GET(ME)
    public Call<Usertest> getMeTestClass(@Query("device_id") String device_id);
    @GET("api/orders")
    public Call<JSONObject> getOrdersVoadoTest(@Query("device_id") String device_id);
    @GET(ME)
    public Call<String> getMeTestString(@Query("device_id") String device_id);
    @POST(TEAM)
    public Call<Team> postTeam(@Body PostTeam team);
    @PATCH(TEAM)
    public Call<Team> patchTeam(@Body PostTeam team);
    @GET(STRATEGIES)
    public Call<getStrategies> getStrategies(@Query("device_id") String device_id);
    @PATCH(TEAM_STRATEGY)
    Call<getTeam> pathStrategy(@Body PatchStrategy strategy);
    @PATCH(TEAM_FORMATION)
    Call<getTeam> pathFormation(@Body PatchFormation formation);
    @PATCH(ME)
    Call<getUser> patchMe(@Body PatchMe patchMe);
    @POST(TEAM_TRAIN)
    Call<getTrain> postTrain(@Body PostTrain train);
    @GET(SHOPPING_ITEMS)
    public Call<getShoppingItems> getShoppingItems(@Query("device_id") String device_id);
    @POST(SHOPPING_BUY)
    public Call<getTrain> postShoppingBuyTrain(@Query("device_id") String device_id, @Query("id") int id);
    @POST(SHOPPING_BUY)
    public Call<PostShoppingBuyResponse> postShoppingBuy(@Body PostShoppingBuy postShoppingBuy);
    @GET(TEAMS)
    Call<getTeams> getTeams(@Query("device_id") String device_id);
    @POST(MATCH)
    Call<PostMatchResponse> postMatch(@Body PostMatch postMatch);
    @GET(MATCH)
    Call<getMatch> getMatch(@Query("device_id") String device_id, @Query("file") String file);
    @GET(TOURNAMENT)
    Call<getTournament> getTournament(@Query("device_id") String device_id);
    @GET(TOURNAMENT_BY_CATEGORY_ID)
    Call<getTournament> getTournamentByCategoryId(@Path("category_id") long id,@Query("device_id") String device_id);
    @GET(TEAM_BY_ID)
    Call<TeamWithFormationId> getTeam(@Path("team_id") long id, @Query("device_id") String device_id);
    @GET(TEAM_BY_ID)
    Call<getTeamWithFormationId> getgetTeam(@Path("team_id") long id, @Query("device_id") String device_id);
    @GET(MATCH_LIVE)
    Call<Live> getMatchLive(@Query("device_id") String device_id);
    @PATCH(PLAYER_VALUE)
    Call<Player> pathPlayerValue(@Path("player_id") long player_id,@Body PatchPlayerValue playerValue);
    @POST(PLAYER_TRANSFERIBLE)
    Call<SellingPlayer> postPlayerTransferible(@Path("player_id") long player_id, @Body PostPlayerTransferible PlayerTransferible);
    @POST(PLAYER_FREE)
    Call<Player> postPlayerFree(@Path("player_id") long player_id, @Body PostPlayerTransferible PlayerTransferible);
    @GET(MARKET)
    Call<getMarket> getMarket(@Query("device_id") String device_id);
    @GET(PLAYER)
    Call<getPlayer> getPlayer(@Path("player_id") long player_id, @Query("device_id") String device_id);
    @POST(PLAYER_OFFER)
    Call<Void> postPlayerOffer(@Body PostPlayerOffer PlayerOffer);
    @GET(NOTIFICATION)
    Call<getNotification> getNotification(@Path("notification_id") long notification_id, @Query("device_id") String device_id);
    @GET(NOTIFICATIONS)
    Call<getNotifications> getNotifications(@Query("device_id") String device_id);
    @GET(ME_TRANSACTIONS)
    Call<MeTransactions> getMeTransactions(@Query("page") int page , @Query("device_id") String device_id);
    @GET(MARKET_TRANSACTIONS)
    Call<MarketTransactions> getMarketTransactions(@Query("page") int page , @Query("device_id") String device_id);
    @GET(MARKET_FOLLOWING)
    Call<getMarket> getMarketFollowing(@Query("device_id") String device_id);
    @POST(MARKET_FOLLOW)
    Call<ResponsePostFollow> postMarketFollow(@Body PostMarketFollow postMarketFollow);
    @POST(MARKET_UNFOLLOW)
    Call<ResponsePostFollow> postMarketUnfollow(@Body PostMarketFollow postMarketFollow);
    @GET(MARKET_OFFERS)
    Call<getMarket> getMarketOffers(@Query("device_id") String device_id);
  /*  @GET(GET_PARAMETERS_BY_PROJECT_PATH)
    public Call<List<parametros>> getParametersByIdProject(@Path("idproject") long id);
    @GET(GET_POINTTYPES_BY_PROJECT_PATH)
    public Call<List<Pointtype>> getPointtypesByProjectId(@Path("idproject") long id);
    @GET(TRY_LOGIN)
    public Call<String> getTryLogin();
    @GET(GET_SURVEYS_BY_PROJECT_PATH)
    public Call<List<Survey>> getSurveysByIdProject(@Path("idproject") long id);
    @GET(GET_SURVEYS_QUESTION_BY_PROJECT_PATH)
    public Call<List<survey_question>> getSurveyQuestionByIdProject(@Path("idproject") long id);

    @GET(GET_POINTS_BY_PROJECT_PATH)
    public Call<List<Point>> getPointsByProjectId(@Path("project_id") long id);

    @POST(ADD_POINT)
    public Call<Point> addPoint(@Body Point rp);
    @POST(ADD_EDITED_POINT)
    public Call<Point> addEditedPoint(@Body Point rp);
    @POST(ADD_COMPLETEDSURVEY)
    public Call<Completed_survey> addCompletedSurvey(@Body Completed_survey rp);
    @POST(ADD_EDITED_COMPLETEDSURVEY)
    public Call<Completed_survey> addEditedCompleted_survey(@Body Completed_survey rp);
    @GET(GET_COMPLETED_SURVEYS_BY_POINT_PATH)
    public Call<List<Completed_survey>> getCompletedSurveysByPointId(@Path("idpoint") long id);

    @Multipart
    @POST(IMAGE_VAR_PATH)//{javaclasstype}/{type}/{id_db_point}/{idparam_or_survey}
    public Call<String> updateImage(@Part("data") MultipartBody.Part data, @Path("javaclasstype") long javaclasstype, @Path("type") long type, @Path("id_db_point") long id_db_point, @Path("idparam_or_survey") long idparam_or_survey, @Path("idcompletedsurvey") long idcompletedsurvey);

    @Multipart
    @POST(FILE_VAR_PATH)///idproject/{javaclasstype}/{type}/{id_db_point}/{idparam_or_survey}
    public Call<String> addFile(@Part("data") MultipartBody.Part data, @Path("idproject") long idproject, @Path("javaclasstype") long javaclasstype, @Path("type") long type, @Path("id_db_point") long id_db_point, @Path("idparam_or_survey") long idparam_or_survey,@Path("idcompletedsurvey") long idcompletedsurvey);
    @Multipart
    @POST(FILE_VAR_PATH)///idproject/{javaclasstype}/{type}/{id_db_point}/{idparam_or_survey}
    public Call<String> uploadFile(@Part MultipartBody.Part data, @Path("idproject") long idproject, @Path("javaclasstype") long javaclasstype, @Path("type") long type, @Path("id_db_point") long id_db_point, @Path("idparam_or_survey") long idparam_or_survey,@Path("idcompletedsurvey") long idcompletedsurvey);
    @Multipart
    @POST(UPLOADFILE_VAR_PATH)///idproject/{javaclasstype}/{type}/{id_db_point}/{idparam_or_survey}
    public Call<String> uploadFileTest(/*@Part("file")  file f, @Part MultipartBody.Part data);
    @Multipart
    @POST(AUDIO_VAR_PATH)
    public Call<String> updateAudio(@Part("data") MultipartBody.Part data, @Path("javaclasstype") long javaclasstype, @Path("type") long type, @Path("id_db_point") long id_db_point, @Path("idparam_or_survey") long idparam_or_survey,@Path("idcompletedsurvey") long idcompletedsurvey);

    @Multipart
    @POST(VIDEO_VAR_PATH)
    public Call<String> updateVideo(@Part("data") MultipartBody.Part data, @Path("javaclasstype") long javaclasstype, @Path("type") long type, @Path("id_db_point") long id_db_point, @Path("idparam_or_survey") long idparam_or_survey, @Path("idcompletedsurvey") long idcompletedsurvey);

    @GET(TEST_MOBILE_PATH)
    public Call<Survey> getSurveytest();
    public Completed_survey addSurvey(@Path("pointid") long id,@Body Completed_survey cs);
    @Streaming
    @GET(GET_MAP_FILE)
    public Call<ResponseBody> getMap(@Path("filename") long id);
    @Streaming
    @GET(GET_MAPAWS_FILE)
    public Call<ResponseBody> getMapAws(@Path("filename") String id);
    @GET(GET_MAP_DATABASE)
    public Call<Map> getMapDatabase(@Path("idproject") long idproject);

    @POST(POST_SIGNUP_REQUEST)
    public Call<Boolean> postSignuprequest(@Body Signuprequest rp);

    @GET(CHECK_PROJECT_VERSION_ANDROID)
    public Call<Project> checkProjectVersion(@Path("idproject") long idproject, @Path("version") long version);
    */
}
