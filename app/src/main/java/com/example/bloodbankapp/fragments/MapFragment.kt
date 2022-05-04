package com.example.bloodbankapp.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.bloodbankapp.R
import com.example.bloodbankapp.activities.MainActivity
import com.example.bloodbankapp.databinding.FragmentMapBinding
import com.example.bloodbankapp.utility.SingleShotLocationProvider
import com.example.bloodbankapp.utility.UrlResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MapFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private var map: GoogleMap? = null
    private val TAG = this.tag
    private var searchBtnClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as MainActivity?)?.hideActivityToolBar()

        requestLocationPermission()
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        binding.currentLocation.setOnClickListener { requestLocationPermission() }

        binding.searchBtn.setOnClickListener {
            searchBtnClicked = true
            requestLocationPermission()
        }

        return root
    }

    private fun requestLocationPermission() {
        Dexter.withContext(activity as MainActivity)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(
                object : MultiplePermissionsListener {
                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport?) {
                        if (multiplePermissionsReport!!.areAllPermissionsGranted()) {
                            Log.v(TAG, "Working till here")
                            if (searchBtnClicked) {
                                searchBtnClicked = false
                                val textLocation = binding.searchEt.text.toString()
                                if (textLocation.isEmpty() || textLocation.isBlank()) {
                                    return
                                }
                                val connectivity = checkForInternet(activity as MainActivity)
                                if (connectivity) {
                                    val location = getLocation(textLocation)
                                    if (location != null) {
//                                    getNearByBloodBanks(location.latitude.toFloat(), location.longitude.toFloat())
                                        map?.addMarker(
                                            MarkerOptions()
                                                .position(location)
                                                .title("Marker")
                                        )
                                        map?.moveCamera(CameraUpdateFactory.newLatLng(location))
                                        map?.animateCamera(CameraUpdateFactory.zoomTo(12.0f))

                                    } else {
                                        Log.v("location", "is null")
                                        Toast.makeText(
                                            (activity as MainActivity),
                                            "Invalid location!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else{
                                    Log.v("Connectivity", "is null")
                                    Toast.makeText(
                                        (activity as MainActivity),
                                        "No Internet Connection!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                return
                            } else {
                                getLocationFromLocationManager()
                            }
                        }
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                            Log.v("Permissions", "denied permanently")
                            showSettingDialog()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {

                    }

                }
            ).onSameThread().check()
    }

    private fun getLocation(textLocation: String): LatLng? {

        val geocoder = Geocoder(activity as MainActivity)
        var addressList: List<Address> = ArrayList()
        try {
            addressList = geocoder.getFromLocationName(textLocation, 1)
        } catch (e: Exception) {
            Log.v("getLocation", e.toString())
        }
        Log.v("getLocation", addressList.toString())
        var address: Address? = null
        for (i in addressList) {
            Log.v("getLocation", "addressList is not null")
            address = i
        }
        if (address != null) {
            return LatLng(address.latitude, address.longitude)
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
        private fun getLocationFromLocationManager() {

            SingleShotLocationProvider.requestSingleUpdate(
                (activity as MainActivity),
                object : SingleShotLocationProvider.LocationCallback {
                    override fun onNewLocationAvailable(location: SingleShotLocationProvider.GPSCoordinates?) {

                        if (location != null) {
                            SingleShotLocationProvider.getAddress(
                                (activity as MainActivity),
                                location.latitude,
                                location.longitude
                            )

                            getNearByBloodBanks(location.latitude, location.longitude)
                        }
                    }
                }
            )
        }

        @OptIn(DelicateCoroutinesApi::class)
        private fun getNearByBloodBanks(latitude: Float, longitude: Float) {
            Log.v("User Location", latitude.toString())
            Log.v("User Location", longitude.toString())


            var urlResult = ""

            GlobalScope.launch(Dispatchers.IO) {
                urlResult = UrlResult.retrieveUrlResult(latitude, longitude)
            }

            if (urlResult.isNotEmpty()) {
                Log.v("Url Result", urlResult)
            }

            val userLocation = LatLng(latitude.toDouble(), longitude.toDouble())
            map?.addMarker(
                MarkerOptions()
                    .position(userLocation)
                    .title("Marker")
            )
            map?.moveCamera(CameraUpdateFactory.newLatLng(userLocation))
            map?.animateCamera(CameraUpdateFactory.zoomTo(12.0f))

        }

        override fun onMapReady(googleMap: GoogleMap) {
            map = googleMap

        }

        private fun showSettingDialog() {
            val dialogue = AlertDialog.Builder(activity as MainActivity)
            dialogue.setPositiveButton(resources.getString(R.string.setting)) { i, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", (activity as MainActivity).packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            dialogue.show()
        }

        override fun onStart() {
            super.onStart()
            (activity as MainActivity?)?.hideActivityToolBar()
        }

        override fun onStop() {
            super.onStop()
            (activity as MainActivity?)?.unHideActivityToolBar()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }

//getNearByBloodBanks(address.latitude.toFloat(), address.longitude.toFloat())
//val latlong = LatLng(address.latitude, address.longitude)
//
//map?.addMarker(
//MarkerOptions()
//.position(latlong)
//.title("Marker")
//)
//map?.moveCamera(CameraUpdateFactory.newLatLng(latlong))
//map?.animateCamera(CameraUpdateFactory.zoomTo(12.0f))