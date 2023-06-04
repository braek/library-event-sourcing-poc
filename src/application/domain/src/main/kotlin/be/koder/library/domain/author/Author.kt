package be.koder.library.domain.author

import be.koder.library.domain.aggregate.EventSourcedAggregate
import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStream
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import be.koder.library.vocabulary.domain.AggregateId

class Author : EventSourcedAggregate {

    private var id: AuthorId
//    private var firstName: FirstName
//    private var lastName: LastName
//    private var email: EmailAddress

    constructor(eventStream: EventStream) : super(eventStream)

    override fun dispatch(event: Event) {
        TODO("Not yet implemented")
    }

    override fun getId(): AggregateId {
        return id
    }
}