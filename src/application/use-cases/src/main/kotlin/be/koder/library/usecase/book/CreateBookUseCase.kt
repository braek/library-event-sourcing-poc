package be.koder.library.usecase.book

import be.koder.library.api.book.CreateBook
import be.koder.library.api.book.CreateBookPresenter
import be.koder.library.domain.book.Book
import be.koder.library.domain.book.BookRepository
import be.koder.library.domain.book.IsbnService
import be.koder.library.domain.event.EventStreamPublisher
import be.koder.library.usecase.UseCase
import be.koder.library.usecase.book.command.CreateBookCommand
import be.koder.library.vocabulary.book.Isbn
import be.koder.library.vocabulary.book.Title

class CreateBookUseCase(
    private val bookRepository: BookRepository,
    private val eventStreamPublisher: EventStreamPublisher,
    private val isbnService: IsbnService
) : UseCase<CreateBookCommand, CreateBookPresenter>, CreateBook {

    override fun createBook(title: Title, isbn: Isbn, presenter: CreateBookPresenter) {
        execute(CreateBookCommand(title, isbn), presenter)
    }

    override fun execute(command: CreateBookCommand, presenter: CreateBookPresenter) {
        if (isbnService.alreadyInUse(command.isbn)) {
            presenter.isbnAlreadyInUse(command.isbn)
            return
        }
        val book = Book.create(command.title, command.isbn)
        bookRepository.save(book)
        eventStreamPublisher.publish(book.getMutations())
        presenter.created(book.getId())
    }
}