package be.koder.library.vocabulary.time

import be.koder.library.vocabulary.domain.ValueObject
import java.time.*
import java.time.ZoneOffset.UTC
import java.time.temporal.ChronoUnit

class Timestamp private constructor(private var value: Instant) : ValueObject {

    init {
        this.value = value.truncatedTo(ChronoUnit.MICROS)
    }

    override fun getValue(): Instant {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Timestamp
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    fun toLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(value, UTC)
    }

    fun toOffsetDateTime(): OffsetDateTime {
        return OffsetDateTime.ofInstant(value, UTC)
    }

    override fun toString(): String {
        return value.toString()
    }

    companion object {

        @JvmStatic
        fun now(): Timestamp {
            return Timestamp(Instant.now())
        }

        @JvmStatic
        fun fromString(str: String): Timestamp {
            return Timestamp(Instant.parse(str))
        }

        @JvmStatic
        fun fromLocalDateTime(ldt: LocalDateTime): Timestamp {
            return Timestamp(ldt.toInstant(UTC))
        }

        @JvmStatic
        fun fromOffsetDateTime(offsetDateTime: OffsetDateTime): Timestamp {
            return Timestamp(offsetDateTime.toInstant())
        }
    }
}