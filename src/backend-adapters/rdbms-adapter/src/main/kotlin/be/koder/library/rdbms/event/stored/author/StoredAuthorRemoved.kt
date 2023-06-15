package be.koder.library.rdbms.event.stored.author

import be.koder.library.domain.author.event.AuthorRemoved
import be.koder.library.rdbms.event.stored.StoredEvent
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

data class StoredAuthorRemoved internal constructor(
    override val id: String,
    override val occurredOn: String,
    val authorId: String
) : StoredEvent {

    constructor(event: AuthorRemoved) : this(
        event.id.toString(),
        event.occurredOn.toString(),
        event.authorId.toString()
    )

    override fun toEvent(): AuthorRemoved {
        return AuthorRemoved(
            EventId.fromString(id),
            Timestamp.fromString(occurredOn),
            AuthorId.fromString(authorId)
        )
    }
}