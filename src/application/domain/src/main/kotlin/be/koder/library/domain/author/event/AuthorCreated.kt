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
    override val id: EventId,
    override val occurredOn: Timestamp,
    override val tags: Set<AggregateId>,
    val authorId: AuthorId,
    val firstName: FirstName,
    val lastName: LastName,
    val emailAddress: EmailAddress
) : Event {

    constructor(authorId: AuthorId, firstName: FirstName, lastName: LastName, emailAddress: EmailAddress) : this(
        EventId.createNew(), Timestamp.now(), setOf(authorId), authorId, firstName, lastName, emailAddress
    )

    constructor(id: EventId, occurredOn: Timestamp, authorId: AuthorId, firstName: FirstName, lastName: LastName, emailAddress: EmailAddress) : this(
        id, occurredOn, setOf(authorId), authorId, firstName, lastName, emailAddress
    )
}