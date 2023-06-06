package be.koder.library.test

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventHandler
import be.koder.library.domain.event.EventStreamPublisher
import be.koder.library.domain.event.EventStream

class MockEventStreamPublisher : EventStreamPublisher {

    private val handlers = ArrayList<EventHandler>()
    private val publishedEvents = ArrayList<Event>()

    override fun publish(eventStream: EventStream) {
        eventStream.forEach { event ->
            publishedEvents.add(event)
            handlers.forEach { handler ->
                handler.handle(event)
            }
        }
    }

    fun subscribe(handler: EventHandler) {
        handlers.add(handler)
    }

    fun getPublishedEvents(): List<Event> {
        return publishedEvents.toList()
    }
}