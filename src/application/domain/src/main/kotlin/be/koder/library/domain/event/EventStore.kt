package be.koder.library.domain.event

import be.koder.library.domain.entity.EventSourcedAggregate
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId

interface EventStore {

    fun appendMutations(aggregate: EventSourcedAggregate)

    // TODO: refactor this one out...
    fun append(eventStream: EventStream)

    fun query(aggregateId: AggregateId): EventStream

    fun query(vararg types: String): EventStream

    fun getLastEventId(aggregateId: AggregateId): EventId?
}