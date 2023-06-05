package be.koder.library.test

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventHandler
import be.koder.library.domain.event.EventPublisher
import be.koder.library.domain.event.EventStream

class MockEventPublisher : EventPublisher {

    private val handlers = ArrayList<EventHandler>()
    private val events = ArrayList<Event>()

    override fun publish(eventStream: EventStream) {
        events.add(eventStream)
        handlers.forEach {
            it.handle(eventStream)
        }
    }

    fun subscribe(handler: EventHandler) {
        handlers.add(handler)
    }

    fun getPublishedEvents(): List<Event> {
        return events.toList()
    }
}