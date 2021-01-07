package skydu.android.instaclone.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    fun getTime(date: Long): String {

        val format = SimpleDateFormat("yyyy-MM-dd hh:mm")

        val dateString: String = format.format(date)

        return dateString
    }

    fun getTimeInMillis(date: String): Long {
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        )
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return dateFormat.parse(date).time
    }
}