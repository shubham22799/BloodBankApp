package com.example.bloodbankapp.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.InputType
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.util.PatternsCompat
import com.example.bloodbankapp.R
import com.example.bloodbankapp.databinding.ActivityLoginRegisterBinding
import com.example.bloodbankapp.di.ApiModule
import com.example.bloodbankapp.model.Login
import com.example.bloodbankapp.model.RegisterOrUpdate
import com.example.bloodbankapp.utility.ContractBloodBankApp
import com.example.bloodbankapp.utility.RealPathUtil
import com.example.bloodbankapp.utility.SharedPreferencesManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class LoginRegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var file: File
    private val TAG = LoginRegisterActivity::class.simpleName
    private var profileImageStatus = false

    private lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        var registerShowPasswordState = false
        binding.showPassword.setOnClickListener {
            if (!registerShowPasswordState) {
                binding.registerPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.registerPassword.setSelection(binding.registerPassword.length())
                registerShowPasswordState = true
            } else {
                binding.registerPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.registerPassword.setSelection(binding.registerPassword.length())
                registerShowPasswordState = false
            }
        }

        var loginShowPasswordState = false
        binding.loginShowPassword.setOnClickListener {
            if (!loginShowPasswordState) {
                binding.loginPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.loginPassword.setSelection(binding.loginPassword.length())
                loginShowPasswordState = true
            } else {
                binding.loginPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.loginPassword.setSelection(binding.loginPassword.length())
                loginShowPasswordState = false
            }
        }


        binding.btnEmail.setOnClickListener {
            binding.llMain.visibility = View.GONE
            binding.llLogin.visibility = View.VISIBLE
            binding.llRegister.visibility = View.GONE
        }
        binding.loginToRegister.setOnClickListener {
            binding.llMain.visibility = View.GONE
            binding.llLogin.visibility = View.GONE
            binding.llRegister.visibility = View.VISIBLE
        }
        binding.register.setOnClickListener {
            binding.registerPb.visibility = View.VISIBLE
            register(it)
        }

        binding.login.setOnClickListener {
            login(it)
        }
        binding.profileImage.setOnClickListener { requestCameraGalleryPermissions() }
    }

    private fun login(view: View) {
        val emailLogin = binding.loginEmail.text.toString().trim()
        val passwordLogin = binding.loginPassword.text.toString().trim()
        if (emailLogin.isEmpty()) {
            onSnack(
                view,
                ContextCompat.getColor(this, R.color.pink),
                ContractBloodBankApp.emptyEmailId
            )
            return
        }
        if (!PatternsCompat.EMAIL_ADDRESS.matcher(emailLogin).matches()) {
            onSnack(
                view,
                ContextCompat.getColor(this, R.color.pink),
                ContractBloodBankApp.incorrectEmailId
            )
            return
        }
        if (binding.loginPassword.text.isEmpty()) {
            onSnack(
                view,
                ContextCompat.getColor(this, R.color.pink),
                ContractBloodBankApp.emptyPassword
            )
            return
        }
        if (binding.loginPassword.text.toString().trim().length < 6) {
            onSnack(
                view,
                ContextCompat.getColor(this, R.color.pink),
                ContractBloodBankApp.passwordLenght
            )
            return
        }
        Log.v(TAG, "$emailLogin/$passwordLogin")
        binding.loginPb.visibility = View.VISIBLE
        val builder = ApiModule.apiService.loginUser(emailLogin, passwordLogin)
        builder.enqueue(object: Callback<Login>{
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                val userData = response.body()?.user_data
                Log.v("Login", userData.toString())
                SharedPreferencesManager.putEmailId(userData?.email )
                SharedPreferencesManager.putImage(userData?.image)
                Log.v("Image", userData?.image.toString())
                SharedPreferencesManager.putUsername(userData?.username)
                SharedPreferencesManager.putPhone(userData?.mobile)
                SharedPreferencesManager.putAddress(userData?.address)
                SharedPreferencesManager.putBloodGroup(userData?.blood_group)
                SharedPreferencesManager.putDOB(userData?.date_of_birth)
                SharedPreferencesManager.putGender(userData?.gender)
//                SharedPreferencesManager.putAge(userData?.)
                SharedPreferencesManager.putUserStatus(true)
                Toast.makeText(this@LoginRegisterActivity, "Login Successfully!", Toast.LENGTH_SHORT).show()
                binding.loginPb.visibility = View.GONE
                startActivity(Intent(this@LoginRegisterActivity, MainActivity::class.java))
                Log.v("When Login:", SharedPreferencesManager.getUserStatus().toString())
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                Log.v("Login", t.message.toString())
                binding.loginPb.visibility = View.GONE
            }

        })
