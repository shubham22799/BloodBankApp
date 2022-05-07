package com.example.bloodbankapp.model

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("registration.php")
    fun registerUser(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part image: MultipartBody.Part,
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