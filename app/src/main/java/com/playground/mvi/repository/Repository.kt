package com.playground.mvi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.playground.mvi.api.MyRetrofitBuilder
import com.playground.mvi.model.BlogPost
import com.playground.mvi.model.User
import com.playground.mvi.ui.main.state.MainViewState
import com.playground.mvi.util.*

object Repository {

    fun getBlogPosts() : LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    data = MainViewState(
                        blogPosts = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return MyRetrofitBuilder.apiService.getBlogPosts()
            }

        }.asLiveData()
    }

    fun getUser(userId : String) : LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    data = MainViewState(
                        user = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return MyRetrofitBuilder.apiService.getUser(userId)
            }


        }.asLiveData()
    }
}