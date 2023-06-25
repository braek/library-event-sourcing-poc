package be.koder.library.domain.book

import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn

interface BookService {

    fun alreadyInUse(isbn: Isbn, exclude: BookId): Boolean

    fun alreadyInUse(isbn: Isbn): Boolean
}