package com.huarrrr.gankio_compose.model


/**
 * BaseModel
 *
 * @author Huarrrr on 2021/6/15
 */

data class BaseModel<T>(
    val `data`: T,
    val status:Int
)