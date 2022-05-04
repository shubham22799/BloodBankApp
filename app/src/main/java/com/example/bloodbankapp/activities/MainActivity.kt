package com.example.bloodbankapp.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodbankapp.R
import com.example.bloodbankapp.databinding.ActivityMainBinding
import com.example.bloodbankapp.utility.SharedPreferencesManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenuPresenter
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBarMain.profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.appBarMain.mainActivityToolbar.navigationIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_hamburger_icon)
        binding.appBarMain.mainActivityToolbar.setNavigationOnClickListener { binding.sideDrawer.open() }

        val username = SharedPreferencesManager.getUserName()
        val profileImage: Bitmap? = convert(SharedPreferencesManager.getImage().toString())
        binding.appBarMain.mainActivityToolbar.title = "Hello,\n$username"
        binding.appBarMain.profile.setImageBitmap(profileImage)

        val bottomNavView: BottomNavigationView = binding.appBarMain.bottomNavView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        bottomNavView.setupWithNavController(navController)
        val sideNavView = binding.sideNavView

        val header = binding.sideNavView.getHeaderView(0)
        val bloodReq = header.findViewById<TextView>(R.id.req_blood)
        val findDonor = header.findViewById<TextView>(R.id.find_donor)
//        val addCamp = header.findViewById<TextView>(R.id.add_camp)
        val aboutUs = header.findViewById<TextView>(R.id.about_us)
        val feedback = header.findViewById<TextView>(R.id.feedback)
        val reportUs = header.findViewById<TextView>(R.id.report_us)
        val faq = header.findViewById<TextView>(R.id.faq)
        val helpDesk = header.findViewById<TextView>(R.id.help_desk)
        val logOut = header.findViewById<TextView>(R.id.log_out)

        bloodReq.setOnClickListener {
            startActivity(Intent(this, RequestBloodActivity::class.java))
            binding.sideDrawer.close()
        }
        findDonor.setOnClickListener {
            startActivity(Intent(this, FindDonorActivity::class.java))
            binding.sideDrawer.close()
        }
//        addCamp.setOnClickListener {
//            startActivity(Intent(this, AddCampActivity::class.java))
//            binding.sideDrawer.close()
//        }
        aboutUs.setOnClickListener {
            startActivity(Intent(this, AboutUsActivity::class.java))
            binding.sideDrawer.close()
        }
        feedback.setOnClickListener {
            startActivity(Intent(this, FeedbackActivity::class.java))
            binding.sideDrawer.close()
        }
        reportUs.setOnClickListener {
            startActivity(Intent(this, ReportUsActivity::class.java))
            binding.sideDrawer.close()
        }
        faq.setOnClickListener {
            startActivity(Intent(this, FaqActivity::class.java))
            binding.sideDrawer.close()
        }
        helpDesk.setOnClickListener {
            startActivity(Intent(this, HelpdeskActivity::class.java))
            binding.sideDrawer.close()
        }
        logOut.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            binding.sideDrawer.close()
            SharedPreferencesManager.putUserStatus(false)
            SharedPreferencesManager.clearSharedPreferences()
            finish()
        }
        changeDrawerLayoutHeight(sideNavView)
        disableMenuScroll(sideNavView)
    }

    fun hideActivityToolBar() {
        binding.appBarMain.toolbarLl.visibility = View.GONE
        binding.sideNavView.visibility = View.GONE
    }

    fun unHideActivityToolBar() {
        binding.appBarMain.toolbarLl.visibility = View.VISIBLE
        binding.sideNavView.visibility = View.VISIBLE
    }


    private fun disableMenuScroll(navView: NavigationView) {
        val navMenu = navView.getChildAt(0) as NavigationMenuView
        navMenu.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
    }

    private fun changeDrawerLayoutHeight(navView: NavigationView) {

        /*With reflection get the navView's presenter*/
        val field = navView.javaClass.getDeclaredField("presenter")
        field.isAccessible = true

        val presenter = field.get(navView) as NavigationMenuPresenter

        /*From presenter, get the header layout field*/
        val layoutField = presenter.javaClass.getDeclaredField("headerLayout")
        layoutField.isAccessible = true

        val headerLayout = layoutField.get(presenter) as LinearLayout

        /*Set layout params on the HeaderLayout to match parent*/
        val params = headerLayout.layoutParams
        params.height = 1300
        headerLayout.layoutParams = params
    }

    private fun convert(base64Str: String): Bitmap? {
        val decodedBytes = Base64.decode(
            base64Str.substring(base64Str.indexOf(",") + 1),
            Base64.DEFAULT
        )
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val decor = window.decorView;
//            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//        }