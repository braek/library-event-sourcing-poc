package be.koder.library.test

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventHandler
import be.koder.library.domain.event.EventStream
import be.koder.library.domain.event.EventPublisher

class InMemoryEventPublisher : EventPublisher {

    private val handlers = ArrayList<EventHandler>()
    private val publishedEvents = ArrayList<Event>()

    override fun publish(eventStream: EventStream) {
        eventStream.forEach { event ->
            publish(event)
        }
    }

    override fun publish(event: Event) {
        publishedEvents.add(event)
        handlers.forEach { handler ->
            handler.handle(event)
        }
    }

    fun subscribe(handler: EventHandler) {
        handlers.add(handler)
    }

    fun getPublishedEvents(): List<Event> {
        return publishedEvents.toList()
    }
}