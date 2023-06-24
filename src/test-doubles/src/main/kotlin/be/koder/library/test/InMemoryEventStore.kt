package be.koder.library.test

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import java.lang.RuntimeException
import java.util.stream.Collectors

class InMemoryEventStore : EventStore {

    private val events: ArrayList<Event> = ArrayList()

    override fun append(aggregateId: AggregateId, mutations: EventStream, lastEventId: EventId?) {
        if (mutations.isEmpty()) {
            return
        }
        if (lastEventId != getLastEventId(aggregateId)) {
            throw RuntimeException(String.format("Optimistic Locking Exception: new events were appended after event with ID %s", lastEventId))
        }
        append(mutations)
    }

    override fun append(mutations: EventStream) {
        mutations.forEach { events.add(it) }
    }

    override fun getLastEventId(aggregateId: AggregateId): EventId? {
        return events.stream()
            .filter { it.tags.contains(aggregateId) }
            .map { it.id }
            .reduce { _, last -> last }
            .orElse(null)
    }

    override fun query(aggregateId: AggregateId): EventStream {
        return EventStream(events.stream()
            .filter { it.tags.contains(aggregateId) }
            .collect(Collectors.toList())
        )
    }

    fun queryByTypes(vararg types: String): EventStream {
        return EventStream(events.stream()
            .filter { types.toList().contains(it.javaClass.simpleName) }
            .collect(Collectors.toList()))
    }
}