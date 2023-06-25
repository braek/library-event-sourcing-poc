package be.koder.library.rdbms.book

import be.koder.library.domain.book.BookService
import be.koder.library.rdbms.event.RdbmsEventStore
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn

class RdbmsBookService(private val eventStore: RdbmsEventStore) : BookService {

    override fun alreadyInUse(isbn: Isbn, exclude: BookId): Boolean {
        TODO("Not yet implemented")
    }

    override fun alreadyInUse(isbn: Isbn): Boolean {
        TODO("Not yet implemented")
    }
}