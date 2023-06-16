package be.koder.library.rdbms.event

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.domain.AggregateId

object TagMapper {

    fun map(aggregateId: AggregateId): String {
        if (aggregateId is AuthorId) {
            return Tag.AUTHOR.name.lowercase() + "#" + aggregateId.toString()
        }
        throw IllegalArgumentException(String.format("Cannot map AggregateId (%s) to String", aggregateId.toString()))
    }

    enum class Tag {
        AUTHOR
    }
}