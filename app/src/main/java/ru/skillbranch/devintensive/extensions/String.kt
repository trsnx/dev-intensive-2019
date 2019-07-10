package ru.skillbranch.devintensive.extensions

import java.util.Collections.replaceAll
import android.R.string
import java.util.regex.Pattern


fun String.truncate(count:Int = 16):String {
    val s = trimEnd()
    if (s.length <= count)
        return this
    return s.substring(0, count).trimEnd() + "..."
}

fun String.stripHtml():String {
    if (length == 0)
        return "";

    val m = Pattern.compile("<.+?>").matcher(this)
    val s:String =  m.replaceAll("")
    return s.replace("\\s+".toRegex(), " ")
}