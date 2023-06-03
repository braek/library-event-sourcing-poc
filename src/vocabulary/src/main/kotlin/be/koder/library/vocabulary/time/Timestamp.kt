package be.koder.library.vocabulary.time

import be.koder.library.vocabulary.domain.ValueObject
import com.sun.jdi.Type
import com.sun.jdi.Value
import com.sun.jdi.VirtualMachine
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class Timestamp private constructor(private val value: Instant) : ValueObject {

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
        return LocalDateTime.ofInstant(value, ZoneOffset.UTC)
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
        fun fromLocalDateTime(t: LocalDateTime): Timestamp {
            return Timestamp(t.toInstant(ZoneOffset.UTC))
        }
    }
}