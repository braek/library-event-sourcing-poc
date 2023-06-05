package be.koder.library.test

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventHandler
import be.koder.library.domain.event.EventPublisher

class MockEventPublisher : EventPublisher {

    private val handlers = ArrayList<EventHandler>()
    private val events = ArrayList<Event>()

    override fun publish(event: Event) {
        events.add(event)
        handlers.forEach {
            it.handle(event)
        }
    }

    fun subscribe(handler: EventHandler) {
        handlers.add(handler)
    }

    fun getPublishedEvents(): List<Event> {
        return events.toList()
    }
}