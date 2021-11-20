package com.example.mtx.ui.salesentry

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtx.dto.ModulesResponse
import com.example.mtx.dto.SalesEntryMapperInterface
import com.example.mtx.dto.toBasketLimit
import com.example.mtx.ui.salesentry.repository.SalesEntryRepo
import com.example.mtx.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SalesEntryViewModel @ViewModelInject constructor(private val repo : SalesEntryRepo): ViewModel() {

    private val _basketResponseState = MutableStateFlow<NetworkResult<SalesEntryMapperInterface>>(
        NetworkResult.Empty)
    val basketResponseState get() = _basketResponseState

    fun isUserDailyBaskets(employee_id: Int, sysdate: String, curDate: String) = viewModelScope.launch {

        _basketResponseState.value = NetworkResult.Loading

        try {

            val mapper = SalesEntryMapperInterface()

            if(sysdate==curDate){

                if(repo.fetchBasketFromLocalRep().isNotEmpty()){

                    val localData = repo.fetchBasketFromLocalRep() //send this back
                    mapper.data = localData
                    mapper.message = ""
                    mapper.status = 200

                }else{

                    val remoteData = repo.fetchBasketFromRemoteRep(employee_id)

                    if(remoteData.status==200) {

                        repo.setBasket(remoteData.basketlimit!!.map { it.toBasketLimit()})
                        val localData = repo.fetchBasketFromLocalRep()

                        if(localData.isEmpty()){
                            mapper.data = emptyList()
                            mapper.message = "Error in persisting data, Please check your phone memory"
                            mapper.status = 400
                        }else{
                            mapper.data = localData
                            mapper.message = ""
                            mapper.status = 200
                        }

                    }else{
                        mapper.data = emptyList()
                        mapper.message = "Basket is not assign to this users"
                        mapper.status = 400
                    }
                }
            }else{

                repo.deleteBasketFromLocalRep()

                val remoteData = repo.fetchBasketFromRemoteRep(employee_id)

                if(remoteData.status==200) {

                    repo.setBasket(remoteData.basketlimit!!.map { it.toBasketLimit()})
                    val localData = repo.fetchBasketFromLocalRep()

                    if(localData.isEmpty()){
                        mapper.data = emptyList()
                        mapper.message = "Error in persisting data, Please check your phone memory"
                        mapper.status = 400
                    }else{
                        mapper.data = localData
                        mapper.message = ""
                        mapper.status = 200
                    }

                }else{
                    mapper.data = emptyList()
                    mapper.message = "Basket is not assign to this users"
                    mapper.status = 400
                }
            }

            _basketResponseState.value = NetworkResult.Success(mapper)
        } catch (e: Throwable) {
            _basketResponseState.value = NetworkResult.Error(e)
        }
    }


}