//        loginUserWithFireBase(emailLogin, passwordLogin, view)
    }

    override fun finish() {
        super.finish()
        binding.loginPb.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (binding.llRegister.visibility == View.VISIBLE) {
            binding.llMain.visibility = View.GONE
            binding.llLogin.visibility = View.VISIBLE
            binding.llRegister.visibility = View.GONE
            return
        }
        if (binding.llLogin.visibility == View.VISIBLE) {
            super.onBackPressed()
        }
    }

    private fun register(view: View) {

        val userNameRegister = binding.registerName.text.toString().trim()
        val emailRegister = binding.registerEmail.text.toString().trim()
        val passwordRegister = binding.registerPassword.text.toString().trim()
        val phoneRegister =  binding.registerPhone.text.toString().trim()

        if (!profileImageStatus) {
            onSnack(
                view,
                ContextCompat.getColor(this, R.color.pink),
                ContractBloodBankApp.emptyImageView
            )
            Log.v(TAG, "ImageProfile is null")
            return
        }
        if (userNameRegister.isEmpty()) {
            onSnack(
                view,
                ContextCompat.getColor(this, R.color.pink),
                ContractBloodBankApp.emptyUserName
            )
            return
        }
        if (emailRegister.isEmpty()) {
            onSnack(
                view,
                ContextCompat.getColor(this, R.color.pink),
                ContractBloodBankApp.emptyEmailId
            )
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailRegister).matches()) {
            onSnack(
                view,
                ContextCompat.getColor(this, R.color.pink),
                ContractBloodBankApp.incorrectEmailId
            )
            return
        }
        if (binding.registerPhone.text.isEmpty()) {
            onSnack(
                view,
                ContextCompat.getColor(this, R.color.pink),
                ContractBloodBankApp.emptyConfirmPassword
            )
            return
        }
        if (passwordRegister.isEmpty()) {
            onSnack(
                view,
                ContextCompat.getColor(this, R.color.pink),
                ContractBloodBankApp.emptyPassword
            )
            return
        }

        val file = File(path)
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, requestFile)

        var name =  RequestBody.create("multipart/form-data".toMediaTypeOrNull(), userNameRegister)
        var password =  RequestBody.create("multipart/form-data".toMediaTypeOrNull(), passwordRegister)
        var email =  RequestBody.create("multipart/form-data".toMediaTypeOrNull(), emailRegister)
        var phone =  RequestBody.create("multipart/form-data".toMediaTypeOrNull(), phoneRegister)

