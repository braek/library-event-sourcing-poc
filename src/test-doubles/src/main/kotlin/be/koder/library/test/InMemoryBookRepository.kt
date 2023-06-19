package be.koder.library.test

import be.koder.library.domain.book.Book
import be.koder.library.domain.book.BookRepository
import be.koder.library.domain.book.IsbnService
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn
import java.util.*

class InMemoryBookRepository(private val eventStore: InMemoryEventStore) : BookRepository, IsbnService {

    override fun get(id: BookId): Optional<Book> {
        return Optional.empty()
    }

    override fun save(aggregate: Book) {
        if (aggregate.noStateChanges()) {
            return
        }
        get(aggregate.getId()).ifPresent {
            if (aggregate.differsFromOrigin(it)) {
                throw RuntimeException("Optimistic Locking Exception")
            }
        }
        eventStore.append(aggregate.getMutations())
    }

    override fun alreadyInUse(isbn: Isbn, exclude: BookId): Boolean {
        return false
    }

    override fun alreadyInUse(isbn: Isbn): Boolean {
        return false
    }
}