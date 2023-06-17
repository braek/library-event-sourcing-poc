package be.koder.library.vocabulary.book

import be.koder.library.vocabulary.domain.ValueObject
import java.util.regex.Pattern

class Isbn private constructor(str: String) : ValueObject {

    private val value: String
    private val regex = Pattern.compile("^\\d{10}|\\d{13}$")

    init {
        val sanitized = str.trim()
        if (!regex.matcher(sanitized).matches()) {
            throw InvalidIsbnException(sanitized)
        }
        value = sanitized
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

        other as Isbn

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}