//        val profileImage =
//        Log.v("Profile Image", SharedPreferencesManager.getImage().toString())
        val builder = ApiModule.apiService.registerUser(name, password,
            email, phone, body)

        builder.enqueue(object :Callback<RegisterOrUpdate>{
            override fun onResponse(call: Call<RegisterOrUpdate>, response: Response<RegisterOrUpdate>) {
                val data = response.body()?.message
                Log.v("Register", data.toString())
                Toast.makeText(this@LoginRegisterActivity, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                binding.llMain.visibility = View.GONE
                binding.llLogin.visibility = View.VISIBLE
                binding.llRegister.visibility = View.GONE

//                binding.registerName.setText("")
//                binding.registerEmail.setText("")
//                binding.registerPassword.setText("")
//                binding.registerPhone.setText("")
            }

            override fun onFailure(call: Call<RegisterOrUpdate>, t: Throwable) {
                Log.v("Register", t.message.toString())
                Toast.makeText(this@LoginRegisterActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

        Log.d(TAG, "saved in preferences")

//        registerUserOnFireBase(emailRegister, passwordRegister)
    }


    private fun requestCameraGalleryPermissions() {
        val tag = "Permission"
        Dexter.withContext(this)
            .withPermissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport?) {
                        if (multiplePermissionsReport!!.areAllPermissionsGranted()) {
                            Log.v(tag, "Permission Granted")
                            showCameraGalleryPopup()
                            return
                        }
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                            Log.v("Permissions", "denied permanently")
                            showSettingDialog()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        list: MutableList<PermissionRequest>?,
                        permissionToken: PermissionToken?
                    ) {
                        permissionToken!!.continuePermissionRequest()
                    }
                })
            .withErrorListener {
                Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show()
            }
            .onSameThread().check()
    }

    private fun showSettingDialog() {
        val dialogue = AlertDialog.Builder(this)
        dialogue.setPositiveButton(resources.getString(R.string.setting)) { i, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        dialogue.show()
    }

    private fun showCameraGalleryPopup() {
        val view = layoutInflater.inflate(R.layout.camera_gallery_popup, null)
        val dialog = Dialog(this)
        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val gallery = view.findViewById<Button>(R.id.gallery)
        gallery!!.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            getActivityResult.launch(galleryIntent)
            dialog.dismiss()
        }
        val camera = view.findViewById<Button>(R.id.camera)
        camera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            file = getFile()
            val fileProvider = FileProvider.getUriForFile(this, "com.example.bloodbankapp", file)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            getActivityResult.launch(cameraIntent)
            dialog.dismiss()
        }
        dialog.setCancelable(true)
    }

    private val getActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                if (uri != null) {
                    path = RealPathUtil.getRealPath(this, uri)

                    val bitmap = BitmapFactory.decodeFile(path)

                    binding.profileImage.setImageBitmap(bitmap)
                    //convert Uri to Bitmap
//                    val galleryBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
//
//                    binding.profileImage.setImageBitmap(galleryBitmap)
//                    val bitToString: String? = convertToBase64(galleryBitmap)
//                    Log.v("BitImage", bitToString.toString())
//                    if (bitToString != null) {
//                        SharedPreferencesManager.putImage(bitToString)
//                    }
                    profileImageStatus = true
                    return@registerForActivityResult
                }
                val takePicture = BitmapFactory.decodeFile(file.absolutePath)
                if (takePicture != null) {
                    binding.profileImage.setImageBitmap(takePicture)
                    val stringImage: String? = convertToBase64(takePicture)
                    SharedPreferencesManager.putImage(stringImage)
                }
                profileImageStatus = true
                return@registerForActivityResult
            }
        }

    override fun onStart() {
        super.onStart()
        profileImageStatus = false
    }

    private fun convertToBase64(bitmap: Bitmap): String {
        val base = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, base)
        val b: ByteArray = base.toByteArray()
        val outPut = Base64.encodeToString(b, Base64.DEFAULT)
        return outPut
    }

    private fun convertToString(bitmap: Bitmap): String? {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }
    private fun getFile(): File {
        var directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("Picture", ".jpge", directoryStorage)
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

    private fun registerUserOnFireBase(emailRegister: String, passwordRegister: String) {
        auth.createUserWithEmailAndPassword(emailRegister, passwordRegister)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                    binding.llMain.visibility = View.GONE
                    binding.llLogin.visibility = View.VISIBLE
                    binding.llRegister.visibility = View.GONE

                }
            }.addOnFailureListener { exception ->
                Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    private fun loginUserWithFireBase(emailLogin: String, passwordLogin: String, view: View) {
        auth.signInWithEmailAndPassword(emailLogin, passwordLogin).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Login Successfully!", Toast.LENGTH_SHORT).show()
                binding.loginPb.visibility = View.GONE
                SharedPreferencesManager.putUserStatus(true)
                startActivity(Intent(this, MainActivity::class.java))
                Log.v("When Login:", SharedPreferencesManager.getUserStatus().toString())
            }
        }.addOnFailureListener { exception ->
            onSnack(
                view,
                ContextCompat.getColor(this, R.color.pink),
                exception.localizedMessage.toString()
            )
            binding.loginPb.visibility = View.GONE

        }
    }
}
