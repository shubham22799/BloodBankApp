package com.example.bloodbankapp.model

import com.google.gson.annotations.SerializedName
import java.io.File
import java.io.Serializable

data class Login(
    @SerializedName("status_code")
    val status_code: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("user_data")
    val user_data: UserData
    ): Serializable

data class UserData(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("blood_group")
    val blood_group: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("date_of_birth")
    val date_of_birth: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("password")
    val password: String
):Serializable
//    {"status_code":200,"status":1,"message":"User
//    Found","user_data":{"id":"16","username":"shubham","email":"ss@gmail.com",
//    "mobile":"0","image":"","blood_group":"AB","gender":"female",
//    "date_of_birth":"12-01-1999","address":"kandivali","password":"123456"}}