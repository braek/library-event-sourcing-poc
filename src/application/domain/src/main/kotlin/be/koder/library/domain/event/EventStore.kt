package be.koder.library.domain.event

import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId

interface EventStore {

    fun append(aggregateId: AggregateId, mutations: EventStream, lastEventId: EventId?)

    fun append(mutations: EventStream)

    fun query(aggregateId: AggregateId): EventStream

    fun getLastEventId(aggregateId: AggregateId): EventId?
}