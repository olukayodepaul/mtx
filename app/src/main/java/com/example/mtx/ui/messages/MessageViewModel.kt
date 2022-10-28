package com.example.mtx.ui.messages

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtx.dto.EntityAccuracy
import com.example.mtx.dto.toAccuracyEntity
import com.example.mtx.ui.messages.repository.MessageRepo
import com.example.mtx.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MessageViewModel @ViewModelInject constructor(private val repo: MessageRepo) : ViewModel() {

    private val _messageResponseState =
        MutableStateFlow<NetworkResult<List<EntityAccuracy>>>(NetworkResult.Empty)
    val messageResponseState get() = _messageResponseState

    fun isMessageAccuracy(customerCode: String, entriesDate: String) = viewModelScope.launch {
        _messageResponseState.value = NetworkResult.Loading
        try {
            val isDataAccuracy = repo.getDataAccuracy()
            val entryDate = isDataAccuracy.filter { it.entry_date.equals(entriesDate) }

            if (entryDate.isNotEmpty()) {
                _messageResponseState.value = NetworkResult.Success(isDataAccuracy)
            } else {
                val isData = repo.dataAccuracy(customerCode)
                if (isData.status == 200 || isData.accuracy!!.isNotEmpty()) {
                    isData.accuracy!!.map { it.toAccuracyEntity() }
                    _messageResponseState.value = NetworkResult.Success(repo.getDataAccuracy())
                } else {
                    _messageResponseState.value = NetworkResult.Success(emptyList())
                }
            }
        } catch (e: Throwable) {
            _messageResponseState.value = NetworkResult.Error(e)
        }
    }


    /**
     * Update Status icon at on click.
     * @Update Update Status icon at on click
     */
    private val _updateResponseState = MutableStateFlow<NetworkResult<Unit>>(NetworkResult.Empty)
    val updateResponseState get() = _updateResponseState

    fun isMessageUpdateAccuracy(status: Int, id: String) = viewModelScope.launch {
        try {
            _updateResponseState.value =
                NetworkResult.Success(repo.updateDataAccuracyStatus(status, id))
        } catch (e: Throwable) {
            _updateResponseState.value = NetworkResult.Error(e)
        }
    }

    //put it on modules.
    /**
     *  Status count as notification.
     * @SelectCount change message status from unread to read.....
     */
    private val _countResponseState = MutableStateFlow<NetworkResult<Int>>(NetworkResult.Empty)
    val countResponseState get() = _countResponseState

    fun isMessageCountAccuracy() = viewModelScope.launch {
        try {
            _countResponseState.value =
                NetworkResult.Success(repo.getCountDataAccuracyStatus())
        } catch (e: Throwable) {
            _countResponseState.value = NetworkResult.Error(e)
        }
    }


}