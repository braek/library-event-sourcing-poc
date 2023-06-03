package be.koder.library.domain.event

interface EventPublisher {
    fun publish(event: Event)
}