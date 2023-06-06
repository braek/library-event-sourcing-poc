package be.koder.library.domain.author.event

import be.koder.library.domain.event.Event
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

data class AuthorCreated internal constructor(
    val id: EventId, val occurredOn: Timestamp, val tags: Set<AggregateId>, val firstName: FirstName, val lastName: LastName, val email: EmailAddress
) : Event {

    constructor(authorId: AuthorId, firstName: FirstName, lastName: LastName, email: EmailAddress) : this(
        EventId.createNew(), Timestamp.now(), setOf(authorId), firstName, lastName, email
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