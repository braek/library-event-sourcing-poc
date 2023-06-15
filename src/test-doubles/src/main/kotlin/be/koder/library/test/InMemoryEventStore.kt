package be.koder.library.test

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.domain.event.EventStreamQuery
import be.koder.library.vocabulary.domain.AggregateId
import java.util.stream.Collectors

class InMemoryEventStore : EventStore {

    private val events: ArrayList<Event> = ArrayList()

    override fun append(eventStream: EventStream) {
        eventStream.forEach { events.add(it) }
    }

    override fun query(query: EventStreamQuery): EventStream {
        TODO("Not yet implemented")
    }

    fun query(aggregateId: AggregateId): EventStream {
        return EventStream(events.stream().filter { it.tags.contains(aggregateId) }.collect(Collectors.toList()))
    }

    fun queryByTypes(vararg types: String): EventStream {
        return EventStream(events.stream()
            .filter { types.toList().contains(it.javaClass.simpleName) }
            .collect(Collectors.toList()))
    }
}