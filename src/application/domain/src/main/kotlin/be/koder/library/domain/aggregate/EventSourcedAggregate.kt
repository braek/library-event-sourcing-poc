package be.koder.library.domain.aggregate

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStream

abstract class EventSourcedAggregate(eventStream: EventStream) : Aggregate {

    private var statusQuo: EventStream = eventStream
    private val mutations: ArrayList<Event> = ArrayList()

    init {
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