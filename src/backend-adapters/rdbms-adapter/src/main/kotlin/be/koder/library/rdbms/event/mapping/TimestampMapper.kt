package be.koder.library.rdbms.event.mapping

import be.koder.library.vocabulary.time.Timestamp
import java.util.regex.Pattern

object TimestampMapper {

    private val regexSeconds = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$")
    private val regexMilliseconds = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$")

    // Purpose: make sure that the Timestamp is written as String with precision of milliseconds
    fun map(timestamp: Timestamp): String {
        // Default precision = microseconds
        val str = timestamp.getValue().toString()
        // Precision = seconds
        if (regexSeconds.matcher(str).matches()) {
            return "${str.substring(0, 19)}.000000Z"
        }
        // Precision = milliseconds
        if (regexMilliseconds.matcher(str).matches()) {
            return "${str.substring(0, 23)}000Z"
        }
        return str
    }
}