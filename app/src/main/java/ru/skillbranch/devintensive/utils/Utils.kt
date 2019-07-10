package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullname:String?):Pair<String?, String?> {
        val parts : List<String>? = fullname?.trim()?.split(" ");

        val firstName = if (parts?.getOrNull(0)?.length == 0) null else parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1);

        return Pair(firstName, lastName)
    }

    fun toInitials(firstName:String?, lastName:String?):String? {
        var result = ""
        result += when(firstName?.trim()) {
            null, "" -> ""
            else -> firstName?.trim()[0].toUpperCase()
        }
        result += when(lastName?.trim()) {
            null, "" -> ""
            else -> lastName?.trim()[0].toUpperCase()
        }
        return if (result.isEmpty()) null else result
    }

    fun transliteration(str:String, divider:String = " "):String {
        val map = mapOf('а' to "a",'б' to "b",'в' to "v",'г' to "g",'д' to "d",'е' to "e",'ё' to "e",
            'ж' to "zh",'з' to "z",'и' to "i",'й' to "i",'к' to "k",'л' to "l",'м' to "m",'н' to "n",'о' to "o",
            'п' to "p",'р' to "r",'с' to "s",'т' to "t",'у' to "u",'ф' to "f",'х' to "h",'ц' to "c",'ч' to "ch",
            'ш' to "sh",'щ' to "sh",'ъ' to "",'ы' to "i",'ь' to "",'э' to "e",'ю' to "yu",'я' to "ya", ' ' to divider)
        var sb = StringBuilder()
        var isUpperCase = false
        for (s in str) {
            isUpperCase = false
            if (s == s.toUpperCase())
                isUpperCase = true
            if (!map.containsKey(s.toLowerCase())) {
                sb.append(s)
            }
            else {
                if (isUpperCase && s != ' ')
                    sb.append(map[s!!.toLowerCase()]!![0].toUpperCase())
                else
                    sb.append(map[s.toLowerCase()])
            }
        }
        return sb.toString()
    }
}