package be.koder.library.vocabulary.author

import be.koder.library.vocabulary.domain.ValueObject
import java.util.*
import java.util.regex.Pattern

class EmailAddress : ValueObject {

    private val value: String
    private val pattern = Pattern.compile("^[a-z]{1,100}$")

    constructor(str: String) {
        val sanitized = str.trim().lowercase(Locale.getDefault())
        if (sanitized.isBlank() || !pattern.matcher(sanitized).matches()) {
            throw InvalidEmailAddressException(sanitized)
        }
        this.value = str
    }

    override fun getValue(): Any {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as EmailAddress
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value
    }
}