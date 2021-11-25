package com.example.mtx.ui.attendant

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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtx.R
import com.example.mtx.databinding.ActivityLoadInBinding
import com.example.mtx.util.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class LoadInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadInBinding

    private lateinit var sessionManager: SessionManager

    private lateinit var adapter: LoadInAdapter

    private val viewModel: AttendantViewModel by viewModels()

    private var hasGps = false

    lateinit var mLocationManager: LocationManager

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    private var task_id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        sessionManager = SessionManager(this)
        setSupportActionBar(binding.toolbar)
        onActivityResult()
        initAdapter()
        initWidget()
        basketResponse()


    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclers.layoutManager = layoutManager
        binding.recyclers.setHasFixedSize(true)
    }

    private fun initWidget() {

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        lifecycleScope.launchWhenResumed {
            binding.toolbar.subtitle = "${sessionManager.fetchEmployeeName.first()} (${sessionManager.fetchEmployeeEdcode.first()})"

        }

        lifecycleScope.launchWhenCreated {
            viewModel.isUserDailyBaskets( sessionManager.fetchEmployeeId.first(),
                sessionManager.fetchDate.first(),
                GeoFencing.currentDate!!
            )
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clockin -> {
                isPermissionRequest()
                task_id = 3
                getCurrentLocation()
            }
            R.id.breaks -> {
                isPermissionRequest()
                task_id = 4
                getCurrentLocation()
            }
            R.id.close -> {
                isPermissionRequest()
                task_id = 6
                getCurrentLocation()
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.attendantmenus, menu)
        return true
    }

    private fun basketResponse() {
        lifecycleScope.launchWhenResumed {
            viewModel.basketResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty -> { }

                        is NetworkResult.Error -> {}

                        is NetworkResult.Loading -> {}

                        is NetworkResult.Success -> {

                            binding.loaders.isVisible = false

                            val limitToSalesEntry =  it.data!!.data!!.filter {
                                    filters->filters.seperator.equals("1")
                            }

                            adapter = LoadInAdapter(limitToSalesEntry)
                            adapter.notifyDataSetChanged()
                            binding.recyclers.setItemViewCacheSize(limitToSalesEntry.size)
                            binding.recyclers.adapter = adapter

                        }
                    }
                }
            }
        }
    }

    private fun isCurrentLocationSetter(currentLocation: Location?) {
        stopLocationUpdate()
        when(task_id){
            3->{
                lifecycleScope.launchWhenResumed {
                    viewModel.recordTask(sessionManager.fetchEmployeeId.first(), 3, currentLocation!!.latitude.toString(),currentLocation!!.longitude.toString(), "Clockin" )
                }
            }
            4->{
                lifecycleScope.launchWhenResumed {
                    viewModel.recordTask(sessionManager.fetchEmployeeId.first(), 4, currentLocation!!.latitude.toString(),currentLocation!!.longitude.toString(), "Break" )
                }
            }
            6->{
                lifecycleScope.launchWhenResumed {
                    viewModel.recordTask(sessionManager.fetchEmployeeId.first(), 6, currentLocation!!.latitude.toString(),currentLocation!!.longitude.toString(), "Close" )
                }
            }
        }
    }
}