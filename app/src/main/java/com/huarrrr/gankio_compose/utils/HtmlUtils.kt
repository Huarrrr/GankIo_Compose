package com.huarrrr.gankio_compose.utils

import android.os.Build
import android.text.Html

/**
 * HtmlUtils
 *
 * @author Huarrrr on 2021/6/15
 *
 * 将Html文本转为普通字符串
 */
fun getHtmlText(text: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        text
    }
}