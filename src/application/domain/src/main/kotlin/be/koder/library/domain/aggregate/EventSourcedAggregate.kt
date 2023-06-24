package be.koder.library.domain.aggregate

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStream
import be.koder.library.vocabulary.event.EventId
import kotlin.collections.ArrayList

abstract class EventSourcedAggregate(eventStream: EventStream) : Aggregate {

    private val origin: EventStream = eventStream
    private val mutations: ArrayList<Event> = ArrayList()

    init {
        this.origin.forEach {
            dispatch(it)
        }
    }

    protected fun apply(event: Event) {
        dispatch(event)
        mutations.add(event)
    }

    fun getLastEventId(): EventId? {
        return origin.stream()
            .reduce { _, last -> last }
            .map { it.id }
            .orElse(null)
    }

    fun getMutations(): EventStream {
        return EventStream(mutations)
    }

    fun noStateChanges(): Boolean {
        return mutations.isEmpty()
    }

    protected abstract fun dispatch(event: Event)
}