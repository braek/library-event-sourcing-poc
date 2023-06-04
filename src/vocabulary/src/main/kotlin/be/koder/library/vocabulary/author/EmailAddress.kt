package be.koder.library.vocabulary.author

import be.koder.library.vocabulary.domain.ValueObject
import sun.jvm.hotspot.oops.CellTypeState.value

class EmailAddress : ValueObject {

    private val value: String

    constructor(str: String) {
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