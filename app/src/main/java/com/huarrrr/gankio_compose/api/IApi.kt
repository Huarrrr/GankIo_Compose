package com.huarrrr.gankio_compose.api

import com.huarrrr.gankio_compose.model.ArticleListData
import com.huarrrr.gankio_compose.model.BannerBean
import com.huarrrr.gankio_compose.network.Response
import retrofit2.http.*

/**
 * IApi
 *
 * @author Huarrrr on 2021/6/15
 */


interface IApi {

    /**
     * banner   https://gank.io/api/v2/banners
     * 请求方式: GET
     */
    @GET("banners")
    suspend fun getHomeBanner(): Response<List<BannerBean>>


    /**
     * 随机文章   https://gank.io/api/v2/random/category/<category>/type/<type>/count/<count>
     * 请求方式: GET
     */
    @GET("data/category/All/type/All/page/{page}/count/10")
    suspend fun getHomeArticleList(@Path("page") page: Int = 1): Response<List<ArticleListData>>
}