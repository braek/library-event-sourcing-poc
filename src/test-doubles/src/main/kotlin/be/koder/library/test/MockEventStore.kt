package be.koder.library.test

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream

class MockEventStore : EventStore {

    private val events: ArrayList<Event> = ArrayList()

    override fun append(eventStream: EventStream) {
        eventStream.forEach {
            events.add(it)
        }
    }
}