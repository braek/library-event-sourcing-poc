package be.koder.library.vocabulary.author

import be.koder.library.vocabulary.domain.ValueObject

class LastName(str: String) : ValueObject {

    private val value: String

    init {
        val sanitized = str.trim()
        if (sanitized.length < 2 || sanitized.length > 20) {
            throw InvalidLastNameException(sanitized)
        }
        this.value = sanitized.trim()
    }

    override fun getValue(): String {
        return value
    }

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as LastName
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}