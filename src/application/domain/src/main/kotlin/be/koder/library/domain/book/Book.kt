package be.koder.library.domain.book

import be.koder.library.domain.aggregate.EventSourcedAggregate
import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStream
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.domain.AggregateId

class Book(eventStream: EventStream) : EventSourcedAggregate(eventStream) {

    private lateinit var id: BookId

    override fun dispatch(event: Event) {
        // TODO: implement this one
    }

    override fun getId(): AggregateId {
        return id
    }
}