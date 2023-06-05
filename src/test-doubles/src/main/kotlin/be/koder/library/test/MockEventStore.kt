package be.koder.library.test

import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.vocabulary.domain.AggregateId
import java.util.stream.Collectors
import kotlin.reflect.KClass

class MockEventStore : EventStore {

    private val events: ArrayList<Event> = ArrayList()

    override fun append(eventStream: EventStream) {
        eventStream.forEach { events.add(it) }
    }

    fun query(aggregateId: AggregateId): EventStream {
        return EventStream(events.stream().filter { it.tags().contains(aggregateId) }.collect(Collectors.toList()))
    }

    fun <T : Event> query(clazz: KClass<T>): EventStream {
        return EventStream(events.stream().filter { it::class == clazz }.collect(Collectors.toList()))
    }
}