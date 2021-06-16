package com.huarrrr.gankio_compose.model


/**
 * ArticleData
 *
 * @author Huarrrr on 2021/6/16
 */

data class ArticleListData(
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

data class ArticleDetailData(
    val _id: String,
    val author: String,
    val category: String,
    val content: String,
    val createdAt: String,
    val desc: String,
    val email: String,
    val images: List<String>,
    val index: Int,
    val isOriginal: Boolean,
    val license: String,
    val likeCounts: Int,
    val likes: List<Any>,
    val markdown: String,
    val originalAuthor: String,
    val publishedAt: String,
    val stars: Int,
    val status: Int,
    val tags: List<Any>,
    val title: String,
    val type: String,
    val updatedAt: String,
    val url: String,
    val views: Int
)