package be.koder.library.usecase.book

import be.koder.library.api.book.UnlinkAuthorFromBook
import be.koder.library.api.book.UnlinkAuthorFromBookPresenter
import be.koder.library.domain.author.AuthorService
import be.koder.library.domain.book.BookRepository
import be.koder.library.domain.event.EventPublisher
import be.koder.library.usecase.UseCase
import be.koder.library.usecase.book.command.UnlinkAuthorFromBookCommand
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.BookId

class UnlinkAuthorFromBookUseCase(
    val bookRepository: BookRepository,
    val eventPublisher: EventPublisher,
    val authorService: AuthorService
) : UseCase<UnlinkAuthorFromBookCommand, UnlinkAuthorFromBookPresenter>, UnlinkAuthorFromBook {

    override fun unlinkAuthor(author: AuthorId, book: BookId, presenter: UnlinkAuthorFromBookPresenter) {
        execute(UnlinkAuthorFromBookCommand(author, book), presenter)
    }

    override fun execute(command: UnlinkAuthorFromBookCommand, presenter: UnlinkAuthorFromBookPresenter) {
        TODO("Not yet implemented")
    }
}