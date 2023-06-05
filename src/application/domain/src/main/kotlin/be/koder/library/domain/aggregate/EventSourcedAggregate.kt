package be.koder.library.domain.aggregate

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStream

abstract class EventSourcedAggregate : Aggregate {

    private val state: EventStream
    private val mutations: ArrayList<Event> = ArrayList()

    constructor(eventStream: EventStream) {
        this.state = eventStream
        this.state.events.forEach {
            dispatch(it)
        }
    }

    protected fun apply(event: Event) {
        dispatch(event)
        mutations.add(event)
    }

    fun hasStateChanges(): Boolean {
        return mutations.isNotEmpty()
    }

    fun getMutations(): EventStream {
        return EventStream(mutations)
    }

    protected abstract fun dispatch(event: Event)
}