package be.koder.library.usecase.author

import be.koder.library.api.author.CreateAuthor
import be.koder.library.api.author.CreateAuthorPresenter
import be.koder.library.domain.author.Author
import be.koder.library.domain.author.AuthorRepository
import be.koder.library.domain.author.EmailService
import be.koder.library.domain.event.EventStreamPublisher
import be.koder.library.usecase.UseCase
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName

class CreateAuthorUseCase(
    private val authorRepository: AuthorRepository,
    private val eventStreamPublisher: EventStreamPublisher,
    private val emailService: EmailService
) : CreateAuthor, UseCase<CreateAuthorCommand, CreateAuthorPresenter> {

    override fun createAuthor(firstName: FirstName, lastName: LastName, email: EmailAddress, presenter: CreateAuthorPresenter) {
        execute(CreateAuthorCommand(firstName, lastName, email), presenter)
    }

    override fun execute(command: CreateAuthorCommand, presenter: CreateAuthorPresenter) {
        if (emailService.alreadyInUse(command.email)) {
            presenter.emailAlreadyInUse(command.email)
            return
        }
        val author = Author.create(command.firstName, command.lastName, command.email)
        authorRepository.save(author)
        eventStreamPublisher.publish(author.getMutations())
        presenter.created(author.getId())
    }
}