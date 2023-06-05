package be.koder.library.domain.aggregate

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStream

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

    fun getOrigin(): EventStream {
        return origin
    }

    fun noStateChanges(): Boolean {
        return mutations.isEmpty()
    }

    fun getMutations(): EventStream {
        return EventStream(mutations)
    }

    protected abstract fun dispatch(event: Event)
}