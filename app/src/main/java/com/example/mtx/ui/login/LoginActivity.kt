package com.example.mtx.ui.login

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.mtx.R
import com.example.mtx.databinding.ActivityMainBinding
import com.example.mtx.dto.User
import com.example.mtx.ui.module.ModulesActivity
import com.example.mtx.util.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var sessionManager: SessionManager

    private var userName: String? = null

    private var password: String? = null

    private lateinit var database: FirebaseDatabase

    private var hasGps = false

    lateinit var mLocationManager: LocationManager

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        database = FirebaseDatabase.getInstance()
        binding.loginid.setOnClickListener(this)
        loginStateFlow()
        onActivityResult()
        //setRequestedToken()
    }

    private fun setLogin() = lifecycleScope.launch {

        userName = binding.etUsername.text.toString()
        password = binding.etPassword.text.toString()

        if (userName!!.isEmpty() || password!!.isEmpty()) {

            ToastDialog(applicationContext, "Enter username and password").toast

        } else {

            if(sessionManager.fetchDate.first() == GeoFencing.currentDate){
                val intent = Intent(applicationContext, ModulesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()

            }else{
               viewModel.fetchAllSalesEntries(userName!!, password!!)
            }
        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.loginid -> {
                isPermissionRequest()
            }
        }
    }

    private fun loginStateFlow() {
        lifecycleScope.launchWhenResumed {
            viewModel.loginResponseState.collect {
                it.let {
                    when (it) {

                        is NetworkResult.Empty -> {
                        }

                        is NetworkResult.Error -> {
                            binding.loader.isVisible = false
                            ToastDialog(applicationContext, it.throwable!!.message.toString()).toast
                        }

                        is NetworkResult.Loading -> {
                            binding.loader.isVisible = true
                        }

                        is NetworkResult.Success -> {

                            binding.loader.isVisible = false

                            if(it.data!!.status==200){

                                sessionManager.deleteStore()
                                sessionManager.storeEmployeeId(it.data.login!!.employee_id!!)
                                sessionManager.storeDate(it.data.login!!.dates!!)
                                sessionManager.storeEmployeeName(it.data.login!!.name!!)
                                sessionManager.storeEmployeeEdcode(it.data.login!!.employee_code!!)
                                sessionManager.storeRegionId(it.data.login!!.region_id!!)
                                sessionManager.storeDepotLat(it.data.login!!.depotlat!!)
                                sessionManager.storeDepotLng(it.data.login!!.depotlng!!)
                                sessionManager.storeWaiver(it.data.login!!.depotwaiver!!)
                                sessionManager.storeUsername(userName!!)
                                sessionManager.storePassword(password!!)

                                val intent = Intent(applicationContext, ModulesActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()

                            }else{
                                ToastDialog(applicationContext, it.data.msg!!).toast
                            }
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

        if (usesPermission.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, usesPermission.toTypedArray(), 0)
            return
        } else if (!hasGps) {
            isGpsEnableIntent()
            return
        } else if (available == ConnectionResult.API_UNAVAILABLE) {
            ToastDialog(applicationContext, "Play Update the google play service");
            return
        } else {
            setLogin()
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
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            }
    }
//
//    private fun setRequestedToken() {
//        val user = User("paul", "email@paul")
//        val references =    database.getReference("/defaulttoken/")
//        references.child("shbshbshbsq").setValue(user)
//    }


}

