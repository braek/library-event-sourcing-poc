package be.koder.library.rdbms.util

import be.koder.library.vocabulary.time.Timestamp
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Given a mapper utility")
class MapperUtilTest {

    @Test
    @DisplayName("when Timestamp with precision of seconds")
    fun testPrecisionOfSeconds() {
        val timestamp = Timestamp.fromString("2001-01-01T10:00:00Z")
        val str = MapperUtil.map(timestamp)
        assertThat(str).isEqualTo("2001-01-01T10:00:00.000000Z")
    }

    @Test
    @DisplayName("when Timestamp with precision of microseconds")
    fun testPrecisionOfMicroseconds() {
        val timestamp = Timestamp.fromString("2001-01-01T10:00:00.001Z")
        val str = MapperUtil.map(timestamp)
        assertThat(str).isEqualTo("2001-01-01T10:00:00.001000Z")
    }
}