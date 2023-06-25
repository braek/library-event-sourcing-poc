package be.koder.library.rdbms.book

import be.koder.library.domain.book.Book
import be.koder.library.domain.book.BookRepository
import be.koder.library.rdbms.event.RdbmsEventStore
import be.koder.library.vocabulary.book.BookId
import org.springframework.transaction.annotation.Transactional
import java.util.*

open class RdbmsBookRepository(private val eventStore: RdbmsEventStore) : BookRepository {

    override fun get(id: BookId): Optional<Book> {
        val eventStream = eventStore.query(id)
        if (eventStream.isEmpty()) {
            return Optional.empty()
        }
        return Optional.of(Book(eventStream))
    }

    @Transactional
    override fun save(aggregate: Book) {
        // TODO: update inline projections
        eventStore.appendMutations(aggregate)
    }
}