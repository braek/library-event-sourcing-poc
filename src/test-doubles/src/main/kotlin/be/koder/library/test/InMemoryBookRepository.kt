package be.koder.library.test

import be.koder.library.domain.book.Book
import be.koder.library.domain.book.BookRepository
import be.koder.library.domain.book.IsbnService
import be.koder.library.domain.book.event.BookCreated
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn
import java.util.*

class InMemoryBookRepository(private val eventStore: InMemoryEventStore) : BookRepository, IsbnService {

    override fun get(id: BookId): Optional<Book> {
        val eventStream = eventStore.query(id)
        if (eventStream.isEmpty()) {
            return Optional.empty()
        }
        return Optional.of(Book(eventStream))
    }

    override fun save(aggregate: Book) {
        eventStore.appendMutations(aggregate)
    }

    override fun alreadyInUse(isbn: Isbn, exclude: BookId): Boolean {
        val stack = buildIsbnStack()
        stack.remove(exclude)
        return stack.containsValue(isbn)
    }

    override fun alreadyInUse(isbn: Isbn): Boolean {
        return buildIsbnStack().containsValue(isbn)
    }

    private fun buildIsbnStack(): MutableMap<BookId, Isbn> {
        val stack: MutableMap<BookId, Isbn> = mutableMapOf()
        val eventStream = eventStore.query(
            BookCreated::class.simpleName!!
        )
        eventStream.stream()
            .filter { it is BookCreated }
            .map { it as BookCreated }
            .forEach { stack[it.bookId] = it.isbn }
        return stack
    }
}