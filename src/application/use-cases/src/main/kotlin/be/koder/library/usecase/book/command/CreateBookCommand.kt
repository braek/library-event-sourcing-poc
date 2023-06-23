package be.koder.library.usecase.book.command

import be.koder.library.domain.cqrs.Command
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.Isbn
import be.koder.library.vocabulary.book.Title

data class CreateBookCommand(
    val title: Title,
    val isbn: Isbn,
    val author: AuthorId
) : Command