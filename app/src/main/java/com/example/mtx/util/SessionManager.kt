package com.example.mtx.util

import android.content.Context
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionManager (context: Context)  {

    private val dataStore = context.createDataStore(name = "user_session_com_mtx_005")

    companion object {
        val employeeId = preferencesKey<Int>("employeeId")
        val userName = preferencesKey<String>("username")
        val passWord = preferencesKey<String>("password")
        val entryDate = preferencesKey<String>("entryDate")
        val customerEntryDate = preferencesKey<String>("customerEntryDate")
        val employeeName = preferencesKey<String>("employeeName")
        val employeeEdcode= preferencesKey<String>("employeeEdcode")
        val regionId = preferencesKey<Int>("regionId")
    }

    //delete state flow
    suspend fun deleteStore() {
        dataStore.edit {
            it.clear()
        }
    }

    //store session
    suspend fun storeRegionId(region: Int) {
        dataStore.edit {
            it[regionId] = region
        }
    }

    //retrieve session
    val fetchRegion: Flow<Int> = dataStore.data.map {
        it[regionId] ?: 0
    }


    //store session
    suspend fun storeEmployeeEdcode(employee: String) {
        dataStore.edit {
            it[employeeEdcode] = employee
        }
    }

    //retrieve session
    val fetchEmployeeEdcode: Flow<String> = dataStore.data.map {
        it[employeeEdcode] ?: ""
    }

    //store session
    suspend fun storeEmployeeName(employee: String) {
        dataStore.edit {
            it[employeeName] = employee
        }
    }

    //retrieve session
    val fetchEmployeeName: Flow<String> = dataStore.data.map {
        it[employeeName] ?: ""
    }

    //store session
    suspend fun storeEmployeeId(employee_id: Int) {
        dataStore.edit {
            it[employeeId] = employee_id
        }
    }

    //retrieve session
    val fetchEmployeeId: Flow<Int> = dataStore.data.map {
        it[employeeId] ?: 0
    }


    //store session
    suspend fun storeUsername(username: String) {
        dataStore.edit {
            it[userName] = username
        }
    }

    //retrieve session
    val fetchUsername: Flow<String> = dataStore.data.map {
        it[userName] ?: ""
    }

    //store session
    suspend fun storePassword(password: String) {
        dataStore.edit {
            it[passWord] = password
        }
    }

    //retrieve session
    val fetchPassword: Flow<String> = dataStore.data.map {
        it[passWord] ?: ""
    }


    //store session
    suspend fun storeDate(date: String) {
        dataStore.edit {
            it[entryDate] = date
        }
    }

    //retrieve session
    val fetchDate: Flow<String> = dataStore.data.map {
        it[entryDate] ?: "0000-00-00"
    }

    //store session
    suspend fun storeCustomerEntryDate(date: String) {
        dataStore.edit {
            it[customerEntryDate] = date
        }
    }

    //retrieve session
    val fetchCustomerEntryDate: Flow<String> = dataStore.data.map {
        it[customerEntryDate] ?: "0000-00-00"
    }

}