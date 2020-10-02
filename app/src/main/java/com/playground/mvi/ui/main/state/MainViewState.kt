package com.playground.mvi.ui.main.state

import com.playground.mvi.model.BlogPost
import com.playground.mvi.model.User

data class MainViewState(

    var blogPosts: List<BlogPost>? = null,
    var user: User? = null
)