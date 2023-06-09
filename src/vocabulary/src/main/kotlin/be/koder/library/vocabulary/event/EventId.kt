package be.koder.library.vocabulary.event

import be.koder.library.vocabulary.domain.ValueObject
import java.util.*

class EventId private constructor(private val value: UUID) : ValueObject {

    override fun getValue(): UUID {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as EventId
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }

    companion object {
        @JvmStatic
        fun createNew(): EventId {
            return EventId(UUID.randomUUID())
        }

        @JvmStatic
        fun fromUuid(uuid: UUID): EventId {
            return EventId(uuid)
        }

        @JvmStatic
        fun fromString(str: String): EventId {
            return fromUuid(UUID.fromString(str))
        }
    }
}