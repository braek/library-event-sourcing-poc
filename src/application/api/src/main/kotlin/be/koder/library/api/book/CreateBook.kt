package be.koder.library.api.book

import be.koder.library.vocabulary.book.Isbn
import be.koder.library.vocabulary.book.Title

interface CreateBook {
    fun createBook(title: Title, isbn: Isbn, presenter: CreateBookPresenter)
}