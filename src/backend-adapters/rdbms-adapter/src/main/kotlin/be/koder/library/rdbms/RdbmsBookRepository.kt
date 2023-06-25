package be.koder.library.rdbms

import be.koder.library.domain.book.Book
import be.koder.library.domain.book.BookRepository
import be.koder.library.vocabulary.book.BookId
import java.util.*

class RdbmsBookRepository(private val eventStore: RdbmsEventStore) : BookRepository {

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
}