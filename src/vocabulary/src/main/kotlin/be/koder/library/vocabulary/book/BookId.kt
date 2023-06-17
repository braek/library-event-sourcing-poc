package be.koder.library.vocabulary.book

import be.koder.library.vocabulary.domain.AggregateId
import java.util.*

class BookId private constructor(private val value: UUID) : AggregateId {

    override fun getValue(): UUID {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as BookId
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        @JvmStatic
        fun createNew(): BookId {
            return BookId(UUID.randomUUID())
        }

        @JvmStatic
        fun fromUuid(uuid: UUID): BookId {
            return BookId(uuid)
        }

        @JvmStatic
        fun fromString(str: String): BookId {
            return BookId(UUID.fromString(str))
        }
    }
}