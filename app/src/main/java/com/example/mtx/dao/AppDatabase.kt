package com.example.mtx.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mtx.datasource.AppDao
import com.example.mtx.dto.CustomersListEntity


@Database(entities = [CustomersListEntity::class],version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val appdao: AppDao
    companion object {
        val DATABASE_NAME = "itl_m_1_2_3"
    }
}