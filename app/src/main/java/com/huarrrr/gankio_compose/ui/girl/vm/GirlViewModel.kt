package com.huarrrr.gankio_compose.ui.girl.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huarrrr.gankio_compose.model.GankData
import com.huarrrr.gankio_compose.network.Api
import com.huarrrr.gankio_compose.network.apiCall
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * ProFileViewModel
 *
 * @author Huarrrr on 2021/6/17
 */

class GirlViewModel:ViewModel() {
    var list by mutableStateOf(mutableListOf<GankData>())
    init {
        lazyLoad()
    }

    fun lazyLoad() {
        viewModelScope.launch {
            //第一次请求接口返回101，所以这里搞两次
            val gankDeffer = async { apiCall { Api.get().getRandomGank() } }
            val gankDeffer1 = async { apiCall { Api.get().getRandomGank() } }
            val gankRes = gankDeffer.await()
            val gankRes1 = gankDeffer1.await()
            if (gankRes.isSuccess()){
                list = gankRes.data!!
                return@launch
            }
            else if (gankRes1.isSuccess()) {
                list = gankRes1.data!!
            }
        }
    }
}