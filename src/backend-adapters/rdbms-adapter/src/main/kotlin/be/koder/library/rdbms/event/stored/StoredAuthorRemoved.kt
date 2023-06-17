package be.koder.library.rdbms.event.stored

import be.koder.library.domain.author.event.AuthorRemoved
import be.koder.library.rdbms.mapper.TimestampMapper
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

data class StoredAuthorRemoved internal constructor(
    override val eventId: String,
    override val occurredOn: String,
    val authorId: String
) : StoredEvent<AuthorRemoved> {

    constructor(event: AuthorRemoved) : this(
        event.id.toString(),
        TimestampMapper.map(event.occurredOn),
        event.authorId.toString()
    )

    override fun toEvent(): AuthorRemoved {
        return AuthorRemoved(
            EventId.fromString(eventId),
            Timestamp.fromString(occurredOn),
            AuthorId.fromString(authorId)
        )
    }
}