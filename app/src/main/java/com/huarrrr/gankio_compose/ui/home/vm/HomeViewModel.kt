package com.huarrrr.gankio_compose.ui.home.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huarrrr.gankio_compose.model.BannerBean
import com.huarrrr.gankio_compose.model.GankData
import com.huarrrr.gankio_compose.network.Api
import com.huarrrr.gankio_compose.network.apiCall
import com.huarrrr.gankio_compose.ui.view.LoadState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var homePageState by mutableStateOf(LoadState.LOADING)

    var bannerList by mutableStateOf(listOf<BannerBean>())
    var gankList by mutableStateOf(listOf<GankData>())
    var girlList by mutableStateOf(listOf<GankData>())


    init {
        loadBanner()
        loadGank()
    }

    fun loadBanner() {
        viewModelScope.launch {
            val bannerRes = apiCall { Api.get().getHomeBanner() }
            if (bannerRes.isSuccess()) {
                if (!bannerRes.data!!.isNullOrEmpty()) {
                    bannerRes.data!!.forEach {
                        it.data = it.image
                    }
                    bannerList = bannerRes.data!!
                }
            }
        }
    }

    fun loadGank() {
        viewModelScope.launch {
            homePageState = LoadState.LOADING
            val articleRes = async { apiCall { Api.get().getHotArticleList() } }.await()
            val gankRes = async { apiCall { Api.get().getHotGankList() } }.await()
            val girlRes = async { apiCall { Api.get().getHotGirlList() } }.await()
            if (articleRes.isSuccess() && gankRes.isSuccess() && girlRes.isSuccess()) {
                homePageState = LoadState.SUCCESS
                if (!articleRes.data!!.isNullOrEmpty()) {
                    gankList = articleRes.data!!
                }

                if (!gankRes.data!!.isNullOrEmpty()) {
                    gankList = gankRes.data!!
                }

                if (!girlRes.data!!.isNullOrEmpty()) {
                    girlList = girlRes.data!!
                }

            } else {
                homePageState = LoadState.FAIL
            }
        }
    }
}