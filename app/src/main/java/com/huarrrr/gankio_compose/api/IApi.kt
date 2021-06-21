package com.huarrrr.gankio_compose.api

import com.huarrrr.gankio_compose.model.BannerBean
import com.huarrrr.gankio_compose.model.GankData
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
     * 热门文章  https://gank.io/api/v2/hot/<hot_type>/category/<category>/count/<count>
     * 请求方式: GET views浏览量
     */
    @GET("https://gank.io/api/v2/hot/views/category/Article/count/2")
    suspend fun getHotArticleList(): Response<List<GankData>>

    @GET("https://gank.io/api/v2/hot/views/category/GanHuo/count/3")
    suspend fun getHotGankList(): Response<MutableList<GankData>>

    @GET("https://gank.io/api/v2/hot/views/category/Girl/count/10")
    suspend fun getHotGirlList(): Response<List<GankData>>


    /**
     *  分类分页 https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/10
     *  请求方式： GET
     */

    @GET("data/category/{category}/type/{type}/page/{page}/count/10")
    suspend fun getGankList(
        @Path("category") category: String = "GanHuo",
        @Path("type") type: String = "Android",
        @Path("page") page: Int = 1
    ): Response<MutableList<GankData>>

    /**
     * 随机妹子 https://gank.io/api/v2/random/category/Girl/type/Girl/count/10
     * 请求方式： GET
     */
    @GET("random/category/Girl/type/Girl/count/10")
    suspend fun getRandomGank(): Response<MutableList<GankData>>
}