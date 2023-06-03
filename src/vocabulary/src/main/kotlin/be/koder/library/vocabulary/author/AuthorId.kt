package be.koder.library.vocabulary.author

import be.koder.library.vocabulary.domain.AggregateId
import java.util.UUID

class AuthorId private constructor(private val value: UUID) : AggregateId {

    override fun getValue(): UUID {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AuthorId
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {

        @JvmStatic
        fun fromString(str: String): AuthorId {
            return AuthorId(UUID.fromString(str))
        }

        @JvmStatic
        fun fromUuid(uuid: UUID): AuthorId {
            return AuthorId(uuid)
        }
    }
}