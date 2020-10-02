package com.playground.mvi.ui

import com.playground.mvi.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState : DataState<*>?)

}

