package be.koder.library.domain.book

import be.koder.library.domain.entity.EventSourcedAggregate
import be.koder.library.domain.author.AuthorService
import be.koder.library.domain.book.event.AuthorLinkedToBook
import be.koder.library.domain.book.event.BookCreated
import be.koder.library.domain.book.presenter.LinkAuthorToBookDomainPresenter
import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStream
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn
import be.koder.library.vocabulary.book.Title

class Book(eventStream: EventStream) : EventSourcedAggregate(eventStream) {

    private lateinit var id: BookId
    private lateinit var title: Title
    private lateinit var isbn: Isbn
    private lateinit var authors: MutableSet<AuthorId>

    override fun getId(): BookId {
        return id
    }

    fun takeSnapshot(): BookSnapshot {
        return BookSnapshot(
            id, isbn, title, authors.toSet()
        )
    }

    override fun dispatch(event: Event) {
        if (event is BookCreated) {
            exec(event)
        }
        if (event is AuthorLinkedToBook) {
            exec(event)
        }
    }

    private fun exec(event: BookCreated) {
        this.id = event.bookId
        this.title = event.title
        this.isbn = event.isbn
        this.authors = mutableSetOf()
        this.authors.add(event.author)
    }

    private fun exec(event: AuthorLinkedToBook) {
        this.authors.add(event.author)
    }

    fun linkAuthor(author: AuthorId, authorService: AuthorService, presenter: LinkAuthorToBookDomainPresenter) {
        if (!authorService.exists(author)) {
            presenter.authorDoesNotExist(author)
            return
        }
        if (authors.contains(author)) {
            presenter.authorAlreadyLinked(author)
            return
        }
        apply(AuthorLinkedToBook(author, id))
        presenter.linked(author, id)
    }

    companion object {
        @JvmStatic
        fun create(title: Title, isbn: Isbn, author: AuthorId): Book {
            val book = Book(EventStream.empty())
            book.apply(BookCreated(BookId.createNew(), title, isbn, author))
            return book
        }
    }
}