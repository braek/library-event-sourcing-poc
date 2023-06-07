package be.koder.library.domain.author.event

import be.koder.library.domain.event.Event
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

data class AuthorRemoved(
    val id: EventId,
    val occurredOn: Timestamp,
    val tags: Set<AggregateId>
) : Event {

    constructor(authorId: AuthorId) : this(
        EventId.createNew(), Timestamp.now(), setOf(authorId)
    )

    override fun id(): EventId {
        return id
    }

    override fun occurredOn(): Timestamp {
        return occurredOn
    }

    override fun tags(): Set<AggregateId> {
        return tags
    }

    fun authorId(): AuthorId {
        return tags.iterator().next() as AuthorId;
    }
}