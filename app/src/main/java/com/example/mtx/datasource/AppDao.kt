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

    @Query("UPDATE osqty SET inventory=:inventory, pricing=:pricing, orders=:order, entry_time=:entry_time, controlpricing=:controlpricing, controlinventory = :controlinventory, controlorder=:controlorder where  auto=:auto")
    suspend fun updateDailySales(inventory: Double, pricing: Int, order: Double, entry_time: String, controlpricing:Int, controlinventory:Int, controlorder:Int, auto:Int)

    @Query("SELECT count(auto) FROM osqty WHERE   controlpricing = '' OR controlinventory = '' OR controlorder = ''")
    suspend fun validateSalesEntry() : Int

}

