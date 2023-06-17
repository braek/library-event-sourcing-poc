package be.koder.library.rdbms.mapper

import be.koder.library.vocabulary.time.Timestamp
import java.util.regex.Pattern

object TimestampMapper {

    private val regexSeconds = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$")
    private val regexMicroseconds = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$")

    // Purpose: make sure that the Timestamp is written as String with precision of milliseconds
    fun map(timestamp: Timestamp): String {
        val str = timestamp.getValue().toString()
        // Precision = seconds
        if (regexSeconds.matcher(str).matches()) {
            return "${str.substring(0, 19)}.000000Z"
        }
        // Precision = microseconds
        if (regexMicroseconds.matcher(str).matches()) {
            return "${str.substring(0, 23)}000Z"
        }
        return str
    }
}