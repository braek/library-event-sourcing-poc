package be.koder.library.domain.author

import be.koder.library.domain.aggregate.EventSourcedAggregate
import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStream
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import be.koder.library.vocabulary.domain.AggregateId

class Author(eventStream: EventStream) : EventSourcedAggregate(eventStream) {

    private lateinit var id: AuthorId
    private lateinit var firstName: FirstName
    private lateinit var lastName: LastName
    private lateinit var email: EmailAddress

    override fun dispatch(event: Event) {
        TODO("Not yet implemented")
    }

    override fun getId(): AggregateId {
        return id
    }
}