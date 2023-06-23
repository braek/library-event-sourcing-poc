package be.koder.library.domain.book

import be.koder.library.domain.aggregate.EventSourcedAggregate
import be.koder.library.domain.book.event.BookCreated
import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStream
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn
import be.koder.library.vocabulary.book.Title

class Book(eventStream: EventStream) : EventSourcedAggregate(eventStream) {

    private lateinit var id: BookId
    private lateinit var title: Title
    private lateinit var isbn: Isbn

    override fun getId(): BookId {
        return id
    }

    fun getTitle(): Title {
        return title
    }

    fun getIsbn(): Isbn {
        return isbn
    }

    override fun dispatch(event: Event) {
        if (event is BookCreated) {
            exec(event)
        }
    }

    private fun exec(event: BookCreated) {
        this.id = event.bookId
        this.title = event.title
        this.isbn = event.isbn
    }

    companion object {
        @JvmStatic
        fun create(title: Title, isbn: Isbn): Book {
            val book = Book(EventStream.empty())
            book.apply(BookCreated(BookId.createNew(), title, isbn))
            return book
        }
    }
}