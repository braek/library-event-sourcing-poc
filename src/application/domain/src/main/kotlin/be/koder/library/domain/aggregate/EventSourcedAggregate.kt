package be.koder.library.domain.aggregate

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStream

abstract class EventSourcedAggregate : Aggregate {

    private var statusQuo: EventStream = EventStream(emptyList())
    private val mutations: ArrayList<Event> = ArrayList()

    constructor(stream: EventStream) {
        this.statusQuo = stream
        this.statusQuo.events.forEach {
            dispatch(it)
        }
    }

    protected fun apply(event: Event) {
        dispatch(event)
        mutations.add(event)
    }

    fun isMutated(): Boolean {
        return mutations.isNotEmpty()
    }

    fun getMutations(): EventStream {
        return EventStream(mutations)
    }

    protected abstract fun dispatch(event: Event)
}