package be.koder.library.vocabulary.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Given a class that represents Timestamps")
class TimestampTest {

    @Test
    fun testIt() {
        val str = "2000-01-01T10:00:00.0001Z"
        val timestamp = Timestamp.fromString(str)
        assertThat(timestamp.getValue().toString()).isEqualTo("2000-01-01T10:00:00.000100Z")
    }
}