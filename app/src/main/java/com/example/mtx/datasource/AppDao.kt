package com.example.mtx.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mtx.dto.BasketLimitList
import com.example.mtx.dto.CustomersList
import com.example.mtx.dto.UserSpinnerEntity

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

    @Query("SELECT count(auto) FROM osqty WHERE   controlpricing = '0' OR controlinventory = '0' OR controlorder = '0'")
    suspend fun validateSalesEntry() : Int

    @Query("UPDATE  osqty SET  controlpricing = '0', controlinventory = '0',  controlorder = '0' , pricing= '0.0', inventory = '0.0', orders = '0.0', entry_time = ''")
    suspend fun setBasketToInitState()

    @Query("SELECT * FROM osqty WHERE  (inventory!='0.0'  OR pricing !='0.0' OR orders !='0.0' ) ")
    suspend fun salesPosted():  List<BasketLimitList>

    @Query("UPDATE  osqty SET  controlpricing = '0', controlinventory = '0',  controlorder = '0' , pricing= '0.0', inventory = '0.0', orders = '0.0', entry_time = '',  order_sold = order_sold - orders  WHERE auto=:auto and seperator = '1'")
    suspend fun resetOrders(auto: Int)

    @Query("UPDATE  customers SET timeago = :timeago WHERE urno = :urno ")
    suspend fun setVisitTime(timeago:String, urno:Int )

    @Query("SELECT * FROM spinners")
    suspend fun fetchSpinnerFromLocalDb() : List<UserSpinnerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCustomer(cust: List<UserSpinnerEntity>)


}

