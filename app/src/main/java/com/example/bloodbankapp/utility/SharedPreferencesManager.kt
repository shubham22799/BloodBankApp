package com.example.bloodbankapp.utility


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SharedPreferencesManager {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private const val sharedPreferenceName: String = "mySharedPreference"
    private const val imageUrl: String = "image"
    private const val userName: String = "name"
    private const val emailId: String = "email"
    private const val address: String = "address"
    private const val phone: String = "phone"
    private const val location: String = "location"
    private const val gender: String = "gender"
    private const val age: String = "age"
    private const val bloodGroup: String = "bloodGroup"
    private const val dob: String = "dob"
    private const val state: String = "state"
    private const val keyUserStatus: String = "status"
    private const val latitude: String = "latitude"
    private const val longitude: String = "longitude"
    private const val city: String = "city"
    private const val country: String = "country"
    private const val postalCode: String = "postalCode"
    private const val knownName: String = "knownName"
    private const val addressLine: String = "addressLine"


    const val defImage: String = "Bitmap"
    private const val defUserName: String = "User Name"
    private const val defAddress: String = "Address"
    private const val defBloodGroup: String = "BloodGroup"
    private const val defEmailId: String = "Email Id"
    private const val defPassword: String = "Password"
    private const val defPhone: String = "Phone"
    private const val defLocation: String = "def_location"
    private const val defGender: String = "Gender"
    private const val defAge: String = "Age"
    private const val defDOB: String = "Dob"
    private const val defState: String = "State"
    private const val defLatitude: Float = 0.0F
    private const val defLongitude: Float = 0.0F

    fun createPreference(context: Context) {
        sharedPreferences = context.getSharedPreferences(sharedPreferenceName, MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun clearSharedPreferences() {
        editor.clear().apply()
    }

    fun getImage(): String? {
        return sharedPreferences.getString(imageUrl, defImage)
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(userName, defUserName)
    }

    fun getEmailId(): String? {
        return sharedPreferences.getString(emailId, defEmailId)
    }

    fun getAddress(): String? {
        return sharedPreferences.getString(address, defAddress)
    }

    fun getBloodGroup(): String? {
        return sharedPreferences.getString(bloodGroup, defBloodGroup)
    }

    fun getDOB(): String? {
        return sharedPreferences.getString(dob, defDOB)
    }

    fun getGender(): String? {
        return sharedPreferences.getString(gender, defGender)
    }

    fun getAge(): String? {
        return sharedPreferences.getString(age, defAge)
    }

    fun getPhone(): String? {
        return sharedPreferences.getString(phone, defPhone)
    }

    //Put
    fun putImage(string: String?) {
        editor.putString(imageUrl, string).apply()
    }

    fun putUsername(string: String?) {
        editor.putString(userName, string).apply()
    }

    fun putEmailId(string: String?) {
        editor.putString(emailId, string).apply()
    }

    fun putPhone(string: String?) {
        editor.putString(phone, string).apply()
    }

    fun putBloodGroup(string: String?) {
        editor.putString(bloodGroup, string).apply()
    }

    fun putDOB(string: String?) {
        editor.putString(dob, string).apply()
    }

    fun putGender(string: String?) {
        editor.putString(gender, string).apply()
    }

    fun putAddress(string: String?) {
        editor.putString(address, string).apply()
    }

    fun putAge(string: String?) {
        editor.putString(age, string).apply()
    }

    fun getUserStatus(): Boolean? {
        return sharedPreferences.getBoolean(keyUserStatus, false)
    }

    fun putUserStatus(boolean: Boolean) {
        editor.putBoolean(keyUserStatus, boolean).commit()
    }


    //Extras
    fun setUserCity(string: String?) {
        editor.putString(city, string).apply()
    }

    fun getUserCity(): String? {
        return sharedPreferences.getString(city, "")
    }

    fun setUserState(string: String?) {
        editor.putString(state, string).apply()
    }

    fun getUserState(): String? {
        return sharedPreferences.getString(state, "")
    }

    fun setUserCountry(string: String?) {
        editor.putString(country, string).apply()
    }

    fun getUserCountry(): String? {
        return sharedPreferences.getString(country, "")
    }

    fun setUserPostalCode(string: String?) {
        editor.putString(postalCode, string).apply()
    }

    fun getUserPostalCode(): String? {
        return sharedPreferences.getString(postalCode, "")
    }

    fun setUserKnownName(string: String?) {
        editor.putString(knownName, string).apply()
    }

    fun getUserKnownName(): String? {
        return sharedPreferences.getString(knownName, "")
    }

    fun setAddressLine(string: String?) {
        editor.putString(addressLine, string).apply()
    }

    fun getAddressLine(): String? {
        return sharedPreferences.getString(addressLine, "")
    }

    fun putLocationLatLong(lat: String, long: String) {
        editor.putString(latitude, lat).apply()
        editor.putString(longitude, long).apply()
    }

    fun getLocationLatLong(): Array<String> {
        val latitude = sharedPreferences.getString(latitude, defLatitude.toString())
        val longitude = sharedPreferences.getString(longitude, defLongitude.toString())
        return arrayOf(latitude.toString(), longitude.toString())
    }
}