package com.tmsnith.emotionsense.Utils;

import com.tmsnith.emotionsense.RequestModels.DocumentModel;
import com.tmsnith.emotionsense.Resoponse.TextResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiInterface {

//    @GET("/profile/{id}")
//    Call<UserModel> getProfile(@Path("id") String id);
//
//    @POST("/post")
//    @FormUrlEncoded
//
//    Call<UploadPostActivity.Response1> sendPostData(@Field("title") String title, @Field("description") String description,
//                                                    @Field("credit") int credit
//            , @Field("tags") String tag, @Field("picPostUrl") String picPostUrl, @Field("isPublic") String isPublic, @Field("authorId") String authorid,
//                                                    @Field("circlesId") String circlesId);
//
//    @POST("/user")
//    @FormUrlEncoded
//    Call<in.coders.fsociety.taskbuddy.Fragments.LoginFragment.UserSentResponse> sendUserData(
//            @Field("name") String name,
//            @Field("id") String id,
//            @Field("picUrl") String picUrl,
//            @Field("email") String email);
//
//    @GET("/profile/post/{id}")
//    Call<ProfilePostModel> getProfilePosts(@Path("id") String id);
//
//
//    @GET("/search")
//    Call<SearchResponse> getSearchResult(@Query("userId") String userID, @Query("keyword") String keyword);
//    @GET("/main/post")
//    Call<MainPostModel> getMainPosts(@Query("id") String id);
//
//    @FormUrlEncoded
//    @POST("/register")
//    Call<RegisterResponse> register(@Field("userId") String userId, @Field("postId") int postId);
//
//
//    @FormUrlEncoded
//    @POST("/friend/new/{userId}")
//    Call<RegisterResponse> addAsFriend(@Path("userId") String userId, @Field("friendId") String friendId);
//
//
//  @GET("/profile/circles/{id}")
//    Call<ProfileCircleResponse> getCircleList(@Path("id") String id);

    @POST("/text/analytics/v2.0/sentiment")
    Call<TextResponse> sendText(@Header("Ocp-Apim-Subscription-Key") String header,
            @Header("Content-Type") String type
            ,@Body DocumentModel request
            );
//    @POST("emotion/v1.0/recognize")
//    Call<Image>
 }
