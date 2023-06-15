package be.koder.library.vocabulary.domain

interface ValueObject {
    fun getValue(): Any

    override fun toString(): String
}