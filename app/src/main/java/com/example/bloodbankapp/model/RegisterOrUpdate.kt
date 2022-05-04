package com.example.bloodbankapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterOrUpdate(
    @SerializedName("status_code")
    val status_code: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    ): Serializable
//{"status_code":200,"status":1,"message":"Data Successfully Inserted"}

