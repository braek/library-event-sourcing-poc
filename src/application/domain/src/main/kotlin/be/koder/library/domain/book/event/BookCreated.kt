package be.koder.library.domain.book.event

import be.koder.library.domain.event.Event
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn
import be.koder.library.vocabulary.book.Title
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

data class BookCreated internal constructor(
    override val id: EventId,
    override val occurredOn: Timestamp,
    override val tags: Set<AggregateId>,
    val bookId: BookId,
    val title: Title,
    val isbn: Isbn
) : Event {

    constructor(bookId: BookId, title: Title, isbn: Isbn) : this(
        EventId.createNew(),
        Timestamp.now(),
        setOf(bookId),
        bookId,
        title,
        isbn
    )

    constructor(id: EventId, occurredOn: Timestamp, bookId: BookId, title: Title, isbn: Isbn) : this(
        id,
        occurredOn,
        setOf(bookId),
        bookId,
        title,
        isbn
    )
}