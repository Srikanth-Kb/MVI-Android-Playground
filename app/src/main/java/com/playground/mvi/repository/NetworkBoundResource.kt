package com.playground.mvi.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.playground.mvi.ui.main.state.MainViewState
import com.playground.mvi.util.*
import com.playground.mvi.util.Constants.Companion.TESTING_NETWORK_DELAY
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//Response object - return type from the retrofit calls (input)
// ViewState Type - type returned from repository (output)
// input ---> wrap ----> output
abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)

        GlobalScope.launch(IO) {
            delay(TESTING_NETWORK_DELAY) //usd for testing loading display
            withContext(Main) {
                val apiResponse = createCall()

                result.addSource(apiResponse) {response ->
                    result.removeSource(apiResponse)

                    handleNetworkCall(response)

                }
            }

        }
    }

    fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when(response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse -> {
                println("DEBUG : NetworkBoundResource : ${response.errorMessage}")
                onReturnError(response.errorMessage)
            }
            is ApiEmptyResponse -> {
                println("DEBUG : NetworkBoundResource : HTTP ERROR 204 - RETURNED NOTHING")
                onReturnError("HTTP ERROR 204 - RETURNED NOTHING")
            }
        }
    }

    fun onReturnError(message : String) {
        result.value = DataState.error(message)
    }

    abstract fun handleApiSuccessResponse(response : ApiSuccessResponse<ResponseObject>)

    abstract fun createCall() : LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>
}