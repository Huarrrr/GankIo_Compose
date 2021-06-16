package com.huarrrr.gankio_compose.model


/**
 * BaseBannerBean
 *
 * @author Huarrrr on 2021/6/16
 *
 * Banner Model 的基类
 */
abstract class BaseBannerBean {
    // 图片资源 可以是：url、文件路径或者是 drawable id
    abstract val data: Any?
}