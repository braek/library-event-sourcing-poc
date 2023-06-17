package be.koder.library.domain.author

import be.koder.library.domain.author.presenter.CreateAuthorDomainPresenter
import be.koder.library.domain.event.EventStreamPublisher
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName

class AuthorCreator(
    private val authorRepository: AuthorRepository,
    private val eventStreamPublisher: EventStreamPublisher,
    private val emailService: EmailService
) {
    fun create(
        firstName: FirstName,
        lastName: LastName,
        emailAddress: EmailAddress,
        presenter: CreateAuthorDomainPresenter
    ) {
        if (emailService.alreadyInUse(emailAddress)) {
            presenter.emailAddressAlreadyInUse(emailAddress)
            return
        }
        val author = Author.create(firstName, lastName, emailAddress)
        authorRepository.save(author)
        eventStreamPublisher.publish(author.getMutations())
        presenter.created(author.getId())
    }
}