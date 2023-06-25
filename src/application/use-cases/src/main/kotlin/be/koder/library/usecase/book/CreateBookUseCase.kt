package be.koder.library.usecase.book

import be.koder.library.api.book.CreateBook
import be.koder.library.api.book.CreateBookPresenter
import be.koder.library.domain.book.Book
import be.koder.library.domain.book.BookRepository
import be.koder.library.domain.book.BookService
import be.koder.library.domain.event.EventPublisher
import be.koder.library.usecase.UseCase
import be.koder.library.usecase.book.command.CreateBookCommand
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.Isbn
import be.koder.library.vocabulary.book.Title

class CreateBookUseCase(
    private val bookRepository: BookRepository,
    private val eventPublisher: EventPublisher,
    private val bookService: BookService
) : UseCase<CreateBookCommand, CreateBookPresenter>, CreateBook {

    override fun createBook(title: Title, isbn: Isbn, author: AuthorId, presenter: CreateBookPresenter) {
        execute(CreateBookCommand(title, isbn, author), presenter)
    }

    override fun execute(command: CreateBookCommand, presenter: CreateBookPresenter) {
        if (bookService.alreadyInUse(command.isbn)) {
            presenter.isbnAlreadyInUse(command.isbn)
            return
        }
        val book = Book.create(command.title, command.isbn, command.author)
        bookRepository.save(book)
        eventPublisher.publish(book.getMutations())
        presenter.created(book.getId())
    }
}