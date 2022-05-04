package com.example.bloodbankapp.model

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("registration.php")
    fun registerUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("image") image: String
    ): Call<RegisterOrUpdate>

    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<Login>

    @FormUrlEncoded
    @POST("update_user.php")
    fun updateUser(
        @Field("email") email: String,
        @Field("blood_group") blood: String,
        @Field("gender") gender: String,
        @Field("date_of_birth") date_of_birth: String,
        @Field("address") address: String,
    ): Call<RegisterOrUpdate>
}