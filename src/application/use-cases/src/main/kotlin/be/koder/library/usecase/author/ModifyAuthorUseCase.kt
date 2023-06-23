package be.koder.library.usecase.author

import be.koder.library.api.author.ModifyAuthor
import be.koder.library.api.author.ModifyAuthorPresenter
import be.koder.library.domain.author.AuthorRepository
import be.koder.library.domain.author.EmailService
import be.koder.library.domain.event.EventPublisher
import be.koder.library.usecase.UseCase
import be.koder.library.usecase.author.command.ModifyAuthorCommand
import be.koder.library.usecase.author.presenter.ModifyAuthorDomainPresenterDecorator
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName

class ModifyAuthorUseCase(
    private val authorRepository: AuthorRepository,
    private val emailService: EmailService,
    private val eventPublisher: EventPublisher
) : ModifyAuthor, UseCase<ModifyAuthorCommand, ModifyAuthorPresenter> {

    override fun modifyAuthor(authorId: AuthorId, firstName: FirstName, lastName: LastName, emailAddress: EmailAddress, presenter: ModifyAuthorPresenter) {
        execute(ModifyAuthorCommand(authorId, firstName, lastName, emailAddress), presenter)
    }

    override fun execute(command: ModifyAuthorCommand, presenter: ModifyAuthorPresenter) {
        authorRepository.get(command.authorId).ifPresentOrElse({
            it.modify(
                command.firstName,
                command.lastName,
                command.emailAddress,
                emailService,
                ModifyAuthorDomainPresenterDecorator(presenter)
            )
            authorRepository.save(it)
            eventPublisher.publish(it.getMutations())
        }, presenter::authorNotFound)
    }
}