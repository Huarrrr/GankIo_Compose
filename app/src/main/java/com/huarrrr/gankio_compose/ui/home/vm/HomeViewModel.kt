package com.huarrrr.gankio_compose.ui.home.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huarrrr.gankio_compose.network.Api
import com.huarrrr.gankio_compose.network.apiCall
import com.huarrrr.gankio_compose.ui.view.LoadState
import com.huarrrr.gankio_compose.ui.view.Toaster
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var pageState by mutableStateOf(LoadState.LOADING)
    var showLoading by mutableStateOf(false)
    var list by mutableStateOf(listOf<Any>())
    var refreshingState by mutableStateOf(false)
    var loadState by mutableStateOf(false)
    private var page = 0

    init {
        firstLoad()
    }

    fun firstLoad() {
        viewModelScope.launch {
            page = 0
            pageState = LoadState.LOADING
            val bannerDeffer = async { apiCall { Api.get().getHomeBanner() } }
            val articleDeffer = async { apiCall { Api.get().getHomeArticleList() } }
            val bannerRes = bannerDeffer.await()
            val articleRes = articleDeffer.await()
            if (bannerRes.isSuccess() && articleRes.isSuccess()) {
                pageState = LoadState.SUCCESS
                list = mutableListOf<Any>().apply {
                    bannerRes.data!!.forEach {
                        it.data = it.image
                    }
                    add(bannerRes.data!!)
                    addAll(articleRes.data!!)
                }
            } else {
                pageState = LoadState.FAIL
            }
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            page = 0
            refreshingState = true
            val bannerDeffer = async { apiCall { Api.get().getHomeBanner() } }
            val articleDeffer = async { apiCall { Api.get().getHomeArticleList() } }
            val bannerRes = bannerDeffer.await()
            val articleRes = articleDeffer.await()
            if (bannerRes.isSuccess()) {
                list = mutableListOf<Any>().apply {
                    bannerRes.data!!.forEach {
                        it.data = it.image
                    }
                    add(bannerRes.data!!)
                    addAll(articleRes.data!!)
                }
                refreshingState = false
            } else {
                refreshingState = false
                Toaster.show("加载失败")
            }
        }
    }

    fun onLoad() {
        viewModelScope.launch {
            loadState = true
            val articleList = apiCall { Api.get().getHomeArticleList(page + 1) }
            if (articleList.isSuccess()) {
                page++
                list = list.toMutableList().apply {
                    addAll(articleList.data!!)
                }
                loadState = false
            } else {
                loadState = false
                Toaster.show("加载失败")
            }
        }
    }
}