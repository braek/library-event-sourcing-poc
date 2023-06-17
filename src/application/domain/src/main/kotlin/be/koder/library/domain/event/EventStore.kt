package be.koder.library.domain.event

interface EventStore {

    fun append(mutations: EventStream)

    fun query(query: EventStreamQuery): EventStream
}