package be.koder.library.vocabulary.author

import be.koder.library.vocabulary.domain.ValueObject
import sun.jvm.hotspot.oops.CellTypeState.value

class FirstName(str: String) : ValueObject {

    private val value: String

    init {
        val sanitized = str.trim()
        if (sanitized.length < 2 || sanitized.length > 20) {
            throw InvalidFirstNameException(sanitized)
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
        other as FirstName
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}