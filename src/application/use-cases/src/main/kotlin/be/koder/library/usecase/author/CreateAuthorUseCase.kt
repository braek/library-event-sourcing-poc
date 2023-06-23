package be.koder.library.usecase.author

import be.koder.library.api.author.CreateAuthor
import be.koder.library.api.author.CreateAuthorPresenter
import be.koder.library.domain.author.AuthorCreator
import be.koder.library.domain.author.AuthorRepository
import be.koder.library.domain.author.EmailService
import be.koder.library.domain.event.EventPublisher
import be.koder.library.usecase.UseCase
import be.koder.library.usecase.author.command.CreateAuthorCommand
import be.koder.library.usecase.author.presenter.CreateAuthorDomainPresenterDecorator
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName

class CreateAuthorUseCase(
    private val authorRepository: AuthorRepository,
    private val eventPublisher: EventPublisher,
    private val emailService: EmailService
) : CreateAuthor, UseCase<CreateAuthorCommand, CreateAuthorPresenter> {

    override fun createAuthor(firstName: FirstName, lastName: LastName, email: EmailAddress, presenter: CreateAuthorPresenter) {
        execute(CreateAuthorCommand(firstName, lastName, email), presenter)
    }

    override fun execute(command: CreateAuthorCommand, presenter: CreateAuthorPresenter) {
        AuthorCreator(authorRepository, eventPublisher, emailService).create(
            command.firstName,
            command.lastName,
            command.emailAddress,
            CreateAuthorDomainPresenterDecorator(presenter)
        )
    }
}