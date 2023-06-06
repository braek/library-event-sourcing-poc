package be.koder.library.domain.event

import java.util.stream.Stream
import java.util.stream.StreamSupport

data class EventStream(private val events: List<Event>) : Iterable<Event> {

    constructor(vararg events: Event) : this(events.asList())

    fun isEmpty(): Boolean {
        return events.isEmpty()
    }

    override fun iterator(): Iterator<Event> {
        return events.iterator();
    }

    fun stream(): Stream<Event> {
        return StreamSupport.stream(events.spliterator(), false)
    }

    companion object {
        @JvmStatic
        fun empty(): EventStream {
            return EventStream(emptyList())
        }
    }
}