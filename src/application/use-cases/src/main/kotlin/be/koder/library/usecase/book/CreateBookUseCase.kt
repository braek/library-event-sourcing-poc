package be.koder.library.usecase.book

import be.koder.library.api.book.CreateBook
import be.koder.library.api.book.CreateBookPresenter
import be.koder.library.usecase.UseCase
import be.koder.library.usecase.book.command.CreateBookCommand
import be.koder.library.vocabulary.book.Isbn
import be.koder.library.vocabulary.book.Title

class CreateBookUseCase : UseCase<CreateBookCommand, CreateBookPresenter>, CreateBook {

    override fun createBook(title: Title, isbn: Isbn, presenter: CreateBookPresenter) {
        execute(CreateBookCommand(title, isbn), presenter)
    }

    override fun execute(command: CreateBookCommand, presenter: CreateBookPresenter) {
        TODO("Not yet implemented")
    }
}