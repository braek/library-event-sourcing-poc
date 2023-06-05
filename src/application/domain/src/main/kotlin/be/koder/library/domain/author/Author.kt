package be.koder.library.domain.author

import be.koder.library.domain.aggregate.EventSourcedAggregate
import be.koder.library.domain.author.event.AuthorCreated
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
    private lateinit var email: EmailAddress

    override fun dispatch(event: Event) {

    }

    override fun getId(): AuthorId {
        return id
    }

    companion object {
        @JvmStatic
        fun createNew(firstName: FirstName, lastName: LastName, email: EmailAddress): Author {
            val author = Author(EventStream.empty())
            author.apply(AuthorCreated(AuthorId.createNew(), firstName, lastName, email))
            return author
        }
    }
}