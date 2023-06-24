package be.koder.library.usecase.book

import be.koder.library.api.book.LinkAuthorToBook
import be.koder.library.api.book.LinkAuthorToBookPresenter
import be.koder.library.domain.author.AuthorService
import be.koder.library.domain.book.BookRepository
import be.koder.library.domain.event.EventPublisher
import be.koder.library.usecase.UseCase
import be.koder.library.usecase.book.command.LinkAuthorToBookCommand
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.BookId

class LinkAuthorToBookUseCase(
    private val bookRepository: BookRepository,
    private val eventPublisher: EventPublisher,
    private val authorService: AuthorService
) : UseCase<LinkAuthorToBookCommand, LinkAuthorToBookPresenter>, LinkAuthorToBook {

    override fun linkAuthorToBook(author: AuthorId, book: BookId, presenter: LinkAuthorToBookPresenter) {
        execute(LinkAuthorToBookCommand(author, book), presenter)
    }

    override fun execute(command: LinkAuthorToBookCommand, presenter: LinkAuthorToBookPresenter) {
        bookRepository.get(command.book).ifPresentOrElse({
            it.linkAuthor(command.author)
            bookRepository.save(it)
            eventPublisher.publish(it.getMutations())
            presenter.linked(command.author, command.book)
        }) {
            presenter.bookDoesNotExist(command.book)
        }
    }
}