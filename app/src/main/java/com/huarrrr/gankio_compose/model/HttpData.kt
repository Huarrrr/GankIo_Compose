package com.huarrrr.gankio_compose.model


/**
 * ArticleData
 *
 * @author Huarrrr on 2021/6/16
 */

data class GankData(
    val _id: String = "",
    val author: String = "",
    val category: String = "",
    val createdAt: String = "",
    val desc: String = "",
    val images: MutableList<String> = mutableListOf(),
    val likeCounts: Int = 0,
    val publishedAt: String = "",
    val stars: Int = 0,
    val title: String = "",
    val type: String = "",
    val url: String = "",
    val views: Int = 0
)