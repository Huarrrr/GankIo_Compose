package com.huarrrr.gankio_compose.network

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * ApiCaller
 *
 * @author Huarrrr on 2021/6/15
 */

object ApiCaller {
    const val TAG = "ApiCaller"
}

suspend inline fun <T> apiCall(crossinline call: suspend CoroutineScope.() -> Response<T>): Response<T> {
    return withContext(Dispatchers.IO) {
        val res: Response<T>
        try {
            res = call()
        } catch (e: Throwable) {
            Log.e(ApiCaller.TAG, "request error", e)
            return@withContext Response.fromException(e)
        }
        return@withContext res
    }
}