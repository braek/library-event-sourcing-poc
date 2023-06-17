package be.koder.library.vocabulary.book

import be.koder.library.vocabulary.domain.ValueObject

class Title private constructor(str: String) : ValueObject {

    private val value: String

    init {
        val sanitized = str.trim()
        if (sanitized.isEmpty() || sanitized.length > 100) {
            throw InvalidTitleException(sanitized)
        }
        this.value = sanitized
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
        other as Title
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        @JvmStatic
        fun fromString(str: String): Title {
            return Title(str)
        }
    }
}