package be.koder.library.domain.book.presenter

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.BookId

interface LinkAuthorToBookDomainPresenter {

    fun linked(author: AuthorId, book: BookId)

    fun authorDoesNotExist(author: AuthorId)
}