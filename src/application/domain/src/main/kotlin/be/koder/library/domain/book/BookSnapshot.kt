package be.koder.library.domain.book

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn
import be.koder.library.vocabulary.book.Title

data class BookSnapshot(
    val id: BookId,
    val isbn: Isbn,
    val title: Title,
    val authors: Set<AuthorId>
)