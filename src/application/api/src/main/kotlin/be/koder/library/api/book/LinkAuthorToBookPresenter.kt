package be.koder.library.api.book

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.BookId

interface LinkAuthorToBookPresenter {

    fun authorLinked(author: AuthorId, book: BookId)

    fun authorAlreadyLinked(author: AuthorId)

    fun authorDoesNotExist(author: AuthorId)

    fun bookDoesNotExist(book: BookId)
}