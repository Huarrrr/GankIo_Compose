package com.huarrrr.gankio_compose.ui.gank.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huarrrr.gankio_compose.model.GankData
import com.huarrrr.gankio_compose.network.Api
import com.huarrrr.gankio_compose.network.apiCall
import com.huarrrr.gankio_compose.ui.view.LoadState
import com.huarrrr.gankio_compose.ui.view.Toaster
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * GankViewModel
 *
 * @author Huarrrr on 2021/6/17
 */

class GankViewModel : ViewModel() {
    var pageState by mutableStateOf(LoadState.LOADING)
    var list by mutableStateOf(mutableListOf<GankData>())
    var refreshingState by mutableStateOf(false)
    var loadState by mutableStateOf(false)
    private var page = 1

    init {
        lazyLoad("GanHuo", "Android")
    }

    fun lazyLoad(category: String, type: String) {
        viewModelScope.launch {
            pageState = LoadState.LOADING
            val gankRes = apiCall {
                Api.get().getGankList(
                    category,
                    type,
                    page
                )
            }
            if (gankRes.isSuccess()) {
                if (gankRes.total_counts == 0) {
                    pageState = LoadState.EMPTY
                }
                pageState = LoadState.SUCCESS
                list = gankRes.data!!
            } else {
                pageState = LoadState.FAIL
            }
        }
    }

    //下拉刷新
    fun onRefresh(category: String, type: String) {
        viewModelScope.launch {
            page = 1
            refreshingState = true
            val gankDeffer = async { apiCall { Api.get().getGankList(category, type, page) } }
            val gankRes = gankDeffer.await()
            if (gankRes.isSuccess()) {
                if (gankRes.total_counts == 0) {
                    pageState = LoadState.EMPTY
                }
                pageState = LoadState.SUCCESS
                list = gankRes.data!!
                refreshingState = false
            } else {
                refreshingState = false
                Toaster.show("刷新失败")
            }
        }
    }

    //上拉分页加载
    fun onLoadMore(category: String, type: String) {
        viewModelScope.launch {
            loadState = true
            val gankDeffer = async { apiCall { Api.get().getGankList(category, type, page + 1) } }
            val gankRes = gankDeffer.await()
            if (gankRes.isSuccess()) {
                page++
                if (gankRes.data!!.isNullOrEmpty()) {
                    Toaster.show("没有更多数据了...")
                }
                list.addAll(gankRes.data!!)
                loadState = false
            } else {
                loadState = false
                Toaster.show("加载失败")
            }
        }
    }
}