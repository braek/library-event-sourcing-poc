package be.koder.library.domain.book.event

import be.koder.library.domain.event.Event
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

data class AuthorLinkedToBook internal constructor(
    override val id: EventId,
    override val occurredOn: Timestamp,
    override val tags: Set<AggregateId>,
    val author: AuthorId,
    val book: BookId
) : Event {

    constructor(author: AuthorId, book: BookId) : this(
        EventId.createNew(),
        Timestamp.now(),
        setOf(author, book),
        author,
        book
    )
}