package com.example.bloodbankapp.utility

import android.util.Log
import com.google.android.gms.common.api.internal.ApiKey
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object UrlResult {

    fun retrieveUrlResult(latitude: Float, longitude: Float): String {
        var urlData: String = ""
        var httpURLConnection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        val googlePlacesUrl =
            StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        googlePlacesUrl.append("location=$latitude,$longitude")
        googlePlacesUrl.append("&radius=" + 1000)
        googlePlacesUrl.append("&keyword=bloodbank")
        googlePlacesUrl.append("&sensor=true")
        googlePlacesUrl.append("&key=" + "AIzaSyDm53Jq-mU0oqqNFPrfn5u9msMTCppohDk")
        Log.v("urlBuilder", googlePlacesUrl.toString())

        try {
            val getUrl = URL(googlePlacesUrl.toString())
            httpURLConnection = getUrl.openConnection() as HttpURLConnection?
            httpURLConnection?.connect()

            inputStream = httpURLConnection?.inputStream

            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val sb = StringBuffer()
            var line = ""

            while (bufferedReader.readLine() != null){
                line = bufferedReader.readLine()
                sb.append(line)
            }
            urlData = sb.toString()
            bufferedReader.close()

        }catch (e: Exception){
            Log.v("retrieveUrlResult", e.toString())
        }finally {
            inputStream?.close()
            httpURLConnection?.disconnect()
        }
        return urlData
    }


}