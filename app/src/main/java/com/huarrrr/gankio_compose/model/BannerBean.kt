package com.huarrrr.gankio_compose.model

/**
 * BannerBean
 *
 * @author Huarrrr on 2021/6/15
 */

//data class BannerBean(
//    val uid: Int = 0,
//    val desc: String,
//    val id: Int,
//    val imagePath: String,
//    val isVisible: Int,
//    val order: Int,
//    val title: String,
//    val type: Int,
//    val url: String,
//    var filePath: String = "",
//    override var data: String?
//)

data class BannerBean(
    val image: String = "",
    val title: String = "",
    val url: String = "",
    override var data: String?
) : BaseBannerBean()