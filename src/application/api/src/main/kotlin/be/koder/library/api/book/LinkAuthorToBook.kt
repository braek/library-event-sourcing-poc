package be.koder.library.api.book

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.BookId

interface LinkAuthorToBook {
    fun link(author: AuthorId, book: BookId, presenter: LinkAuthorToBookPresenter)
}