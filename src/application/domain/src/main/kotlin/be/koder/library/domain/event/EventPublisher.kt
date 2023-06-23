package be.koder.library.domain.event

interface EventPublisher {
    fun publish(eventStream: EventStream)

    fun publish(event: Event)
}