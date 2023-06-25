package be.koder.library.api.book

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.BookId

interface UnlinkAuthorFromBookPresenter {

    fun authorLinked(author: AuthorId, book: BookId)

    fun authorDoesNotExist(author: AuthorId)

    fun bookDoesNotExist(book: BookId)
}