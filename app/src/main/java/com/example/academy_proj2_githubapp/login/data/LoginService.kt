package com.example.academy_proj2_githubapp.login.data

import com.example.academy_proj2_githubapp.login.data.models.AccessToken
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): AccessToken

    /*
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun refreshToken(
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") type: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ): AccessToken

     */

}