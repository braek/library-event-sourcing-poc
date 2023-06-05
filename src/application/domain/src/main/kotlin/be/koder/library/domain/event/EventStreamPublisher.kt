package be.koder.library.domain.event

interface EventStreamPublisher {
    fun publish(eventStream: EventStream)
}