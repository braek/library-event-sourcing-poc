package be.koder.library.rdbms.event.json

import be.koder.library.domain.book.event.BookCreated
import be.koder.library.rdbms.mapper.TimestampMapper
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn
import be.koder.library.vocabulary.book.Title
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

data class BookCreatedJson internal constructor(
    override val eventId: String,
    override val eventType: String,
    override val occurredOn: String,
    val bookId: String,
    val title: String,
    val isbn: String,
    val author: String
) : EventJson<BookCreated> {

    constructor(event: BookCreated) : this(
        event.id.toString(),
        BookCreated::class.simpleName!!,
        TimestampMapper.map(event.occurredOn),
        event.bookId.toString(),
        event.title.toString(),
        event.isbn.toString(),
        event.author.toString()
    )

    override fun toEvent(): BookCreated {
        return BookCreated(
            EventId.fromString(eventId),
            Timestamp.fromString(occurredOn),
            BookId.fromString(bookId),
            Title.fromString(title),
            Isbn.fromString(isbn),
            AuthorId.fromString(author)
        )
    }
}