package com.example.bloodbankapp.activities

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SharedMemory
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bloodbankapp.R
import com.example.bloodbankapp.databinding.ActivityProfileBinding
import com.example.bloodbankapp.di.ApiModule
import com.example.bloodbankapp.model.RegisterOrUpdate
import com.example.bloodbankapp.utility.ContractBloodBankApp
import com.example.bloodbankapp.utility.SharedPreferencesManager
import com.google.android.gms.common.api.Api
import com.google.android.material.dialog.MaterialDialogs
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialSetup()
        binding.profileToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.profileToolbar.setNavigationOnClickListener { finish() }

        binding.update.setOnClickListener {
            binding.showUserDetails.visibility = View.VISIBLE
            binding.editUserDetails.visibility = View.GONE
            updateUser(it)
        }
        binding.btnEdit.setOnClickListener {
            binding.showUserDetails.visibility = View.GONE
            binding.editUserDetails.visibility = View.VISIBLE
//            updateUser()
        }
        initialSetup()
    }

    private fun updateUser(view: View) {

        val email = SharedPreferencesManager.getEmailId().toString()
        val bloodGroup = binding.bloodGroupTxt.text.toString().trim()
        val gender = binding.genderTxt.text.toString().trim()
        val dob = binding.dobTxt.text.toString().trim()
        val address = binding.addressTxt.text.toString().trim()

        if (bloodGroup.isNullOrEmpty()){
            onSnack(
                view,
                ContextCompat.getColor(this, R.color.pink),
                ContractBloodBankApp.passwordLenght
            )
            return
        }
        val builder = ApiModule.apiService.updateUser(email, bloodGroup, gender, dob, address)

        builder.enqueue(object : Callback<RegisterOrUpdate>{
            override fun onResponse(
                call: Call<RegisterOrUpdate>,
                response: Response<RegisterOrUpdate>
            ) {
                val message = response.body()?.message
                Toast.makeText(this@ProfileActivity, "$message", Toast.LENGTH_SHORT).show()
                Log.v("Update", message.toString())
            }

            override fun onFailure(call: Call<RegisterOrUpdate>, t: Throwable) {
                Toast.makeText(this@ProfileActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                Log.v("Update", t.localizedMessage.toString())
            }
        })
    }

    private fun initialSetup() {
        val image = SharedPreferencesManager.getImage()?.let { convert(it) }
        if (image != null){
            binding.imageProfile.setImageBitmap(image)
        }
        binding.username.text = SharedPreferencesManager.getUserName()
        binding.bloodGroup.text = SharedPreferencesManager.getBloodGroup()
        binding.gender.text = SharedPreferencesManager.getGender()
        binding.mail.text = SharedPreferencesManager.getEmailId()
        binding.phone.text = SharedPreferencesManager.getPhone()
        binding.mail.text = SharedPreferencesManager.getEmailId()
//      binding.age.text = SharedPreferencesManager.getAge()
    }

    private fun convert(base64Str: String): Bitmap? {
        val decodedBytes = Base64.decode(
            base64Str.substring(base64Str.indexOf(",") + 1),
            Base64.DEFAULT
        )
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    private fun onSnack(view: View, texColor: Int, errorMessage: String) {
        val snackBar =
            Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).setAction("Action", null)
        val snackBarView = snackBar.view
        //       snackBarView.setBackgroundColor(getColor(R.color.light_gray))
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.white))

        val textView =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(texColor)
        textView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        textView.textSize = 14f

        snackBar.show()
    }

}