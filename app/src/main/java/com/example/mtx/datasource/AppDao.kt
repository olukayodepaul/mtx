package com.example.mtx.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mtx.dto.BasketLimitList
import com.example.mtx.dto.CustomersList

@Dao
interface AppDao {

    @Query("SELECT * FROM customers")
    suspend fun getCustomers() : List<CustomersList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCustomers(cust: List<CustomersList>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setBasket(cust: List<BasketLimitList>)

    @Query("SELECT * FROM osqty")
    suspend fun fetchBasketFromLocalRep() : List<BasketLimitList>

    @Query("delete from osqty")
    suspend fun deleteBasketFromLocalRep()
}

