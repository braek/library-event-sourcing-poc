package be.koder.library.domain.event

import be.koder.library.vocabulary.domain.AggregateId

interface EventStore {

    fun append(mutations: EventStream, tag: AggregateId)

    fun query(query: EventStreamQuery): EventStream
}