package be.koder.library.api.book

import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn

interface CreateBookPresenter {
    fun created(bookId: BookId)

    fun isbnAlreadyInUse(isbn: Isbn)
}