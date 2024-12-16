package com.example.rynnarriola.searchapp.util

import android.os.Build
import android.text.Html

fun String.toHtmlFormattedString(): String {
    val formattedString = this.replace("\n", "<br/>")
    return if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(formattedString, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(formattedString).toString()
    }
}
