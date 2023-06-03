package be.koder.library.domain.aggregate

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStream

abstract class EventSourcedAggregate private constructor(
    private val statusQuo: EventStream = EventStream(emptyList())
) : Aggregate {

    private val mutations: ArrayList<Event> = ArrayList()

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