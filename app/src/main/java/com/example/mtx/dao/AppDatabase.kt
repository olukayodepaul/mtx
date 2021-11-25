package com.example.mtx.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mtx.datasource.AppDao
import com.example.mtx.dto.BasketLimitList
import com.example.mtx.dto.CustomersList
import com.example.mtx.dto.UserSpinnerEntity


@Database(entities = [CustomersList::class, BasketLimitList::class, UserSpinnerEntity::class],version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val appdao: AppDao
    companion object {
        val DATABASE_NAME = "mtx_m_1_2_3"
    }
}