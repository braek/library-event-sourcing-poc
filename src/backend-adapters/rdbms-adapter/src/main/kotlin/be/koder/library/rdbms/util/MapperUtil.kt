package be.koder.library.rdbms.util

import be.koder.library.vocabulary.time.Timestamp
import java.util.regex.Pattern

object MapperUtil {

    // Purpose: make sure that the Timestamp is written as String with precision of milliseconds
    fun map(timestamp: Timestamp): String {
        val str = timestamp.getValue().toString()
        // Precision = seconds
        if (Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$").matcher(str).matches()) {
            return "${str.substring(0, 19)}.000000Z"
        }
        // Precision = microseconds
        if (Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$").matcher(str).matches()) {
            return "${str.substring(0, 23)}000Z"
        }
        return str
    }
}