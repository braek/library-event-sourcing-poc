package be.koder.library.domain.event

interface EventStore {

    fun append(eventStream: EventStream)

    fun query(query: EventStreamQuery): EventStream
}