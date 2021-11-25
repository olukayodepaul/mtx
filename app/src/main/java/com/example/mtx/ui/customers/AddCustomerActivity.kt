package com.example.mtx.ui.customers

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.mtx.databinding.ActivityAddCuctomerBinding
import com.example.mtx.dto.UserSpinnerEntity
import com.example.mtx.util.NetworkResult
import com.example.mtx.util.PermissionUtility
import com.example.mtx.util.SessionManager
import com.example.mtx.util.ToastDialog
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first


@AndroidEntryPoint
class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCuctomerBinding

    private lateinit var sessionManager: SessionManager

    private val viewModel: AddCustomerViewModel by viewModels()

    private lateinit var languageAdapter: LanguageAdapter

    private lateinit var outletClassAdapter: OutletClassAdapter

    private lateinit var outletTypeAdapter: OutletTypeAdapter

    private var hasGps = false

    lateinit var mLocationManager: LocationManager

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCuctomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toobar)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        sessionManager = SessionManager(this)
        isRefresh()
        onActivityResult()
        viewModel.fetchAllSpinners()
        languageAdapter = LanguageAdapter()
        outletClassAdapter = OutletClassAdapter()
        outletTypeAdapter = OutletTypeAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.mtx.R.menu.map_outlet, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.example.mtx.R.id.map_outlet_id -> {
                isPermissionRequest()
                getCurrentLocation()
            }

        }
        return false
    }


    private fun isRefresh() {
        lifecycleScope.launchWhenResumed {
            viewModel.spinnerResponseState.collect {
                it.let {
                    when(it){
                        is NetworkResult.Empty -> {}

                        is NetworkResult.Error -> {}

                        is NetworkResult.Loading -> {}

                        is NetworkResult.Success -> {

                            val languages: List<UserSpinnerEntity> = it.data!!.data!!.filter { fil->fil.sep ==1 }

                            val outlettype: List<UserSpinnerEntity> = it.data.data!!.filter { fil->fil.sep ==2 }

                            val outletclass: List<UserSpinnerEntity> = it.data.data!!.filter { fil->fil.sep ==3}

                            val outletLanguage = ArrayList<String>()
                            val outletClass = ArrayList<String>()
                            val outletType = ArrayList<String>()


                            if (languageAdapter.size() > 0) {
                                languageAdapter.clear()
                            }

                            if (outletClassAdapter.size() > 0) {
                                outletClassAdapter.clear()
                            }

                            if (outletTypeAdapter.size() > 0) {
                                outletTypeAdapter.clear()
                            }

                            for (i in languages.indices) {
                                outletLanguage.add(languages[i].name!!)
                                languageAdapter.add(languages[i].id!!, languages[i].name!!)
                            }

                            for (i in outletclass.indices) {
                                outletClass.add(outletclass[i].name!!)
                                outletClassAdapter.add(outletclass[i].id!!, outletclass[i].name!!)
                            }

                            for (i in outlettype.indices) {
                                outletType.add(outlettype[i].name!!)
                                outletTypeAdapter.add(outlettype[i].id!!, outlettype[i].name!!)
                            }

                            val arrayAdapterLanguage = ArrayAdapter(
                                applicationContext,
                                R.layout.simple_list_item_1,
                                outletLanguage
                            )
                            binding.tvFieldLangauge.setAdapter(arrayAdapterLanguage)
                            binding.tvFieldLangauge.threshold = 1

                            val arrayAdapterOutletType = ArrayAdapter(
                                applicationContext,
                                R.layout.simple_list_item_1,
                                outletType
                            )
                            binding.tvFieldType.setAdapter(arrayAdapterOutletType)
                            binding.tvFieldType.threshold = 1

                            val arrayAdapterOutletClass = ArrayAdapter(
                                applicationContext,
                                R.layout.simple_list_item_1,
                                outletClass
                            )
                            binding.tvFieldClass.setAdapter(arrayAdapterOutletClass)
                            binding.tvFieldClass.threshold = 1
                        }
                    }
                }
            }
        }
    }

    private fun isPermissionRequest() {

        val usesPermission = PermissionUtility.requestPermission(this)

        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)

        if(usesPermission.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, usesPermission.toTypedArray(), 0)
            return
        }else if(!hasGps){
            isGpsEnableIntent()
            return
        }else if(available == ConnectionResult.API_UNAVAILABLE){
            ToastDialog(applicationContext, "Play Update the google play service");
            return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    println("permissionRequest  Granted")
                }
            }
        }
    }

    private fun isGpsEnableIntent() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        activityResultLauncher.launch(intent)
    }

    private fun onActivityResult() {
        activityResultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {

        locationRequest = LocationRequest.create().apply {
            interval = 1 * 1000
            fastestInterval = 1 * 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(builder.build())

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            isCurrentLocationSetter(locationResult.lastLocation)
        }
    }

    //location settings.
    private fun stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun isCurrentLocationSetter(location: Location?) = lifecycleScope.launchWhenCreated{

        stopLocationUpdate()
        val outletLanguageId = languageAdapter.getValueId(binding.tvFieldLangauge.text.toString())
        val outletClassId = outletClassAdapter.getValueId(binding.tvFieldClass.text.toString())
        val outletTypeId = outletTypeAdapter.getValueId(binding.tvFieldType.text.toString())
        val outletName = binding.tvFieldCustname.text.toString()
        val contactPerson = binding.tvFieldContactPerson.text.toString()
        val mobileNumber = binding.tvFieldContact.text.toString()
        val contactAddress = binding.tvFieldAddress.text.toString()
        val latitude = location!!.latitude.toString()
        val longitude = location.longitude.toString()
        val employee_id = sessionManager.fetchEmployeeId.first()
        val divisition = "new_outlet"


    }

}