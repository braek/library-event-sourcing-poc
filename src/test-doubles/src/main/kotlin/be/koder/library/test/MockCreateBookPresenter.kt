package be.koder.library.test

import be.koder.library.api.book.CreateBookPresenter
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn

class MockCreateBookPresenter : CreateBookPresenter {

    override fun created(bookId: BookId) {
        // Do nothing
    }

    override fun isbnAlreadyInUse(isbn: Isbn) {
        // Do nothing
    }
}