package be.koder.library.usecase.book.command

import be.koder.library.domain.cqrs.Command
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.BookId

data class UnlinkAuthorFromBookCommand(
    val author: AuthorId,
    val book: BookId
) : Command