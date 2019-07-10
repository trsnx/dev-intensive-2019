package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = SECOND * 60L
const val HOUR = MINUTE * 60L
const val DAY = HOUR * 24L

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}
fun Date.humanizeDiff(date:Date = Date()) : String {
    val isBackTime = this.time < date.time
    val diff = Math.abs(this.time - date.time)
    var msg = when (diff) {
        in 0 * SECOND .. 1 * SECOND -> return "только что"
        in 1 * SECOND .. 45 * SECOND -> "несколько секунд"
        in 45 * SECOND .. 75* SECOND -> "минуту"
        in 75 * SECOND .. 45 * MINUTE -> TimeUnits.MINUTE.plural((diff/ MINUTE).toInt())
        in 45 * MINUTE .. 75 * MINUTE -> "час"
        in 75 * MINUTE .. 22 * HOUR -> TimeUnits.HOUR.plural((diff / HOUR).toInt())
        in 22 * HOUR .. 26 * HOUR -> "день"
        in 26 * HOUR .. 360 * DAY -> TimeUnits.DAY.plural((diff/ DAY).toInt())
        else -> "более года"
    }
    if (msg == "более года" && !isBackTime)
        return "более чем через год"
    if (isBackTime)
        msg += " назад"
    else
        msg = "через $msg"
    return msg;
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int): String {
            return pluralImpl(value, "секунду", "секунды", "секунд")
        }
    },
    MINUTE {
        override fun plural(value: Int): String {
            return pluralImpl(value, "минуту", "минуты", "минут")
        }

    },
    HOUR {
        override fun plural(value: Int): String {
            return pluralImpl(value, "час", "часа", "часов")
        }
    },
    DAY {
        override fun plural(value: Int): String {
            return pluralImpl(value, "день", "дня", "дней")
        }
    };

    abstract fun plural(value:Int):String
    private companion object Helper {
        fun pluralImpl(value:Int, oneMod:String, twoMod:String, fiveMod:String):String
        {
            return when (value % 100) {
                0 -> "$value $fiveMod"
                1 -> "$value $oneMod"
                in 2..4 -> "$value $twoMod"
                in 5..20 -> "$value $fiveMod"
                else -> when (value % 10) {
                    0 -> "$value $fiveMod"
                    1 -> "$value $oneMod"
                    in 2..4 -> "$value $twoMod"
                    else -> "$value $fiveMod"
                }
            }
        }
    }
}