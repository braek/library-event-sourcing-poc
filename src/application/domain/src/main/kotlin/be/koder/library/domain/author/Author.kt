package be.koder.library.domain.author

import be.koder.library.domain.aggregate.EventSourcedAggregate
import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.author.event.AuthorModified
import be.koder.library.domain.author.event.AuthorRemoved
import be.koder.library.domain.author.presenter.ModifyAuthorDomainPresenter
import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStream
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName

class Author(eventStream: EventStream) : EventSourcedAggregate(eventStream) {

    private lateinit var id: AuthorId
    private lateinit var firstName: FirstName
    private lateinit var lastName: LastName
    private lateinit var emailAddress: EmailAddress

    override fun dispatch(event: Event) {
        if (event is AuthorCreated) {
            exec(event)
        }
        if (event is AuthorModified) {
            exec(event)
        }
    }

    private fun exec(event: AuthorModified) {
        this.firstName = event.firstName
        this.lastName = event.lastName
        this.emailAddress = event.emailAddress
    }

    private fun exec(event: AuthorCreated) {
        this.id = event.authorId()
        this.firstName = event.firstName
        this.lastName = event.lastName
        this.emailAddress = event.emailAddress
    }

    override fun getId(): AuthorId {
        return id
    }

    fun modify(firstName: FirstName, lastName: LastName, emailAddress: EmailAddress, emailService: EmailService, presenter: ModifyAuthorDomainPresenter) {
        if (emailService.alreadyInUse(emailAddress, id)) {
            presenter.emailAddressAlreadyInUse(emailAddress)
            return
        }
        apply(AuthorModified(id, firstName, lastName, emailAddress))
        presenter.modified(id)
    }

    fun getFirstName(): FirstName {
        return firstName
    }

    fun getLastName(): LastName {
        return lastName
    }

    fun getEmailAddress(): EmailAddress {
        return emailAddress
    }

    fun remove() {
        apply(AuthorRemoved(id))
    }

    companion object {
        @JvmStatic
        fun create(firstName: FirstName, lastName: LastName, email: EmailAddress): Author {
            val author = Author(EventStream.empty())
            author.apply(AuthorCreated(AuthorId.createNew(), firstName, lastName, email))
            return author
        }
    }
}