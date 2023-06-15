package be.koder.library.domain.author.event

import be.koder.library.domain.event.Event
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

data class AuthorRemoved(
    override val id: EventId,
    override val occurredOn: Timestamp,
    override val tags: Set<AggregateId>,
    val authorId: AuthorId
) : Event {

    constructor(id: EventId, occurredOn: Timestamp, authorId: AuthorId) : this(
        id, occurredOn, setOf(authorId), authorId
    )
}