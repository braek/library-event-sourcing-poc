package be.koder.library.api.book

import be.koder.library.vocabulary.book.BookId

interface CreateBookPresenter {
    fun created(bookId: BookId)
}