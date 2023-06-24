package be.koder.library.domain.event

import be.koder.library.domain.aggregate.EventSourcedAggregate
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId

interface EventStore {

    fun save(aggregate: EventSourcedAggregate)

    fun append(mutations: EventStream)

    fun query(aggregateId: AggregateId): EventStream

    fun queryByTypes(vararg types: String): EventStream

    fun getLastEventId(aggregateId: AggregateId): EventId?
}